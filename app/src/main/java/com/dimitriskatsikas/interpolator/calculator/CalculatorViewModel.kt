package com.dimitriskatsikas.interpolator.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorView.State())

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalculatorView.State()
    )

    fun onUiAction(action: CalculatorView.UiAction) {
        when (action) {
            is CalculatorView.UiAction.Calculate -> calculate(action)
            is CalculatorView.UiAction.InputChange -> updateCta(action)
            CalculatorView.UiAction.Clear -> TODO()
            CalculatorView.UiAction.OpenInfoScreen -> TODO()
        }
    }

    private fun calculate(action: CalculatorView.UiAction.Calculate) {
        _state.update {
            it.copy(ctaState = CalculatorView.State.CtaState.Loading)
        }
        viewModelScope.launch(Dispatchers.Default) {
            val result = computeLinearInterpolation(
                inputX1 = action.inputX1,
                inputY1 = action.inputY1,
                inputX2 = action.inputX2,
                inputY2 = action.inputY2,
                inputX3 = action.inputX3
            )

            _state.update {
                it.copy(
                    result = result,
                    ctaState = CalculatorView.State.CtaState.Enabled
                )
            }
        }
    }

    private fun updateCta(inputChange: CalculatorView.UiAction.InputChange) {
        if (inputChange.inputX1.isNotEmpty() &&
            inputChange.inputY1.isNotEmpty() &&
            inputChange.inputX2.isNotEmpty() &&
            inputChange.inputY2.isNotEmpty() &&
            inputChange.inputX3.isNotEmpty()
        ) {
            _state.update {
                it.copy(ctaState = CalculatorView.State.CtaState.Enabled)
            }
        } else {
            _state.update {
                it.copy(ctaState = CalculatorView.State.CtaState.Disabled)
            }
        }
    }

    private suspend fun computeLinearInterpolation(
        inputX1: String,
        inputY1: String,
        inputX2: String,
        inputY2: String,
        inputX3: String
    ): String {
        return "32"
    }
}
