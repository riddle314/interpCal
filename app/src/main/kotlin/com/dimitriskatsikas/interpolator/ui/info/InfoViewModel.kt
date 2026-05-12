package com.dimitriskatsikas.interpolator.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimitriskatsikas.interpolator.di.VersionName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    @VersionName versionName: String
) : ViewModel() {

    val state: StateFlow<InfoView.State> = MutableStateFlow(InfoView.State(versionName = versionName))

    private val _effect: Channel<InfoView.Effect> = Channel(Channel.CONFLATED)
    val effect: Flow<InfoView.Effect> = _effect.receiveAsFlow()

    fun onUiAction(action: InfoView.UiAction) {
        when (action) {
            InfoView.UiAction.OnBackClicked -> sendEffect(
                InfoView.Effect.NavigateBack
            )

            is InfoView.UiAction.OnPolicyClicked -> sendEffect(
                InfoView.Effect.NavigateToPolicy(url = action.url)
            )

            is InfoView.UiAction.OnRateClicked -> sendEffect(
                InfoView.Effect.NavigateToRate(url = action.url)
            )
        }
    }

    private fun sendEffect(effect: InfoView.Effect) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.send(effect)
        }
    }
}
