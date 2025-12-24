package com.dimitriskatsikas.interpolator.info

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class InfoViewModel {

    private val _effect: Channel<InfoView.Effect> = Channel(Channel.BUFFERED)
    val effect: Flow<InfoView.Effect> = _effect.receiveAsFlow()

    fun onUiAction(action: InfoView.UiAction) {
        when (action) {
            InfoView.UiAction.OnBackClicked -> _effect.trySend(
                InfoView.Effect.NavigateBack
            )
        }
    }
}
