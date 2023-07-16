package com.dossantos.g_test.ui.custom.layout_manager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UnScrollableGridLayoutManager(context: Context, spanCount: Int, private val verticalSpanCount: Int) :
    GridLayoutManager(context, spanCount) {

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun checkLayoutParams(layoutParams: RecyclerView.LayoutParams?): Boolean {
        layoutParams?.height = height / verticalSpanCount
        return true
    }
}