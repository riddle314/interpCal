package com.dimitriskatsikas.interpolator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.dimitriskatsikas.interpolator.calculator.CalculatorScreen
import com.dimitriskatsikas.interpolator.calculator.CalculatorViewModel
import com.dimitriskatsikas.interpolator.calculator.handleCalculatorEffect
import com.dimitriskatsikas.interpolator.info.InfoScreen
import com.dimitriskatsikas.interpolator.info.handleInfoEffect

@Composable
fun AppNavigation() {

    val backStack = remember { mutableStateListOf<Route>(Route.Calculator) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Route.Calculator -> NavEntry(key) {
                    CalculatorScreen(
                        viewModel = CalculatorViewModel(),
                        onEffect = {
                            handleCalculatorEffect(
                                effect = it,
                                backStack = backStack
                            )
                        }
                    )
                }

                is Route.Info -> NavEntry(key) {
                    InfoScreen(
                        onEffect = {
                            handleInfoEffect(
                                effect = it,
                                backStack = backStack
                            )
                        }
                    )
                }
            }
        }
    )
}
