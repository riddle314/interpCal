package com.dimitriskatsikas.interpolator.calculator

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.Route
import com.dimitriskatsikas.interpolator.calculator.CalculatorView.UiAction
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorContent(
    state: CalculatorView.State,
    snackbarHostState: SnackbarHostState,
    onAction: (UiAction) -> Unit
) {
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
    ) {
        Spacer(Modifier.height(24.dp))
        StartPointSection(state, onAction)
        Spacer(Modifier.height(24.dp))
        EndPointSection(state, onAction)
        Spacer(Modifier.height(24.dp))
        TargetValueSection(state, onAction)
        Spacer(Modifier.height(24.dp))
        if (state.result.isNotEmpty()) {
            ResultCard(result = state.result)
            Spacer(Modifier.height(24.dp))
        }
        CalculateButton(
            state = state,
            onAction = onAction
        )
        Spacer(Modifier.height(12.dp))
        ClearButton(onAction)
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(50.dp))
    }
}

@Composable
private fun StartPointSection(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    SectionHeader(text = stringResource(R.string.start_point_header))
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
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
            label = stringResource(R.string.input_label_x1),
            modifier = Modifier.weight(1f)
        )
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
            label = stringResource(R.string.input_label_y1),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EndPointSection(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    SectionHeader(text = stringResource(R.string.end_point_header))
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
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
            label = stringResource(R.string.input_label_x2),
            modifier = Modifier.weight(1f)
        )
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
            label = stringResource(R.string.input_label_y2),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TargetValueSection(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    SectionHeader(text = stringResource(R.string.target_value_header))
    OutlinedTextField(
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
        label = { Text(stringResource(R.string.input_label_x3)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (state.ctaState == CalculatorView.State.CtaState.Enabled) {
                    onAction(UiAction.Calculate)
                }
            }
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ClearButton(onAction: (UiAction) -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = { onAction(UiAction.Clear) },
        enabled = true
    ) {
        Text(text = stringResource(R.string.clear_fields))
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun CalculateButton(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    when (state.ctaState) {
        CalculatorView.State.CtaState.Disabled -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { },
            enabled = false
        ) {
            Text(text = stringResource(R.string.calculate))
        }

        CalculatorView.State.CtaState.Enabled -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { onAction(UiAction.Calculate) }
        ) {
            Text(text = stringResource(R.string.calculate))
        }

        CalculatorView.State.CtaState.Loading -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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
    label: String,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        modifier = modifier
    )
}

@Composable
private fun ResultCard(result: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.result),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = result,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Previews
@Composable
private fun CalculatorPreview(
    @PreviewParameter(CalculatorPreviewStateProvider::class) state: CalculatorView.State
) {
    InterpolatorTheme {
        CalculatorContent(
            state = state,
            snackbarHostState = SnackbarHostState(),
            onAction = {}
        )
    }
}
