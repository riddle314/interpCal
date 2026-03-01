package com.dimitriskatsikas.interpolator.ui.info

object InfoView {

    data class State(
        val versionName: String = ""
    )

    sealed interface Effect {
        data object NavigateBack : Effect
        data class NavigateToPolicy(val url: String) : Effect
        data class NavigateToRate(val url: String) : Effect
    }

    sealed interface UiAction {
        data object OnBackClicked : UiAction
        data class OnPolicyClicked(val url: String) : UiAction
        data class OnRateClicked(val url: String) : UiAction
    }
}
