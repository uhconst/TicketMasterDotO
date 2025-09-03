package com.uhc.feature.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.annotations.TicketMasterPreview
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.lib.compose.utils.theme.dimensions

@Composable
fun AboutLayout() {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.dimensions.spacing.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacing.medium))

        Text(
            modifier = Modifier.testTag("about_name"),
            text = stringResource(R.string.name),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.testTag("about_role"),
            text = stringResource(R.string.role),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacing.medium))

        Text(
            modifier = Modifier.testTag("about_bio"),
            text = stringResource(R.string.bio),
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacing.large))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(MaterialTheme.dimensions.spacing.medium)) {
                Text(
                    modifier = Modifier.testTag("about_this_app_title"),
                    text = stringResource(R.string.about_this_app),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacing.small))
                Text(
                    modifier = Modifier.testTag("about_app_description"),
                    text = stringResource(R.string.app_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spacing.large))

        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier.testTag("about_github_button"),
                onClick = { uriHandler.openUri("https://github.com/uhconst") }
            ) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = stringResource(R.string.github),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacing.small))
                Text(stringResource(R.string.github))
            }

            OutlinedButton(
                modifier = Modifier.testTag("about_linkedin_button"),
                onClick = { uriHandler.openUri("https://www.linkedin.com/in/uryel-constancio-49247384/") }
            ) {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = stringResource(R.string.linkedin),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimensions.spacing.small))
                Text(stringResource(R.string.linkedin))
            }
        }
    }
}

@TicketMasterPreview
@Composable
private fun AboutLayoutPreview() {
    TicketMasterTheme {
        AboutLayout()
    }
}
