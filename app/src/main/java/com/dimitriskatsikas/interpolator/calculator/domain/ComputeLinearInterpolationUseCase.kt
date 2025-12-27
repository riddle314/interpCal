package com.dimitriskatsikas.interpolator.calculator.domain

import java.math.BigDecimal
import java.math.MathContext

class ComputeLinearInterpolationUseCase {

    /**
     * Computes the linear interpolation for a given set of points.
     *
     * This function takes the coordinates of two known points (x1, y1) and (x2, y2),
     * and the x-coordinate of a third point (x3). It then calculates the corresponding
     * y-coordinate (y3) for the third point, assuming it lies on the straight line
     * connecting the first two points.
     *
     * The formula used is: `y3 = y1 + ((y2 - y1) * (x3 - x1)) / (x2 - x1)`.
     *
     * All input values are provided as strings and are converted to [BigDecimal] for high-precision calculation.
     *
     * @param inputX1 The x-coordinate of the first point.
     * @param inputY1 The y-coordinate of the first point.
     * @param inputX2 The x-coordinate of the second point.
     * @param inputY2 The y-coordinate of the second point.
     * @param inputX3 The x-coordinate of the point to interpolate.
     * @return A [Result] object containing the calculated y-coordinate as a [String] on success.
     *         On failure, it returns:
     *         - [NoNumbersInputException] if any input field does not contain a valid number.
     *         - [IdenticalXInputsException] if `inputX1` and `inputX2` are the same, which would cause a division by zero.
     */
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

        if (x1 != null &&
            y1 != null &&
            x2 != null &&
            y2 != null &&
            x3 != null
        ) {
            try {
                val y3 = calculate(
                    p1 = Point(x = x1, y = y1),
                    p2 = Point(x = x2, y = y2),
                    x3 = x3
                )
                return Result.success(y3.toPlainString())
            } catch (e: IdenticalXInputsException) {
                return Result.failure(e)
            }
        } else {
            return Result.failure(NoNumbersInputException())
        }
    }

    /**
     * Computes the linear interpolation for a given set of points.
     *
     * @throws IdenticalXInputsException if x1 and x2 are identical.*/
    private fun calculate(
        p1: Point,
        p2: Point,
        x3: BigDecimal
    ): BigDecimal {
        if (p1.x == p2.x) {
            throw IdenticalXInputsException()
        }

        // Formula: y3 = y1 + ((y2 - y1) * (x3 - x1)) / (x2 - x1)
        val numerator = (p2.y - p1.y) * (x3 - p1.x)
        val denominator = p2.x - p1.x
        val divisionResult = numerator.divide(
            denominator,
            MathContext.DECIMAL128
        )
        val y3 = p1.y + divisionResult
        return y3
    }

    private data class Point(val x: BigDecimal, val y: BigDecimal)

    class IdenticalXInputsException() : Exception(
        "The initial X values (x1 and x2) cannot be identical."
    )

    class NoNumbersInputException() : Exception(
        "All input fields must contain valid numbers."
    )
}
