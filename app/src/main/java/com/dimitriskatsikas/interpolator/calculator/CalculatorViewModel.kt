package com.dimitriskatsikas.interpolator.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _effect: Channel<CalculatorView.Effect> = Channel(Channel.BUFFERED)
    val effect: Flow<CalculatorView.Effect> = _effect.receiveAsFlow()

    fun onUiAction(action: CalculatorView.UiAction) {
        when (action) {
            is CalculatorView.UiAction.Calculate -> calculate()
            is CalculatorView.UiAction.InputChange -> updateCta(action)
            CalculatorView.UiAction.Clear -> clearState()
            CalculatorView.UiAction.OpenInfoScreen -> {
                _effect.trySend(CalculatorView.Effect.OpenInfoScreen)
            }
        }
    }

    private fun calculate() {
        _state.update {
            it.copy(ctaState = CalculatorView.State.CtaState.Loading)
        }
        viewModelScope.launch(Dispatchers.Default) {
            val currentState = _state.value
            val result = computeLinearInterpolation(
                inputX1 = currentState.inputX1,
                inputY1 = currentState.inputY1,
                inputX2 = currentState.inputX2,
                inputY2 = currentState.inputY2,
                inputX3 = currentState.inputX3
            )

            _state.update {
                it.copy(
                    result = result,
                    ctaState = CalculatorView.State.CtaState.Enabled
                )
            }
        }
    }

    private fun updateCta(action: CalculatorView.UiAction.InputChange) {
        val areAllFieldsFilled = action.inputX1.isNotEmpty() &&
                action.inputY1.isNotEmpty() &&
                action.inputX2.isNotEmpty() &&
                action.inputY2.isNotEmpty() &&
                action.inputX3.isNotEmpty()

        val areAllFieldsNumbers = action.inputX1.toBigDecimalOrNull() != null &&
                action.inputY1.toBigDecimalOrNull() != null &&
                action.inputX2.toBigDecimalOrNull() != null &&
                action.inputY2.toBigDecimalOrNull() != null &&
                action.inputX3.toBigDecimalOrNull() != null

        _state.update {
            it.copy(
                inputX1 = action.inputX1,
                inputY1 = action.inputY1,
                inputX2 = action.inputX2,
                inputY2 = action.inputY2,
                inputX3 = action.inputX3,
                ctaState = if (areAllFieldsFilled && areAllFieldsNumbers) {
                    CalculatorView.State.CtaState.Enabled
                } else {
                    CalculatorView.State.CtaState.Disabled
                }
            )
        }
    }

    private suspend fun computeLinearInterpolation(
        inputX1: String,
        inputY1: String,
        inputX2: String,
        inputY2: String,
        inputX3: String
    ): String {
        // TODO something seems not working on calculations
        val x1 = inputX1.toBigDecimal()
        val y1 = inputY1.toBigDecimal()
        val x2 = inputX2.toBigDecimal()
        val y2 = inputY2.toBigDecimal()
        val x3 = inputX3.toBigDecimal()

        if (x1 == x2) {
            return "Please provide different values for x1 and x2"
        } else {
            val result = ((y2 - y1) / (x2 - x1)) * (x3 - x1) + y1
            return result.toString()
        }
    }

    private fun clearState() {
        _state.update {
            it.copy(
                inputX1 = "",
                inputY1 = "",
                inputX2 = "",
                inputY2 = "",
                inputX3 = "",
                result = "",
                ctaState = CalculatorView.State.CtaState.Disabled
            )
        }
    }
}
