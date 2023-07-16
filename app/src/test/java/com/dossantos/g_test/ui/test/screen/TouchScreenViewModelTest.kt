package com.dossantos.g_test.ui.test.screen

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.res.Resources
import android.os.Looper
import android.util.DisplayMetrics
import androidx.lifecycle.Observer
import com.dossantos.g_test.helpers.HandlerHelper
import com.dossantos.g_test.model.test.screen.TouchScreenTesterModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TouchScreenViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val resources = mockk<Resources>(relaxed = true)

    val display = mockk<DisplayMetrics>(relaxed = true) {
        densityDpi = 1000
        widthPixels = 2000
        heightPixels = 1200
    }

    val looper = mockk<Looper> {
        every { thread } returns Thread.currentThread()
    }

    val recyclerSetup = mockk<Observer<RecyclerSetup>>(relaxed = true)

    val finishWithSuccess = mockk<Observer<Boolean>>(relaxed = true)

    val unitSlot = slot<() -> Unit>()

    val longSlot = slot<Long>()

    val viewModel = TouchScreenViewModel(resources)

    @Before
    fun before() {
        mockkStatic(Looper::class)
        mockkObject(HandlerHelper)
        every { Looper.getMainLooper() } returns looper
        every { HandlerHelper.postDelayed(any(), any()) } returns Unit
        every { resources.displayMetrics } returns display
    }

    @Test
    fun `test init() with empty list`() = runBlocking {
        // Arrange
        viewModel.recyclerSetup.observeForever(recyclerSetup)
        val expectedVerticalItems = 4
        val expectedHorizontalItems = 2
        val itemsCount = expectedHorizontalItems * expectedVerticalItems

        // Act
        viewModel.init()

        // Assert
        verify {
            recyclerSetup.onChanged(
                Triple(expectedVerticalItems, expectedHorizontalItems, mockTestedCard(itemsCount))
            )
        }
    }

    @Test
    fun `test fail timer after start test`() = runBlocking {
        // Arrange
        val tenSeconds = 10000L
        viewModel.finishWithSuccess.observeForever(finishWithSuccess)
        viewModel.init()

        // Act
        viewModel.onNewItemPassed(TouchScreenTesterModel(1))

        // Assert
        verify { HandlerHelper.postDelayed(capture(longSlot), capture(unitSlot)) }
        assert(longSlot.captured == tenSeconds)
        unitSlot.captured.invoke()
        verify { finishWithSuccess.onChanged(false) }
    }

    @Test
    fun `test success when click in all items`() = runBlocking {
        // Arrange
        val expectedVerticalItems = 4
        val expectedHorizontalItems = 2
        val itemsCount = expectedHorizontalItems * expectedVerticalItems
        viewModel.finishWithSuccess.observeForever(finishWithSuccess)
        viewModel.init()

        // Act
        mockTestedCard(itemsCount).forEach { card ->
            viewModel.onNewItemPassed(TouchScreenTesterModel(card.id))
        }

        // Assert
        verify { finishWithSuccess.onChanged(true) }
    }

    private fun mockTestedCard(itemsCount: Int): MutableList<TouchScreenTesterModel> {
        val list = mutableListOf<TouchScreenTesterModel>()
        for (i in 0 until itemsCount) {
            list.add(TouchScreenTesterModel(id = i))
        }
        return list
    }
}