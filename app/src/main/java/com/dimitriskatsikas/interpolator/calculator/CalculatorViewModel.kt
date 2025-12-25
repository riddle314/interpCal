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

private const val EMPTY_STRING = ""

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
            is CalculatorView.UiAction.InputChange -> onInputChange(action)
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
            computeLinearInterpolation(
                inputX1 = currentState.inputX1,
                inputY1 = currentState.inputY1,
                inputX2 = currentState.inputX2,
                inputY2 = currentState.inputY2,
                inputX3 = currentState.inputX3
            )
        }
    }

    private fun onInputChange(action: CalculatorView.UiAction.InputChange) {
        val areAllFieldsFilledWithNumbers = action.inputX1.toBigDecimalOrNull() != null &&
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
                ctaState = if (areAllFieldsFilledWithNumbers) {
                    CalculatorView.State.CtaState.Enabled
                } else {
                    CalculatorView.State.CtaState.Disabled
                },
                result = EMPTY_STRING
            )
        }
    }

    private suspend fun computeLinearInterpolation(
        inputX1: String,
        inputY1: String,
        inputX2: String,
        inputY2: String,
        inputX3: String
    ) {
        //TODO  I press calculate then I repress calculate and the calculation remains the same
        val x1 = inputX1.toBigDecimalOrNull()
        val y1 = inputY1.toBigDecimalOrNull()
        val x2 = inputX2.toBigDecimalOrNull()
        val y2 = inputY2.toBigDecimalOrNull()
        val x3 = inputX3.toBigDecimalOrNull()

        val areAllFieldsFilledWithNumbers = x1 != null &&
                y1 != null &&
                x2 != null &&
                y2 != null &&
                x3 != null

        if (areAllFieldsFilledWithNumbers) {
            if (x1 == x2) {
                _state.update {
                    it.copy(
                        result = EMPTY_STRING,
                        ctaState = CalculatorView.State.CtaState.Enabled,
                    )
                }
                _effect.trySend(
                    CalculatorView.Effect.ShowErrorToast(
                        CalculatorView.ErrorToast.IdenticalXInputs
                    )
                )
            } else {
                val result =
                    ((y2 - y1) / (x2 - x1)) * (x3 - x1) + y1 //TODO should I use a repository for the calculations?
                _state.update {
                    it.copy(
                        result = result.toString(),
                        ctaState = CalculatorView.State.CtaState.Enabled
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    result = EMPTY_STRING,
                    ctaState = CalculatorView.State.CtaState.Disabled,
                )
            }
            _effect.trySend(
                CalculatorView.Effect.ShowErrorToast(
                    CalculatorView.ErrorToast.NoNumbersInput
                )
            )
        }
    }

    private fun clearState() {
        _state.update {
            it.copy(
                inputX1 = EMPTY_STRING,
                inputY1 = EMPTY_STRING,
                inputX2 = EMPTY_STRING,
                inputY2 = EMPTY_STRING,
                inputX3 = EMPTY_STRING,
                result = EMPTY_STRING,
                ctaState = CalculatorView.State.CtaState.Disabled
            )
        }
    }
}
