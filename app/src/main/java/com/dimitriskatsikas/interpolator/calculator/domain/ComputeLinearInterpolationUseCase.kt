package com.dimitriskatsikas.interpolator.calculator.domain

import java.math.MathContext

class ComputeLinearInterpolationUseCase {

    operator fun invoke(
        inputX1: String,
        inputY1: String,
        inputX2: String,
        inputY2: String,
        inputX3: String
    ): Result<String> {

        val x1 = inputX1.toBigDecimalOrNull()
        val y1 = inputY1.toBigDecimalOrNull()
        val x2 = inputX2.toBigDecimalOrNull()
        val y2 = inputY2.toBigDecimalOrNull()
        val x3 = inputX3.toBigDecimalOrNull()

        val areAllFieldsFilledWithNumbers = x1 != null &&
                y1 != null &&
                x2 != null &&
                y2 != null &&
                x3 != null

        if (areAllFieldsFilledWithNumbers) {
            if (x1 == x2) {
                return Result.failure(IdenticalXInputsException())
            } else {
                // Formula: y3 = y1 + ((y2 - y1) * (x3 - x1)) / (x2 - x1)
                val numerator = (y2 - y1) * (x3 - x1)
                val denominator = x2 - x1
                val divisionResult = numerator.divide(
                    denominator,
                    MathContext.DECIMAL128
                )
                val y3 = y1 + divisionResult
                return Result.success(y3.toPlainString())
            }
        } else {
            return Result.failure(NoNumbersInputException())
        }
    }

    class IdenticalXInputsException() : Exception(
        "The initial X values (x1 and x2) cannot be identical."
    )

    class NoNumbersInputException() : Exception(
        "All input fields must contain valid numbers."
    )
}
