package com.dossantos.g_test.ui.test.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.dossantos.g_test.R
import com.dossantos.g_test.databinding.ActivityTouchScreenTestBinding
import com.dossantos.g_test.helpers.AutoTouchEventHelper
import com.dossantos.g_test.helpers.AutoTouchEventHelper.Companion.autoScrollEntireScreen
import com.dossantos.g_test.model.test.screen.TouchScreenTesterModel
import com.dossantos.g_test.ui.custom.layout_manager.UnScrollableGridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersHolder
import kotlin.math.roundToInt

class TouchScreenTestActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTouchScreenTestBinding.inflate(layoutInflater) }

    private var touchAdapter: TouchScreenTestAdapter? = null

    private var touchLayoutManager: GridLayoutManager? = null

    private val viewModel by viewModel<TouchScreenViewModel> {
        ParametersHolder(mutableListOf(resources))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.init()
        setupObservables()
        setupOnTouchListener()
        setupOnClickListeners()
        checkAutoMode()
    }

    private fun checkAutoMode() {
        if (intent.extras?.getBoolean(AUTO_MODE) == true)
            binding.recyclerView.autoScrollEntireScreen(viewModel.cardSize.toFloat())
    }

    private fun setupOnClickListeners() = with(binding) {
        buttonPassed.setOnClickListener { happyEnding() }
        buttonFail.setOnClickListener { sadEnding() }
    }

    private fun setupObservables() {
        viewModel.finishWithSuccess.observe(this) { isFinished -> onFinished(isFinished) }

        viewModel.recyclerSetup.observe(this) { (_maxHorizontalItems, _maxVerticalItems, _items) ->
            touchLayoutManager = getLayoutManager(_maxHorizontalItems, _maxVerticalItems)
            touchAdapter = getAdapter(_items)
            setupRecyclerView()
        }
    }

    private fun onFinished(isFinished: Boolean) {
        AutoTouchEventHelper.finishSession()
        if (isFinished) showEndButtons() else sadEnding()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        layoutManager = touchLayoutManager
        adapter = touchAdapter
        setHasFixedSize(true)
    }

    private fun getAdapter(items: MutableList<TouchScreenTesterModel>) =
        TouchScreenTestAdapter(items)

    fun getLayoutManager(maxHorizontalItems: Int, maxVerticalItems: Int) =
        UnScrollableGridLayoutManager(this, maxHorizontalItems, maxVerticalItems)

    @SuppressLint("ClickableViewAccessibility")
    private fun setupOnTouchListener() = binding.recyclerView.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> getCardHoverEvent(event)
        }
        true
    }

    private fun getCardHoverEvent(event: MotionEvent) {
        touchAdapter?.touchItemsList?.forEachIndexed { index, model ->
            model.rect?.let { rect ->
                if (rect.contains(event.x.roundToInt(), event.y.roundToInt()))
                    doWhenCardIsFound(index, model)
            }
        }
    }

    private fun doWhenCardIsFound(index: Int, model: TouchScreenTesterModel) {
        if (!model.isPassed) viewModel.onNewItemPassed(model)
        model.isPassed = true
        touchAdapter?.notifyItemChanged(index)
    }

    private fun showEndButtons() {
        binding.buttonLayout.isVisible = true
    }

    private fun happyEnding() {
        showLongToast(getString(R.string.test_passed))
        finish()
    }

    private fun sadEnding() {
        showLongToast(getString(R.string.test_fail))
        finish()
    }

    private fun showLongToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    companion object {
        private const val AUTO_MODE = "AUTO_MODE"
        fun startActivity(context: Context, runAutoModel: Boolean) {
            val intent = Intent(context, TouchScreenTestActivity::class.java)
                .apply { putExtra(AUTO_MODE, runAutoModel) }
            context.startActivity(intent)
        }
    }
}