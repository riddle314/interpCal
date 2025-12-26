package com.dimitriskatsikas.interpolator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dimitriskatsikas.interpolator.calculator.domain.ComputeLinearInterpolationUseCase
import com.dimitriskatsikas.interpolator.calculator.ui.CalculatorViewModel
import com.dimitriskatsikas.interpolator.calculator.ui.CalculatorViewModelFactory
import com.dimitriskatsikas.interpolator.calculator.ui.screen.CalculatorScreen
import com.dimitriskatsikas.interpolator.info.InfoViewModel
import com.dimitriskatsikas.interpolator.info.InfoViewModelFactory
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
                    viewModel = viewModel<CalculatorViewModel>(
                        factory = CalculatorViewModelFactory(
                            computeLinearInterpolationUseCase = ComputeLinearInterpolationUseCase()
                        )
                    ),
                    backStack = backStack
                )
            }
            entry<Route.Info> {
                InfoScreen(
                    viewModel = viewModel<InfoViewModel>(
                        factory = InfoViewModelFactory(
                            versionName = BuildConfig.VERSION_NAME
                        )
                    ),
                    backStack = backStack
                )
            }
        }
    )
}
