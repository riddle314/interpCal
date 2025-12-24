package com.dimitriskatsikas.interpolator.info

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dimitriskatsikas.interpolator.Route

fun handleInfoEffect(
    effect: InfoView.Effect,
    backStack: SnapshotStateList<Route>
) {
    when (effect) {
        is InfoView.Effect.NavigateBack -> {
            backStack.removeLastOrNull()
        }
    }
}
