package com.dimitriskatsikas.interpolator.calculator

private const val EMPTY_STRING = ""

object CalculatorView {

    data class State(
        val inputX1: String = EMPTY_STRING,
        val inputY1: String = EMPTY_STRING,
        val inputX2: String = EMPTY_STRING,
        val inputY2: String = EMPTY_STRING,
        val inputX3: String = EMPTY_STRING,
        val result: String = EMPTY_STRING,
        val ctaState: CtaState = CtaState.Disabled,
        val error: Error? = null
    ) {

        sealed interface CtaState {
            data object Enabled : CtaState
            data object Disabled : CtaState
            data object Loading : CtaState
        }

        sealed interface Error {
            data object IdenticalXInputs : Error
        }
    }

    sealed interface UiAction {

        data class InputChange(
            val inputX1: String,
            val inputY1: String,
            val inputX2: String,
            val inputY2: String,
            val inputX3: String
        ) : UiAction

        data object Calculate : UiAction
        data object Clear : UiAction
        data object OpenInfoScreen : UiAction
    }

    sealed interface Effect {
        data object OpenInfoScreen : Effect
    }
}
