package com.dimitriskatsikas.interpolator.ui.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.interpolator.Route
import com.dimitriskatsikas.interpolator.ui.info.components.InfoContent

@Composable
fun InfoScreen(
    viewModel: InfoViewModel,
    backStack: SnapshotStateList<Route>,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    InfoContent(
        state = state,
        onAction = viewModel::onUiAction
    )

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->
            handleEffect(
                effect = effect,
                backStack = backStack,
                uriHandler = uriHandler
            )
        }
    }
}

private fun handleEffect(
    effect: InfoView.Effect,
    backStack: SnapshotStateList<Route>,
    uriHandler: UriHandler,
) {
    when (effect) {
        is InfoView.Effect.NavigateBack -> {
            backStack.removeLastOrNull()
        }

        is InfoView.Effect.NavigateToPolicy -> uriHandler.openUri(effect.url)
        is InfoView.Effect.NavigateToRate -> uriHandler.openUri(effect.url)
    }
}
