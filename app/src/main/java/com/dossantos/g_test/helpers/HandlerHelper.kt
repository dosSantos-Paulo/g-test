package com.dossantos.g_test.helpers

import android.os.Handler
import android.os.Looper

class HandlerHelper private constructor() {
    companion object {
        fun postDelayed(handleTimer: Long, onEnd: () -> Unit) {
            Handler(Looper.getMainLooper()).postDelayed(onEnd, handleTimer)
        }
    }
}