package com.dimitriskatsikas.interpolator.info.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dimitriskatsikas.interpolator.Route
import com.dimitriskatsikas.interpolator.info.InfoView
import com.dimitriskatsikas.interpolator.info.InfoViewModel

@Composable
fun InfoScreen(
    viewModel: InfoViewModel,
    backStack: SnapshotStateList<Route>,
) {
    InfoContent(
        onAction = viewModel::onUiAction
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect -> handleEffect(effect, backStack) }
    }
}

private fun handleEffect(
    effect: InfoView.Effect,
    backStack: SnapshotStateList<Route>
) {
    when (effect) {
        is InfoView.Effect.NavigateBack -> {
            backStack.removeLastOrNull()
        }
    }
}
