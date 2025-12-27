package com.dimitriskatsikas.interpolator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dimitriskatsikas.interpolator.calculator.ui.screen.CalculatorScreen
import com.dimitriskatsikas.interpolator.info.screen.InfoScreen

@Composable
fun AppNavigation() {

    val backStack = remember { mutableStateListOf<Route>(Route.Calculator) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Calculator> {
                CalculatorScreen(
                    viewModel = hiltViewModel(),
                    backStack = backStack
                )
            }
            entry<Route.Info> {
                InfoScreen(
                    viewModel = hiltViewModel(),
                    backStack = backStack
                )
            }
        }
    )
}
