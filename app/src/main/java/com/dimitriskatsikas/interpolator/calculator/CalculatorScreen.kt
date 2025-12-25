package com.dimitriskatsikas.interpolator.calculator

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

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    backStack: SnapshotStateList<Route>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val identicalXInputsErrorMessage = stringResource(id = R.string.error_identical_x_inputs)
    val noNumbersInputErrorMessage = stringResource(id = R.string.error_no_numbers_input)

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
                noNumbersInputErrorMessage
            )
        }
    }
}

private suspend fun handleEffect(
    effect: CalculatorView.Effect,
    backStack: SnapshotStateList<Route>,
    snackbarHostState: SnackbarHostState,
    identicalXInputsErrorMessage: String,
    noNumbersInputErrorMessage: String
) {
    when (effect) {
        is CalculatorView.Effect.OpenInfoScreen -> {
            backStack.add(Route.Info)
        }

        is CalculatorView.Effect.ShowErrorToast -> {
            when (effect.errorToast) {
                CalculatorView.ErrorToast.IdenticalXInputs -> {
                    snackbarHostState.showSnackbar(identicalXInputsErrorMessage)
                }

                CalculatorView.ErrorToast.NoNumbersInput -> {
                    snackbarHostState.showSnackbar(noNumbersInputErrorMessage)
                }
            }
        }
    }
}
