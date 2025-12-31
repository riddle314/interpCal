package com.dimitriskatsikas.interpolator.calculator.ui.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dimitriskatsikas.interpolator.calculator.ui.CalculatorView
import com.dimitriskatsikas.interpolator.calculator.ui.CalculatorView.State.CtaState

class CalculatorPreviewStateProvider : PreviewParameterProvider<CalculatorView.State> {

    override val values = sequenceOf(
        CalculatorView.State(
            inputX1 = "",
            inputY1 = "",
            inputX2 = "",
            inputY2 = "",
            inputX3 = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            inputX1 = "",
            inputY1 = "",
            inputX2 = "",
            inputY2 = "",
            inputX3 = "",
            result = "",
            ctaState = CtaState.Disabled,
            isExplainerDialogVisible = true
        ),
        CalculatorView.State(
            inputX1 = "1",
            inputY1 = "100",
            inputX2 = "2",
            inputY2 = "200",
            inputX3 = "1.5",
            result = "",
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            inputX1 = "1",
            inputY1 = "100",
            inputX2 = "2",
            inputY2 = "200",
            inputX3 = "1.5",
            result = "",
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            inputX1 = "1",
            inputY1 = "100",
            inputX2 = "2",
            inputY2 = "200",
            inputX3 = "1.5",
            result = "150",
            ctaState = CtaState.Enabled
        )
    )
}
