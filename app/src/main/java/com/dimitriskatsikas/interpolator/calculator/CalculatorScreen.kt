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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.calculator.CalculatorView.UiAction

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
    // Add states for the TextFields
    var inputX1 by remember { mutableStateOf("") }
    var inputY1 by remember { mutableStateOf("") }
    var inputX2 by remember { mutableStateOf("") }
    var inputY2 by remember { mutableStateOf("") }
    var inputX3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter values:"
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = inputX1,
            onValueChange = { newText ->
                inputX1 = newText
                onAction(
                    UiAction.InputChange(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3
                    )
                )
            },
            label = { Text("x1") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = inputY1,
            onValueChange = { newText ->
                inputY1 = newText
                onAction(
                    UiAction.InputChange(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3
                    )
                )
            },
            label = { Text("y1") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = inputX2,
            onValueChange = { newText ->
                inputX2 = newText
                onAction(
                    UiAction.InputChange(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3
                    )
                )
            },
            label = { Text("x2") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = inputY2,
            onValueChange = { newText ->
                inputY2 = newText
                onAction(
                    UiAction.InputChange(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3
                    )
                )
            },
            label = { Text("y2") }
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = inputX3,
            onValueChange = { newText ->
                inputX3 = newText
                onAction(
                    UiAction.InputChange(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3
                    )
                )
            },
            label = { Text("x3") }
        )
        Spacer(Modifier.height(16.dp))
        CtaButton(state, onAction, inputX1, inputY1, inputX2, inputY2, inputX3)
        Spacer(Modifier.height(16.dp))
        Text(text = "Result is ${state.result}")
    }
}

@Composable
private fun CtaButton(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit,
    inputX1: String,
    inputY1: String,
    inputX2: String,
    inputY2: String,
    inputX3: String
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
            onClick = {
                onAction(
                    UiAction.Calculate(
                        inputX1 = inputX1,
                        inputY1 = inputY1,
                        inputX2 = inputX2,
                        inputY2 = inputY2,
                        inputX3 = inputX3
                    )
                )
            }
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

@Preview
@Composable
private fun CalculatorPreview() {
    MaterialTheme {
        Calculator(
            state = CalculatorView.State(),
            onAction = {}
        )
    }
}
