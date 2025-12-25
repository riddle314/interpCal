package com.dimitriskatsikas.interpolator.calculator.ui

import app.cash.turbine.test
import com.dimitriskatsikas.interpolator.calculator.domain.ComputeLinearInterpolationUseCase
import com.dimitriskatsikas.interpolator.calculator.ui.CalculatorView.State.CtaState
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorViewModelTest {

    private var computeLinearInterpolationUseCase: ComputeLinearInterpolationUseCase =
        ComputeLinearInterpolationUseCase()
    private lateinit var testClass: CalculatorViewModel

    @BeforeEach
    fun setUp() {
        testClass = CalculatorViewModel(
            computeLinearInterpolationUseCase = computeLinearInterpolationUseCase
        )
    }

    @Test
    fun `when viewModel is initialized, then state is empty`() = runTest {
        testClass.state.test {
            Assertions.assertEquals(CalculatorView.State(), awaitItem())
        }
    }

    // TODO need to handle other cases also for calculate
    @Test
    fun `when UiAction is Calculate, then calculate`() = runTest {
        val inputX1 = "1"
        val inputY1 = "100"
        val inputX2 = "2"
        val inputY2 = "200"
        val inputX3 = "1.5"

        testClass.onUiAction(
            CalculatorView.UiAction.InputChange(
                inputX1 = inputX1,
                inputY1 = inputY1,
                inputX2 = inputX2,
                inputY2 = inputY2,
                inputX3 = inputX3
            )
        )
        testClass.onUiAction(CalculatorView.UiAction.Calculate)

        val expected = CalculatorView.State(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3,
            result = "150.0",
            ctaState = CtaState.Enabled
        )
        testClass.state.test {
            Assertions.assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `given inputs all filled with numbers, when UiAction is InputChange, then update state`() =
        runTest {
            val inputX1 = "1"
            val inputY1 = "100"
            val inputX2 = "2"
            val inputY2 = "200"
            val inputX3 = "1.5"

            testClass.onUiAction(
                CalculatorView.UiAction.InputChange(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3
                )
            )

            val expected = CalculatorView.State(
                inputX1 = inputX1,
                inputY1 = inputY1,
                inputX2 = inputX2,
                inputY2 = inputY2,
                inputX3 = inputX3,
                ctaState = CtaState.Enabled
            )
            testClass.state.test {
                Assertions.assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given some inputs filled with no numbers, when UiAction is InputChange, then update state`() =
        runTest {
            val inputX1 = "1"
            val inputY1 = "100"
            val inputX2 = "2"
            val inputY2 = "ab"
            val inputX3 = "1.5"

            testClass.onUiAction(
                CalculatorView.UiAction.InputChange(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3
                )
            )

            val expected = CalculatorView.State(
                inputX1 = inputX1,
                inputY1 = inputY1,
                inputX2 = inputX2,
                inputY2 = inputY2,
                inputX3 = inputX3,
                ctaState = CtaState.Disabled
            )
            testClass.state.test {
                Assertions.assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given some inputs empty, when UiAction is InputChange, then update state`() =
        runTest {
            val inputX1 = "1"
            val inputY1 = "100"
            val inputX2 = "2"
            val inputY2 = "200"
            val inputX3 = ""

            testClass.onUiAction(
                CalculatorView.UiAction.InputChange(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3
                )
            )

            val expected = CalculatorView.State(
                inputX1 = inputX1,
                inputY1 = inputY1,
                inputX2 = inputX2,
                inputY2 = inputY2,
                inputX3 = inputX3,
                ctaState = CtaState.Disabled
            )
            testClass.state.test {
                Assertions.assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given input, when UiAction Clear is called, then clearState`() = runTest {
        val inputX1 = "1"
        val inputY1 = "100"
        val inputX2 = "2"
        val inputY2 = "200"
        val inputX3 = "1.5"

        testClass.onUiAction(
            CalculatorView.UiAction.InputChange(
                inputX1 = inputX1,
                inputY1 = inputY1,
                inputX2 = inputX2,
                inputY2 = inputY2,
                inputX3 = inputX3
            )
        )
        testClass.onUiAction(CalculatorView.UiAction.Clear)

        val expected = CalculatorView.State(
            inputX1 = "",
            inputY1 = "",
            inputX2 = "",
            inputY2 = "",
            inputX3 = "",
            result = "",
            ctaState = CtaState.Disabled
        )
        testClass.state.test {
            Assertions.assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `when UiAction OpenInfoScreen is called, then openInfoScreen`() = runTest {
        testClass.onUiAction(CalculatorView.UiAction.OpenInfoScreen)

        testClass.effect.test {
            Assertions.assertEquals(CalculatorView.Effect.OpenInfoScreen, awaitItem())
        }
    }
}
