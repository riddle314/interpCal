package com.dimitriskatsikas.interpolator.info.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.info.InfoView
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoContent(onAction: (InfoView.UiAction) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.info_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(InfoView.UiAction.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(
                                R.string.info_icon_content_description
                            )
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Content(
                paddingValues = paddingValues
            )
        }
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
    ) {}
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
