package com.dimitriskatsikas.interpolator.calculator

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dimitriskatsikas.interpolator.Route

fun handleCalculatorEffect(
    effect: CalculatorView.Effect,
    backStack: SnapshotStateList<Route>
) {
    when (effect) {
        is CalculatorView.Effect.OpenInfoScreen -> {
            backStack.add(Route.Info)
        }
    }
}
