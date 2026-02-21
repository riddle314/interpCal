package com.dimitriskatsikas.interpolator.ui.info

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

    @Test
    fun `when UiAction is OnPolicyClicked, then navigateToPolicy`() = runTest {
        val policyUrl = "https://example.com/policy"
        testClass.onUiAction(InfoView.UiAction.OnPolicyClicked(url = policyUrl))

        testClass.effect.test {
            assertEquals(InfoView.Effect.NavigateToPolicy(url = policyUrl), awaitItem())
        }
    }

    @Test
    fun `when UiAction is OnRateClicked, then navigateToRate`() = runTest {
        val rateUrl = "https://example.com/rate"
        testClass.onUiAction(InfoView.UiAction.OnRateClicked(url = rateUrl))

        testClass.effect.test {
            assertEquals(InfoView.Effect.NavigateToRate(url = rateUrl), awaitItem())
        }
    }
}
