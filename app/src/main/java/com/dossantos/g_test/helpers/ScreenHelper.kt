package com.dossantos.g_test.helpers

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import android.view.ViewTreeObserver

class ScreenHelper private constructor() {
    companion object {

        @SuppressLint("InternalInsetResource", "DiscouragedApi")
        fun Resources.getWindowHeight(): Int {
            val statusBar = getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = getDimensionPixelSize(statusBar)
            val navBar = getIdentifier("navigation_bar_height", "dimen", "android")
            val navBarHeight = getDimensionPixelSize(navBar)
            val height = statusBarHeight - navBarHeight
            return (displayMetrics.heightPixels - height)
        }

        fun Resources.getWindowWidth() = displayMetrics.widthPixels

        fun View.awaitForLayout(doIt: (View) -> Unit) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    doIt(this@awaitForLayout)
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }
}