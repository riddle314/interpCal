package com.dimitriskatsikas.interpolator.calculator.domain

import kotlinx.coroutines.test.runTest
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
    fun `given numbers case 1, when invoke, then return successful result`() = runTest {
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
    fun `given numbers case 2, when invoke, then return successful result`() = runTest {
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
        Assertions.assertEquals("5.0", result.getOrNull())
    }

    @Test
    fun `given identical x inputs, when invoke, then return failure`() = runTest {
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
    fun `given no numbers input, when invoke, then return failure`() = runTest {
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
