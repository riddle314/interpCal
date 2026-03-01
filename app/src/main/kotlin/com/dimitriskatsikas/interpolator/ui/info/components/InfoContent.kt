package com.dimitriskatsikas.interpolator.ui.info.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.interpolator.R
import com.dimitriskatsikas.interpolator.ui.info.InfoView
import com.dimitriskatsikas.interpolator.ui.theme.InterpolatorTheme
import com.dimitriskatsikas.interpolator.utils.Previews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoContent(
    state: InfoView.State,
    onAction: (InfoView.UiAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { paddingValues ->
            Content(
                paddingValues = paddingValues,
                state = state,
                onAction = onAction
            )
        }
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: InfoView.State,
    onAction: (InfoView.UiAction) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        AppBranding(state = state)
        Spacer(modifier = Modifier.height(40.dp))
        DeveloperItem()
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        PrivacyPolicyItem(onAction = onAction)
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        RateAppItem(onAction = onAction)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AppBranding(state: InfoView.State) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = stringResource(R.string.app_icon_content_description),
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(R.color.ic_launcher_background))
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.full_app_name),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Text(
        text = state.versionName,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun DeveloperItem() {
    ListItem(
        headlineContent = { Text(stringResource(R.string.developed_by)) },
        supportingContent = { Text(stringResource(R.string.developer_name)) },
        leadingContent = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null
            )
        }
    )
}

@Composable
private fun PrivacyPolicyItem(onAction: (InfoView.UiAction) -> Unit) {
    val privacyPolicyUrl = stringResource(R.string.privacy_policy_url)
    ListItem(
        modifier = Modifier.clickable {
            onAction(InfoView.UiAction.OnPolicyClicked(url = privacyPolicyUrl))
        },
        headlineContent = { Text(stringResource(R.string.privacy_policy_title)) },
        leadingContent = {
            Icon(
                imageVector = Icons.Outlined.PrivacyTip,
                contentDescription = null
            )
        },
        trailingContent = {
            Icon(
                Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    )
}

@Composable
private fun RateAppItem(onAction: (InfoView.UiAction) -> Unit) {
    val context = LocalContext.current
    val appPackageName = context.packageName
    val googlePlayStoreUrl = stringResource(
        id = R.string.google_play_store_url,
        appPackageName
    )
    ListItem(
        modifier = Modifier.clickable {
            onAction(InfoView.UiAction.OnRateClicked(url = googlePlayStoreUrl))
        },
        headlineContent = { Text(stringResource(R.string.rate_this_app)) },
        supportingContent = { Text(stringResource(R.string.rate_this_app_subtitle)) },
        leadingContent = {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    )
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
