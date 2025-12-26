package com.dimitriskatsikas.interpolator.info

import androidx.lifecycle.ViewModel
import com.dimitriskatsikas.interpolator.BuildConfig
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class InfoViewModel : ViewModel() {

    val state: StateFlow<InfoView.State> = MutableStateFlow(
        InfoView.State(versionName = BuildConfig.VERSION_NAME)
    ).asStateFlow()

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
