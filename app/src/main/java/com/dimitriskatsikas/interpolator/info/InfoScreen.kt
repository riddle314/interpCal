package com.dimitriskatsikas.interpolator.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

@Composable
fun InfoScreen(
    viewModel: InfoViewModel,
    onEffect: (InfoView.Effect) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect -> onEffect(effect) }
    }

    InfoContent(
        onAction = viewModel::onUiAction
    )
}

@Composable
private fun InfoContent(onAction: (InfoView.UiAction) -> Unit) {

}

@Previews
@Composable
private fun InfoPreview() {
    InterpolatorTheme {
        InfoContent(
            onAction = {}
        )
    }
}
