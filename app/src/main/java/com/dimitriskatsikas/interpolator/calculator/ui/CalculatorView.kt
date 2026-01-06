package com.dimitriskatsikas.interpolator.calculator.ui

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
        val isExplainerDialogVisible: Boolean = false
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
        data object ShowExplainerDialog : UiAction
        data object DismissExplainerDialog : UiAction
    }

    sealed interface Effect {
        data object OpenInfoScreen : Effect
        data class ShowErrorToast(val errorType: ErrorType) : Effect
    }

    sealed interface ErrorType {
        data object IdenticalXInputs : ErrorType
        data object NoNumbersInput : ErrorType
        data object Unknown : ErrorType
    }
}
