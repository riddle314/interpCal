package com.dimitriskatsikas.interpolator.ui.calculator

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.Route
import com.dimitriskatsikas.interpolator.ui.calculator.components.CalculatorContent

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    backStack: SnapshotStateList<Route>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val identicalXInputsErrorMessage = stringResource(id = R.string.error_identical_x_inputs)
    val noNumbersInputErrorMessage = stringResource(id = R.string.error_no_numbers_input)
    val unknownErrorMessage = stringResource(id = R.string.calculator_error_unknown)

    CalculatorContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onUiAction
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            handleEffect(
                effect,
                backStack,
                snackbarHostState,
                identicalXInputsErrorMessage,
                noNumbersInputErrorMessage,
                unknownErrorMessage
            )
        }
    }
}

private suspend fun handleEffect(
    effect: CalculatorView.Effect,
    backStack: SnapshotStateList<Route>,
    snackbarHostState: SnackbarHostState,
    identicalXInputsErrorMessage: String,
    noNumbersInputErrorMessage: String,
    unknownErrorMessage: String
) {
    when (effect) {
        is CalculatorView.Effect.OpenInfoScreen -> {
            backStack.add(Route.Info)
        }

        is CalculatorView.Effect.ShowErrorToast -> {
            when (effect.errorType) {
                CalculatorView.ErrorType.IdenticalXInputs -> {
                    snackbarHostState.showSnackbar(identicalXInputsErrorMessage)
                }

                CalculatorView.ErrorType.NoNumbersInput -> {
                    snackbarHostState.showSnackbar(noNumbersInputErrorMessage)
                }

                CalculatorView.ErrorType.Unknown -> {
                    snackbarHostState.showSnackbar(unknownErrorMessage)
                }
            }
        }
    }
}
