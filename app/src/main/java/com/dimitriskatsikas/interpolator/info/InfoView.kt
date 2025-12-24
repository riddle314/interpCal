package com.dimitriskatsikas.interpolator.info

object InfoView {

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}
