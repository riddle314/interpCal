package com.dimitriskatsikas.interpolator.calculator.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ComputeLinearInterpolationUseCaseTest {

    private lateinit var testClass: ComputeLinearInterpolationUseCase

    @BeforeEach
    fun setUp() {
        testClass = ComputeLinearInterpolationUseCase()
    }

    @Test
    fun `given numbers case 1, when invoke, then return successful result`() {
        val inputX1 = "1"
        val inputY1 = "100"
        val inputX2 = "2"
        val inputY2 = "200"
        val inputX3 = "1.5"

        val result = testClass(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3
        )

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals("150.0", result.getOrNull())
    }

    @Test
    fun `given numbers case 2, when invoke, then return successful result`() {
        val inputX1 = "2"
        val inputY1 = "6"
        val inputX2 = "6"
        val inputY2 = "4"
        val inputX3 = "4"

        val result = testClass(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3
        )

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals("5", result.getOrNull())
    }

    @Test
    fun `given numbers case 3, when invoke, then return successful result`() {
        // Through the calculations a number can appear that has a non-terminating
        // decimal expansion and maybe it needs to be rounded otherwise it can create problems.
        // For example in our case if you do the calculations like this:
        // y3 = ((9-6)/(9-2)) * (9-2) + 6 = 3/7 * 7 + 6, we have along the lines
        // the following quotient 3/7 = 0,42857143 which has a non-terminating decimal expansion.
        // In our case though we see that the final result is easy to calculated as 9,
        // so if the calculations are handled correctly we shouldn't have precision problems.
        // So this is what this test, tests out.
        val inputX1 = "2"
        val inputY1 = "6"
        val inputX2 = "9"
        val inputY2 = "9"
        val inputX3 = "9"

        val result = testClass(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3
        )

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals("9", result.getOrNull())
    }

    @Test
    fun `given numbers case 4, when invoke, then return successful result`() {
        // Through the calculations a number can appear that has a non-terminating
        // decimal expansion and maybe it needs to be rounded otherwise it can create problems.
        // For example in our case if you do the calculations like this:
        // y3 = ((9-6)* (9-2))/ (51-2)+ 6 = 21/49 + 6 = (3*7)/(7*7) + 6 = 3/7 + 6,
        // we have along the lines the following quotient 21/49 = 3/7 = 0,42857143,
        // which has a non-terminating decimal expansion.
        // So in our case here we need to see that everything works fine
        // and we have a result with some precision.
        val inputX1 = "2"
        val inputY1 = "6"
        val inputX2 = "51"
        val inputY2 = "9"
        val inputX3 = "9"

        val result = testClass(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3
        )

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(
            "6.4285714285714285714285714285714286",
            result.getOrNull()
        )
    }

    @Test
    fun `given identical x inputs, when invoke, then return failure`() {
        val inputX1 = "1"
        val inputY1 = "100"
        val inputX2 = "1"
        val inputY2 = "200"
        val inputX3 = "1.5"

        val result = testClass(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3
        )

        Assertions.assertTrue(result.isFailure)
        Assertions.assertTrue(
            result.exceptionOrNull() is ComputeLinearInterpolationUseCase.IdenticalXInputsException
        )
    }

    @Test
    fun `given no numbers input, when invoke, then return failure`() {
        val inputX1 = "1"
        val inputY1 = ""
        val inputX2 = "1"
        val inputY2 = "a.b"
        val inputX3 = "3"

        val result = testClass(
            inputX1 = inputX1,
            inputY1 = inputY1,
            inputX2 = inputX2,
            inputY2 = inputY2,
            inputX3 = inputX3
        )

        Assertions.assertTrue(result.isFailure)
        Assertions.assertTrue(
            result.exceptionOrNull() is ComputeLinearInterpolationUseCase.NoNumbersInputException
        )
    }

}
