package com.dimitriskatsikas.interpolator.info

object InfoView {

    data class State(
        val versionName: String = ""
    )

    sealed interface Effect {
        data object NavigateBack : Effect
    }

    sealed interface UiAction {
        data object OnBackClicked : UiAction
    }
}
