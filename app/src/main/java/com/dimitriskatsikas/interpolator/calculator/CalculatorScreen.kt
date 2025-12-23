package com.dimitriskatsikas.interpolator.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.calculator.CalculatorView.UiAction
import com.dimitriskatsikas.interpolator.utils.Previews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculator(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.header_title)) },
                actions = {
//                    IconButton(onClick = { onAction(UiAction.OpenInfoScreen) }) {
//                        Icon(
//                            imageVector = Icons.Filled.Info,
//                            contentDescription = stringResource(
//                                R.string.info_icon_content_description
//                            )
//                        )
//                    }
                }
            )
        },
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
    onAction: (UiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter values:")
        Spacer(Modifier.height(16.dp))
        TextField(
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
            label = { Text("x1") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
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
            label = { Text("y1") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
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
            label = { Text("x2") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
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
            label = { Text("y2") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
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
            label = { Text("x3") }
        )
        Spacer(Modifier.height(16.dp))
        CalculateButton(state, onAction)
        Spacer(Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(UiAction.Clear) },
            enabled = true
        ) {
            Text(text = "Clear fields")
        }
        Spacer(Modifier.height(16.dp))
        Text(text = "Result is: ${state.result}")
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
            Text(text = "Calculate")
        }

        CalculatorView.State.CtaState.Enabled -> Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(UiAction.Calculate) }
        ) {
            Text(text = "Calculate")
        }

        CalculatorView.State.CtaState.Loading -> Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            enabled = false
        ) {
            Text(text = "Calculating...")
        }
    }
}

@Previews
@Composable
private fun CalculatorPreview(
    @PreviewParameter(CalculatorPreviewStateProvider::class) state: CalculatorView.State
) {
    MaterialTheme {
        Calculator(
            state = state,
            onAction = {}
        )
    }
}
