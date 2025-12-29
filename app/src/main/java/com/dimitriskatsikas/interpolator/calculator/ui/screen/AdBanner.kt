package com.dimitriskatsikas.interpolator.calculator.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dimitriskatsikas.interpolator.BuildConfig
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBanner(modifier: Modifier) {
    Surface(
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (LocalInspectionMode.current) {
            // In preview mode, show a placeholder
            PreviewAdPlaceholder(modifier = Modifier.fillMaxWidth())
        } else {
            // On a real device, show the actual ad
            ActualAd()
        }
    }
}

@Composable
private fun ActualAd() {
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

@Composable
private fun PreviewAdPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(R.string.ad_banner_preview_text),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun AdBannerPreview() {
    InterpolatorTheme {
        AdBanner(modifier = Modifier)
    }
}
