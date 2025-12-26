package com.dimitriskatsikas.interpolator.info.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.info.InfoView
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoContent(
    state: InfoView.State,
    onAction: (InfoView.UiAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.info_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(InfoView.UiAction.OnBackClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back_icon_content_description
                            )
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Content(
                paddingValues = paddingValues,
                state = state
            )
        }
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: InfoView.State,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
    ) {
        InfoItem(
            title = stringResource(R.string.info_app_name_title),
            subtitle = stringResource(R.string.full_app_name)
        )
        InfoItem(
            title = stringResource(R.string.info_version_title),
            subtitle = state.versionName
        )
        InfoItem(
            title = stringResource(R.string.info_creator_title),
            subtitle = stringResource(R.string.creator_name)
        )
    }
}

@Composable
private fun InfoItem(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Previews
@Composable
private fun InfoContentPreview() {
    InterpolatorTheme {
        InfoContent(
            state = InfoView.State(versionName = "1.0"),
            onAction = {}
        )
    }
}
