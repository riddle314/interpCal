package com.dimitriskatsikas.interpolator.calculator.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.dimitriskatsikas.interpolator.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBanner() {
    // In preview mode it returns a placeholder because the admob requires a network connection.
    if (LocalInspectionMode.current) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Google Mobile Ads preview banner.",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = BuildConfig.BANNER_AD_UNIT_ID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
