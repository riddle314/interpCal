package com.dimitriskatsikas.interpolator.info.screen

import androidx.compose.runtime.Composable
import com.dimitriskatsikas.interpolator.info.InfoView
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

@Composable
fun InfoContent(onAction: (InfoView.UiAction) -> Unit) {

}

@Previews
@Composable
private fun InfoContentPreview() {
    InterpolatorTheme {
        InfoContent(
            onAction = {}
        )
    }
}
