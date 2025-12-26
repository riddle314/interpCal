package com.dimitriskatsikas.interpolator.info

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InfoViewModelTest {

    private lateinit var testClass: InfoViewModel

    private val versionName = "1.0.0-TEST"

    @BeforeEach
    fun setUp() {
        testClass = InfoViewModel(versionName = versionName)
    }

    @Test
    fun `when viewModel is initialized, then state contains version name`() = runTest {
        testClass.state.test {
            val expectedState = InfoView.State(versionName = versionName)
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `when UiAction is OnBackClicked, then navigateBack`() = runTest {
        testClass.onUiAction(InfoView.UiAction.OnBackClicked)

        testClass.effect.test {
            assertEquals(InfoView.Effect.NavigateBack, awaitItem())
        }
    }
}
