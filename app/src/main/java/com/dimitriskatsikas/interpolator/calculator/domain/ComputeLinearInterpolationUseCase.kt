package com.dimitriskatsikas.interpolator.calculator.domain

class ComputeLinearInterpolationUseCase {

    suspend operator fun invoke(
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
                val substractionY2Y1 = y2 - y1
                val substractionX2X1 = x2 - x1
                val substractionX3X1 = x3 - x1
                val slope = substractionY2Y1.divide(substractionX2X1)
                val y3 = slope * substractionX3X1 + y1
                return Result.success(y3.toString())
            }
        } else {
            return Result.failure(NoNumbersInputException())
        }
    }

    class IdenticalXInputsException() : Exception()
    class NoNumbersInputException() : Exception()
}
