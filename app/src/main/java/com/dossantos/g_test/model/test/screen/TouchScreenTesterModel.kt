package com.dossantos.g_test.model.test.screen

import android.graphics.Rect

data class TouchScreenTesterModel(
    val id: Int,
    var rect: Rect? = null,
    var isPassed: Boolean = false
)
