package com.dimitriskatsikas.interpolator.calculator

object CalculatorView {

    data class State(
        val inputX1: String = "",
        val inputY1: String = "",
        val inputX2: String = "",
        val inputY2: String = "",
        val inputX3: String = "",
        val result: String = "",
        val ctaState: CtaState = CtaState.Disabled
    ) {

        sealed interface CtaState {
            data object Enabled : CtaState
            data object Disabled : CtaState
            data object Loading : CtaState
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
}
