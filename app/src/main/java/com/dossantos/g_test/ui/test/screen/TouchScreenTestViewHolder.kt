package com.dossantos.g_test.ui.test.screen

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.g_test.R
import com.dossantos.g_test.databinding.ViewTouchScreenTestItemBinding
import com.dossantos.g_test.helpers.ScreenHelper.Companion.awaitForLayout
import com.dossantos.g_test.model.test.screen.TouchScreenTesterModel

class TouchScreenTestViewHolder(
    private val binding: ViewTouchScreenTestItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TouchScreenTesterModel) = with(binding.root) {
        if (item.isPassed) background.setTint(context.getColor(R.color.green_100))
        if (item.rect == null) getRect(item)
    }

    private fun getRect(item: TouchScreenTesterModel) = binding.root.awaitForLayout { card ->
        item.rect = Rect(card.left, card.top, card.right, card.bottom)
    }

    companion object {
        fun inflate(parent: ViewGroup) = TouchScreenTestViewHolder(
            ViewTouchScreenTestItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}