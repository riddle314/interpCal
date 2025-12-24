package com.dimitriskatsikas.interpolator.calculator

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.calculator.CalculatorView.UiAction
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    onEffect: (CalculatorView.Effect) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect -> onEffect(effect) }
    }

    CalculatorContent(
        state = state,
        onAction = viewModel::onUiAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorContent(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.header_title)) },
                actions = {
                    IconButton(onClick = { onAction(UiAction.OpenInfoScreen) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(
                                R.string.info_icon_content_description
                            )
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            MainContent(
                state = state,
                paddingValues = paddingValues,
                onAction = onAction
            )
        }
    )

    when (state.error) {
        CalculatorView.State.Error.IdenticalXInputs -> {
            val message = stringResource(id = R.string.error_identical_x_inputs)
            LaunchedEffect(state.error) {
                snackbarHostState.showSnackbar(message)
            }
        }

        null -> Unit
    }
}

@Composable
fun MainContent(
    state: CalculatorView.State,
    paddingValues: PaddingValues,
    onAction: (UiAction) -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(R.string.enter_values))
        Spacer(Modifier.height(16.dp))
        DecimalInputField(
            value = state.inputX1,
            onValueChange = { newText ->
                onAction(
                    UiAction.InputChange(
                        inputX1 = newText,
                        inputY1 = state.inputY1,
                        inputX2 = state.inputX2,
                        inputY2 = state.inputY2,
                        inputX3 = state.inputX3
                    )
                )
            },
            label = stringResource(R.string.input_label_x1)
        )
        Spacer(Modifier.height(16.dp))
        DecimalInputField(
            value = state.inputY1,
            onValueChange = { newText ->
                onAction(
                    UiAction.InputChange(
                        inputX1 = state.inputX1,
                        inputY1 = newText,
                        inputX2 = state.inputX2,
                        inputY2 = state.inputY2,
                        inputX3 = state.inputX3
                    )
                )
            },
            label = stringResource(R.string.input_label_y1)
        )
        Spacer(Modifier.height(16.dp))
        DecimalInputField(
            value = state.inputX2,
            onValueChange = { newText ->
                onAction(
                    UiAction.InputChange(
                        inputX1 = state.inputX1,
                        inputY1 = state.inputY1,
                        inputX2 = newText,
                        inputY2 = state.inputY2,
                        inputX3 = state.inputX3
                    )
                )
            },
            label = stringResource(R.string.input_label_x2)
        )
        Spacer(Modifier.height(16.dp))
        DecimalInputField(
            value = state.inputY2,
            onValueChange = { newText ->
                onAction(
                    UiAction.InputChange(
                        inputX1 = state.inputX1,
                        inputY1 = state.inputY1,
                        inputX2 = state.inputX2,
                        inputY2 = newText,
                        inputX3 = state.inputX3
                    )
                )
            },
            label = stringResource(R.string.input_label_y2)
        )
        Spacer(Modifier.height(16.dp))
        DecimalInputField(
            value = state.inputX3,
            onValueChange = { newText ->
                onAction(
                    UiAction.InputChange(
                        inputX1 = state.inputX1,
                        inputY1 = state.inputY1,
                        inputX2 = state.inputX2,
                        inputY2 = state.inputY2,
                        inputX3 = newText
                    )
                )
            },
            label = stringResource(R.string.input_label_x3)
        )
        Spacer(Modifier.height(16.dp))
        CalculateButton(
            state = state,
            onAction = onAction
        )
        Spacer(Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(UiAction.Clear) },
            enabled = true
        ) {
            Text(text = stringResource(R.string.clear_fields))
        }
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.result, state.result))
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun CalculateButton(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    when (state.ctaState) {
        CalculatorView.State.CtaState.Disabled -> Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            enabled = false
        ) {
            Text(text = stringResource(R.string.calculate))
        }

        CalculatorView.State.CtaState.Enabled -> Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(UiAction.Calculate) }
        ) {
            Text(text = stringResource(R.string.calculate))
        }

        CalculatorView.State.CtaState.Loading -> Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            enabled = false
        ) {
            Text(text = stringResource(R.string.calculating))
        }
    }
}

@Composable
private fun DecimalInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Previews
@Composable
private fun CalculatorPreview(
    @PreviewParameter(CalculatorPreviewStateProvider::class) state: CalculatorView.State
) {
    InterpolatorTheme {
        CalculatorContent(
            state = state,
            onAction = {}
        )
    }
}
