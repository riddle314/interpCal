package com.dimitriskatsikas.interpolator.calculator.ui

import app.cash.turbine.test
import com.dimitriskatsikas.interpolator.calculator.domain.ComputeLinearInterpolationUseCase
import com.dimitriskatsikas.interpolator.calculator.ui.CalculatorView.State.CtaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private var computeLinearInterpolationUseCase: ComputeLinearInterpolationUseCase =
        ComputeLinearInterpolationUseCase()
    private lateinit var testClass: CalculatorViewModel

    @BeforeAll
    fun setupAll() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterAll
    fun tearDownAll() {
        Dispatchers.resetMain()
    }

    @BeforeEach
    fun setUp() {
        testClass = CalculatorViewModel(
            computeLinearInterpolationUseCase = computeLinearInterpolationUseCase
        )
    }

    @Test
    fun `when viewModel is initialized, then state is empty`() =
        runTest {
            testClass.state.test {
                Assertions.assertEquals(CalculatorView.State(), awaitItem())
            }
        }

    @Test
    fun `given inputs all filled with numbers, when UiAction is Calculate, then update state`() =
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

            testClass.state.test {
                testClass.onUiAction(CalculatorView.UiAction.Calculate)

                Assertions.assertEquals(
                    CalculatorView.State(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3,
                        result = "",
                        ctaState = CtaState.Enabled
                    ),
                    awaitItem()
                )
                Assertions.assertEquals(
                    CalculatorView.State(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3,
                        result = "",
                        ctaState = CtaState.Loading
                    ),
                    awaitItem()
                )
                Assertions.assertEquals(
                    CalculatorView.State(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3,
                        result = "150",
                        ctaState = CtaState.Enabled
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `given inputs all filled with numbers and UiAction is Calculate, when UiAction is InputChange for different numbers and UiAction is Calculate, then the state is updated correctly`() =
        runTest {
            testClass.onUiAction(
                CalculatorView.UiAction.InputChange(
                    inputX1 = "1",
                    inputY1 = "100",
                    inputX2 = "2",
                    inputY2 = "200",
                    inputX3 = "1.5"
                )
            )
            testClass.onUiAction(CalculatorView.UiAction.Calculate)
            testClass.onUiAction(
                CalculatorView.UiAction.InputChange(
                    inputX1 = "2",
                    inputY1 = "400",
                    inputX2 = "4",
                    inputY2 = "800",
                    inputX3 = "2.5"
                )
            )
            testClass.onUiAction(CalculatorView.UiAction.Calculate)

            testClass.state.test {
                Assertions.assertEquals(
                    CalculatorView.State(
                        inputX1 = "2",
                        inputY1 = "400",
                        inputX2 = "4",
                        inputY2 = "800",
                        inputX3 = "2.5",
                        result = "500",
                        ctaState = CtaState.Enabled
                    ), awaitItem()
                )
            }
        }

    @Test
    fun `given input with no numbers, when UiAction is Calculate, then update state and show error toast`() =
        runTest {
            val inputX1 = "1"
            val inputY1 = ""
            val inputX2 = "1"
            val inputY2 = "a.b"
            val inputX3 = "3"

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

            testClass.state.test {
                val expected = CalculatorView.State(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3,
                    result = "",
                    ctaState = CtaState.Disabled
                )
                Assertions.assertEquals(expected, awaitItem())
            }

            testClass.effect.test {
                Assertions.assertEquals(
                    CalculatorView.Effect.ShowErrorToast(
                        CalculatorView.ErrorType.NoNumbersInput
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `given identical x inputs,, when UiAction is Calculate, then update state and show error toast`() =
        runTest {
            val inputX1 = "1"
            val inputY1 = "100"
            val inputX2 = "1"
            val inputY2 = "200"
            val inputX3 = "3"

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


            testClass.state.test {
                val expected = CalculatorView.State(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3,
                    result = "",
                    ctaState = CtaState.Enabled
                )
                Assertions.assertEquals(expected, awaitItem())
            }

            testClass.effect.test {
                Assertions.assertEquals(
                    CalculatorView.Effect.ShowErrorToast(
                        CalculatorView.ErrorType.IdenticalXInputs
                    ),
                    awaitItem()
                )
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

            testClass.state.test {
                val expected = CalculatorView.State(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3,
                    ctaState = CtaState.Enabled
                )
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

            testClass.state.test {
                val expected = CalculatorView.State(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3,
                    ctaState = CtaState.Disabled
                )
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

            testClass.state.test {
                val expected = CalculatorView.State(
                    inputX1 = inputX1,
                    inputY1 = inputY1,
                    inputX2 = inputX2,
                    inputY2 = inputY2,
                    inputX3 = inputX3,
                    ctaState = CtaState.Disabled
                )
                Assertions.assertEquals(expected, awaitItem())
            }
        }

    @Test
    fun `given input, when UiAction is Clear, then clearState`() =
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
    fun `when UiAction is OpenInfoScreen, then openInfoScreen`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OpenInfoScreen)

            testClass.effect.test {
                Assertions.assertEquals(CalculatorView.Effect.OpenInfoScreen, awaitItem())
            }
        }

    @Test
    fun `when UiAction is DismissExplainerDialog, then dismissExplainerDialog`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.DismissExplainerDialog)

            testClass.state.test {
                Assertions.assertFalse(expectMostRecentItem().isExplainerDialogVisible)
            }
        }

    @Test
    fun `when UiAction is ShowExplainerDialog, then showExplainerDialog`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.ShowExplainerDialog)

            testClass.state.test {
                Assertions.assertTrue(expectMostRecentItem().isExplainerDialogVisible)
            }
        }
}
