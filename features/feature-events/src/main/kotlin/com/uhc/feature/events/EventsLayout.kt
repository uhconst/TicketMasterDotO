package com.uhc.feature.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.uhc.domain.events.model.Event
import com.uhc.feature.events.state.EventState
import com.uhc.lib.compose.utils.annotations.TicketMasterPreview
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.lib.compose.utils.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventsLayout() {
    val viewModel: EventListViewModel = koinViewModel()

    val eventState by viewModel.eventState.collectAsState()

    when (eventState) {
        is EventState.Success -> {
            EventsList(
                eventState = eventState as EventState.Success,
                onRefresh = { viewModel.loadEvents() },
                onFavouriteClick = { event -> viewModel.onClickFavouriteEvent(event) }
            )
        }

        is EventState.Error -> {
            EventsError(error = eventState as EventState.Error)
        }

        EventState.Loading -> EventsLoading()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsList(
    eventState: EventState.Success,
    onRefresh: () -> Unit,
    onFavouriteClick: (Event) -> Unit
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = onRefresh,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = false,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = state
            )
        },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(MaterialTheme.dimensions.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacing.small),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = eventState.events,
                key = { it.id }
            ) { event ->
                EventItemCard(
                    modifier = Modifier.animateItem(),
                    event = event,
                    onFavouriteClick = { onFavouriteClick(event) }
                )
            }
        }
    }
}

@Composable
internal fun EventItemCard(
    modifier: Modifier = Modifier,
    event: Event,
    onFavouriteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(MaterialTheme.dimensions.spacing.small)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = "Event Image",
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(1.8f)
                    .padding(end = MaterialTheme.dimensions.spacing.small)
            )
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(end = MaterialTheme.dimensions.spacing.small)
            ) {
                Text(
                    modifier = Modifier.testTag("event_name"),
                    text = event.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.testTag("event_venue"),
                    text = event.venue,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.testTag("event_dates"),
                    text = event.dates,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier.testTag("event_favourite_icon"),
                onClick = onFavouriteClick
            ) {
                Icon(
                    imageVector = if (event.favourite) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Favourite"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsError(
    error: EventState.Error,
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimensions.spacing.medium)
                .size(MaterialTheme.dimensions.iconSize.xLarge)
        )
        Text(
            text = error.message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimensions.spacing.large)
                .testTag("error_message")
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.testTag("retry_button")
        ) {
            Text(
                modifier = Modifier.testTag("retry_button_text"),
                text = "Retry"
            )
        }
    }
}

@Composable
internal fun EventsLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.spacing.large)
                .testTag("loading_text")
        )
    }
}

@TicketMasterPreview
@Composable
private fun EventItemPreview() {
    TicketMasterTheme {
        EventItemCard(
            event = Event(
                id = "1",
                name = "Sample Event",
                imageUrl = "",
                dates = "2023-10-01 to 2023-10-05",
                venue = "Sample Venue",
                favourite = false
            ),
            onFavouriteClick = { }
        )
    }
}

@TicketMasterPreview
@Composable
private fun EventsLayoutPreview() {
    TicketMasterTheme {
        EventsList(
            eventState = EventState.Success(
                listOf(
                    Event(
                        id = "1",
                        name = "New York Yankees vs Boston Red Sox",
                        venue = "Yankee Stadium",
                        dates = "2023-10-01 to 2023-10-02",
                        imageUrl = "",
                        favourite = false
                    ),
                    Event(
                        id = "2",
                        name = "Hamilton",
                        venue = "Richard Rodgers Theatre",
                        dates = "2023-10-03",
                        imageUrl = "",
                        favourite = true
                    ),
                    Event(
                        id = "3",
                        name = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
                        venue = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
                        dates = "2023-10-03",
                        imageUrl = "",
                        favourite = true
                    )
                )
            ),
            onRefresh = {},
            onFavouriteClick = {}
        )
    }
}

@TicketMasterPreview
@Composable
private fun EventsErrorPreview() {
    TicketMasterTheme {
        EventsError(error = EventState.Error("Failed to load events"))
    }
}

@TicketMasterPreview
@Composable
private fun EventsLoadingPreview() {
    TicketMasterTheme {
        EventsLoading()
    }
}