package com.dossantos.g_test.helpers

import android.os.SystemClock
import android.view.MotionEvent
import android.view.View

class AutoTouchEventHelper private constructor() {
    companion object {

        private const val ZERO = 0

        private const val SAFE_MARGIN = 10F

        private const val HANDLE_NEXT_EVENT = 100L

        private var cardSize = 80F

        private var isEnableSession = true

        fun finishSession() {
            isEnableSession = false
        }

        fun View.autoScrollEntireScreen(size: Float = 80F) {
            isEnableSession = true
            cardSize = size
            performNewMotion()
        }

        private fun View.performNewMotion(_x: Float = 0F, _y: Float = 0F) {
            dispatchTouchEvent(createMotionEvent(_x, _y))
            if (isEnableSession) nextEvent(_x, _y)
        }

        private fun View.nextEvent(_x: Float, _y: Float) {
            HandlerHelper.postDelayed(HANDLE_NEXT_EVENT) {
                if (isItStillOnScreen(_x)) goNext(_x, _y) else jumpDownMotion(_y)
            }
        }

        private fun View.isItStillOnScreen(_x: Float) = _x < width - SAFE_MARGIN

        private fun View.goNext(_x: Float, _y: Float) = performNewMotion(_x + cardSize, _y)

        private fun View.jumpDownMotion(_y: Float) = performNewMotion(_y = _y + cardSize)

        private fun createMotionEvent(x: Float, y: Float) = MotionEvent.obtain(
            SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis(),
            MotionEvent.ACTION_MOVE,
            x,
            y,
            ZERO
        )
    }
}