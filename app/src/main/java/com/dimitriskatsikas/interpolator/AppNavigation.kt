package com.dimitriskatsikas.interpolator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dimitriskatsikas.interpolator.calculator.CalculatorScreen
import com.dimitriskatsikas.interpolator.calculator.CalculatorViewModel
import com.dimitriskatsikas.interpolator.info.InfoScreen
import com.dimitriskatsikas.interpolator.info.InfoViewModel
import com.dimitriskatsikas.interpolator.info.handleInfoEffect

@Composable
fun AppNavigation() {

    val backStack = remember { mutableStateListOf<Route>(Route.Calculator) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Calculator> {
                CalculatorScreen(
                    viewModel = viewModel<CalculatorViewModel>(),
                    backStack = backStack
                )
            }
            entry<Route.Info> {
                InfoScreen(
                    viewModel = viewModel<InfoViewModel>(),
                    onEffect = {
                        handleInfoEffect(
                            effect = it,
                            backStack = backStack
                        )
                    }
                )
            }
        }
    )
}
