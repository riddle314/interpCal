package com.dimitriskatsikas.interpolator.ui.calculator.components

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.ui.calculator.CalculatorView
import com.dimitriskatsikas.interpolator.ui.calculator.CalculatorView.UiAction
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorContent(
    state: CalculatorView.State,
    snackbarHostState: SnackbarHostState,
    onAction: (UiAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.full_app_name)) },
                actions = {
                    IconButton(onClick = { onAction(UiAction.ShowExplainerDialog) }) {
                        Icon(
                            imageVector = Icons.Outlined.Lightbulb,
                            contentDescription = stringResource(
                                id = R.string.help_icon_content_description
                            )
                        )
                    }
                    IconButton(onClick = { onAction(UiAction.OpenInfoScreen) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(
                                R.string.info_icon_content_description
                            )
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AdBanner(
                modifier = Modifier.padding(
                    WindowInsets.navigationBars.asPaddingValues()
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Surface(modifier = Modifier.fillMaxSize()) {
                if (state.isExplainerDialogVisible) {
                    ExplainerDialog(
                        onDismissRequest = { onAction(UiAction.DismissExplainerDialog) },
                        modifier = Modifier
                    )
                }
                MainContent(
                    state = state,
                    paddingValues = paddingValues,
                    onAction = onAction
                )
            }
        }
    )
}

@Composable
fun MainContent(
    state: CalculatorView.State,
    paddingValues: PaddingValues,
    onAction: (UiAction) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
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
        Spacer(Modifier.height(24.dp))
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
    val focusManager = LocalFocusManager.current
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
                focusManager.clearFocus()
                if (state.ctaState == CalculatorView.State.CtaState.Enabled) {
                    onAction(UiAction.Calculate)
                }
            }
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
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
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun CalculateButton(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    val buttonHeight = 50.dp
    val keyboardController = LocalSoftwareKeyboardController.current
    when (state.ctaState) {
        CalculatorView.State.CtaState.Disabled -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            onClick = { },
            enabled = false
        ) {
            Text(text = stringResource(R.string.calculate))
        }

        CalculatorView.State.CtaState.Enabled -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            onClick = {
                keyboardController?.hide()
                onAction(UiAction.Calculate)
            }
        ) {
            Text(text = stringResource(R.string.calculate))
        }

        CalculatorView.State.CtaState.Loading -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            onClick = { }
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 3.dp
            )
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
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
private fun ResultCard(
    result: String
) {
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(ClipboardManager::class.java)
    val interactionSource = remember { MutableInteractionSource() }
    val haptic = LocalHapticFeedback.current
    val toastMessage = stringResource(id = R.string.result_copied_toast_message)
    val resultLabel = stringResource(id = R.string.copied_result_label)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    val clip = ClipData.newPlainText(resultLabel, result)
                    clipboardManager?.setPrimaryClip(clip)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        Toast.makeText(
                            context,
                            toastMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.result_y),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = stringResource(R.string.tap_to_copy_label),
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            Text(
                text = result,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Previews
@Composable
private fun CalculatorContentPreview(
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
