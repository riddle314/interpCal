package com.dimitriskatsikas.interpolator.ui.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimitriskatsikas.interpolator.domain.LinearInterpolationCalculator
import com.dimitriskatsikas.interpolator.domain.IdenticalXInputsException
import com.dimitriskatsikas.interpolator.domain.NoNumbersInputException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val EMPTY_STRING = ""

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val linearInterpolationCalculator: LinearInterpolationCalculator
) : ViewModel() {

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

            CalculatorView.UiAction.DismissExplainerDialog -> setExplainerDialogVisibility(false)
            CalculatorView.UiAction.ShowExplainerDialog -> setExplainerDialogVisibility(true)
        }
    }

    private fun calculate() {
        _state.update {
            it.copy(
                result = EMPTY_STRING,
                ctaState = CalculatorView.State.CtaState.Loading
            )
        }
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                val currentState = _state.value
                linearInterpolationCalculator(
                    inputX1 = currentState.inputX1,
                    inputY1 = currentState.inputY1,
                    inputX2 = currentState.inputX2,
                    inputY2 = currentState.inputY2,
                    inputX3 = currentState.inputX3
                )
            }

            result.onSuccess { value ->
                _state.update {
                    it.copy(
                        result = value,
                        ctaState = CalculatorView.State.CtaState.Enabled
                    )
                }
            }.onFailure { exception ->
                _state.update {
                    it.copy(
                        result = EMPTY_STRING,
                        ctaState = CalculatorView.State.CtaState.Enabled,
                    )
                }
                when (exception) {
                    is NoNumbersInputException -> showNoNumbersInputErrorToast()
                    is IdenticalXInputsException -> showIdenticalXInputsErrorToast()
                    else -> showUnknownErrorToast()
                }
            }
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

    private fun showIdenticalXInputsErrorToast() {
        _effect.trySend(
            CalculatorView.Effect.ShowErrorToast(
                CalculatorView.ErrorType.IdenticalXInputs
            )
        )
    }

    private fun showNoNumbersInputErrorToast() {
        _effect.trySend(
            CalculatorView.Effect.ShowErrorToast(
                CalculatorView.ErrorType.NoNumbersInput
            )
        )
    }

    private fun setExplainerDialogVisibility(visible: Boolean) {
        _state.update {
            it.copy(
                isExplainerDialogVisible = visible
            )
        }
    }

    private fun showUnknownErrorToast() {
        _effect.trySend(
            CalculatorView.Effect.ShowErrorToast(
                CalculatorView.ErrorType.Unknown
            )
        )
    }
}
