package com.uhc.feature.events

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import coil.compose.AsyncImage
import com.uhc.domain.events.model.Event
import com.uhc.feature.events.state.EventDetailsState
import com.uhc.lib.compose.utils.annotations.TicketMasterPreview
import com.uhc.lib.compose.utils.extensions.sharedElementIfAvailable
import com.uhc.lib.compose.utils.theme.LocalAnimatedVisibilityScope
import com.uhc.lib.compose.utils.theme.LocalSharedTransitionScope
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.lib.compose.utils.theme.dimensions
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EventDetailsLayout(
    eventId: String
) {
    val viewModel: EventDetailsViewModel = koinViewModel(parameters = { parametersOf(eventId) })
    val eventDetailsState by viewModel.eventDetailsState.collectAsState()

    when (eventDetailsState) {
        is EventDetailsState.Success -> {
            EventDetails(
                event = (eventDetailsState as EventDetailsState.Success).event
            )
        }

        is EventDetailsState.Error -> {
            EventsError(
                error = (eventDetailsState as EventDetailsState.Error).message,
                onRetry = viewModel::loadEvents
            )
        }

        EventDetailsState.Loading -> {
            EventsLoading()
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun EventDetails(
    event: Event
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    Column(modifier = Modifier.padding(MaterialTheme.dimensions.spacing.medium)) {
        AsyncImage(
            model = event.imageUrl,
            contentDescription = "Event Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .sharedElementIfAvailable(
                    key = "image${event.id}",
                    shared = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.spacing.small)
                .sharedElementIfAvailable(
                    key = "textName${event.id}",
                    shared = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .testTag("event_name"),
            text = event.name,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.spacing.small)
                .sharedElementIfAvailable(
                    key = "textVenue${event.id}",
                    shared = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .testTag("event_venue"),
            text = event.venue,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.spacing.small)
                .sharedElementIfAvailable(
                    key = "textDates${event.id}",
                    shared = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .testTag("event_dates"),
            text = event.dates,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@TicketMasterPreview
@Composable
private fun EventItemPreview() {
    TicketMasterTheme {
        EventDetails(
            event = Event(
                id = "1",
                name = "Sample Event",
                imageUrl = "",
                dates = "2023-10-01 to 2023-10-05",
                venue = "Sample Venue",
                favourite = false
            )
        )
    }
}
