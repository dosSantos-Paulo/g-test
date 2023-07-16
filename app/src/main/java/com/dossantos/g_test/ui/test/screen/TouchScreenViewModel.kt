package com.dossantos.g_test.ui.test.screen

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.g_test.helpers.HandlerHelper
import com.dossantos.g_test.helpers.ScreenHelper.Companion.getWindowHeight
import com.dossantos.g_test.helpers.ScreenHelper.Companion.getWindowWidth
import com.dossantos.g_test.model.test.screen.TouchScreenTesterModel

typealias RecyclerSetup = Triple<Int, Int, MutableList<TouchScreenTesterModel>>

class TouchScreenViewModel(private val resources: Resources) : ViewModel() {

    var finishWithSuccess = MutableLiveData<Boolean>()

    var recyclerSetup = MutableLiveData<RecyclerSetup>()

    var cardSize = ZERO
        private set

    private var items = mutableListOf<TouchScreenTesterModel>()

    private var itemsCount = ZERO

    private var maxHorizontalItems = ZERO

    private var maxVerticalItems = ZERO

    private var totalItemsPassed = ZERO

    private var timerIsAlreadyInitiated = false

    fun init() {
        if (items.isEmpty()) {
            setup()
            createTesterCards()
        }
        emitRecyclerSetup()
    }

    fun onNewItemPassed(model: TouchScreenTesterModel) {
        if (!timerIsAlreadyInitiated) initTimer()
        items.firstOrNull { it.id == model.id }?.isPassed = model.isPassed
        totalItemsPassed++
        checkIfHasFinished()
    }

    private fun checkIfHasFinished() {
        if (totalItemsPassed == itemsCount) finishWithSuccess.value = true
    }

    private fun setup() {
        cardSize = resources.displayMetrics.densityDpi / HALF
        maxHorizontalItems = resources.getWindowWidth() / cardSize
        maxVerticalItems = resources.getWindowHeight() / cardSize
        itemsCount = maxHorizontalItems * maxVerticalItems
    }

    private fun createTesterCards() {
        for (i in ZERO until itemsCount) {
            items.add(TouchScreenTesterModel(id = i))
        }
    }

    private fun emitRecyclerSetup() {
        recyclerSetup.value = Triple(maxHorizontalItems, maxVerticalItems, items)
    }

    private fun initTimer() {
        HandlerHelper.postDelayed(TEN_SECONDS, ::onTimerEnd)
    }

    private fun onTimerEnd() {
        if (finishWithSuccess.value != true) {
            finishWithSuccess.value = false
        }
    }

    private companion object {
        const val ZERO = 0
        const val HALF = 2
        const val TEN_SECONDS = 10000L
    }
}