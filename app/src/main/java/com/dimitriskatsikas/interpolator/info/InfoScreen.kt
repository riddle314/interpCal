package com.dimitriskatsikas.interpolator.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

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
