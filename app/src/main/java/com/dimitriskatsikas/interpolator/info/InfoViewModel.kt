package com.dimitriskatsikas.interpolator.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class InfoViewModel(versionName: String) : ViewModel() {

    val state: StateFlow<InfoView.State> = MutableStateFlow(InfoView.State(versionName = versionName))

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

class InfoViewModelFactory(private val versionName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InfoViewModel(versionName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
