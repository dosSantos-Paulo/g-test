package com.dossantos.g_test.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dossantos.g_test.databinding.ActivityHomeBinding
import com.dossantos.g_test.ui.test.screen.TouchScreenTestActivity

class HomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupClickListener()
    }

    private fun setupClickListener() = with(binding) {
        buttonInitTest.setOnClickListener { navigateToTouchScreenTest() }
        buttonInitAutoTest.setOnClickListener { navigateToTouchScreenTest(true) }
    }

    private fun navigateToTouchScreenTest(runAutoModel: Boolean = false) {
        TouchScreenTestActivity.startActivity(this, runAutoModel)
    }
}