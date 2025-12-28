package com.dimitriskatsikas.interpolator.utils

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Default | x1 font | Light mode",
    device = Devices.DEFAULT,
    fontScale = 1f,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Default | x1 font | Dark mode",
    device = Devices.DEFAULT,
    fontScale = 1f,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Small | x2 font",
    device = Devices.PIXEL_2,
    fontScale = 2f,
    showSystemUi = true
)
@Preview(
    name = "Tablet | x1 font",
    device = "spec:width=800dp,height=1280dp,dpi=240",
    fontScale = 1f,
    showSystemUi = true
)
annotation class Previews
