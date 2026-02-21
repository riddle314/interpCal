package com.dimitriskatsikas.interpolator.ui.calculator.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dimitriskatsikas.interpolator.ui.calculator.CalculatorView

class CalculatorPreviewStateProvider : PreviewParameterProvider<CalculatorView.State> {

    override val values = sequenceOf(
        CalculatorView.State(
            inputX1 = "",
            inputY1 = "",
            inputX2 = "",
            inputY2 = "",
            inputX3 = "",
            result = "",
            ctaState = CalculatorView.State.CtaState.Disabled
        ),
        CalculatorView.State(
            inputX1 = "",
            inputY1 = "",
            inputX2 = "",
            inputY2 = "",
            inputX3 = "",
            result = "",
            ctaState = CalculatorView.State.CtaState.Disabled,
            isExplainerDialogVisible = true
        ),
        CalculatorView.State(
            inputX1 = "1",
            inputY1 = "100",
            inputX2 = "2",
            inputY2 = "200",
            inputX3 = "1.5",
            result = "",
            ctaState = CalculatorView.State.CtaState.Enabled
        ),
        CalculatorView.State(
            inputX1 = "1",
            inputY1 = "100",
            inputX2 = "2",
            inputY2 = "200",
            inputX3 = "1.5",
            result = "",
            ctaState = CalculatorView.State.CtaState.Loading
        ),
        CalculatorView.State(
            inputX1 = "1",
            inputY1 = "100",
            inputX2 = "2",
            inputY2 = "200",
            inputX3 = "1.5",
            result = "150",
            ctaState = CalculatorView.State.CtaState.Enabled
        ),
        // more test cases for screenshots
        CalculatorView.State(
            inputX1 = "0",
            inputY1 = "0",
            inputX2 = "100",
            inputY2 = "50",
            inputX3 = "50",
            result = "25",
            ctaState = CalculatorView.State.CtaState.Enabled
        ),
        CalculatorView.State(
            inputX1 = "1.5",
            inputY1 = "9.81",
            inputX2 = "3.5",
            inputY2 = "19.62",
            inputX3 = "2.5",
            result = "14.715",
            ctaState = CalculatorView.State.CtaState.Enabled
        )
    )
}
