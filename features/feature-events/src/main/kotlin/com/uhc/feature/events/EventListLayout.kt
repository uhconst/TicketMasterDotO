package com.uhc.feature.events

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.uhc.domain.events.model.Event
import com.uhc.feature.events.state.EventListState
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.annotations.TicketMasterPreview
import com.uhc.lib.compose.utils.extensions.sharedElementIfAvailable
import com.uhc.lib.compose.utils.theme.LocalAnimatedVisibilityScope
import com.uhc.lib.compose.utils.theme.LocalSharedTransitionScope
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.lib.compose.utils.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventListLayout(
    onEventClick: (eventId: String) -> Unit = {},
) {
    val viewModel: EventListViewModel = koinViewModel()

    val eventListState by viewModel.eventListState.collectAsState()

    when (eventListState) {
        is EventListState.Success -> {
            EventsList(
                eventListState = eventListState as EventListState.Success,
                onRefresh = { viewModel.loadEvents() },
                onEventClick = { event -> onEventClick.invoke(event.id) },
                onFavouriteClick = { event -> viewModel.onClickFavouriteEvent(event) }
            )
        }

        is EventListState.Error -> {
            EventsError(
                error = (eventListState as EventListState.Error).message,
                onRetry = viewModel::loadEvents
            )
        }

        EventListState.Loading -> EventsLoading()
    }
}

@Composable
internal fun EventsList(
    eventListState: EventListState.Success,
    onRefresh: () -> Unit,
    onEventClick: (Event) -> Unit,
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
                items = eventListState.events,
                key = { it.id }
            ) { event ->
                EventItemCard(
                    modifier = Modifier.animateItem(),
                    event = event,
                    onEventClick = { onEventClick(event) },
                    onFavouriteClick = { onFavouriteClick(event) }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun EventItemCard(
    modifier: Modifier = Modifier,
    event: Event,
    onEventClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEventClick() }
    ) {
        Row(
            modifier = Modifier
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
                    .sharedElementIfAvailable(
                        key = "image${event.id}",
                        shared = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(end = MaterialTheme.dimensions.spacing.small)
            ) {
                Text(
                    modifier = Modifier
                        .sharedElementIfAvailable(
                            key = "textName${event.id}",
                            shared = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .testTag("event_name"),
                    text = event.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .sharedElementIfAvailable(
                            key = "textVenue${event.id}",
                            shared = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .testTag("event_venue"),
                    text = event.venue,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .sharedElementIfAvailable(
                            key = "textDates${event.id}",
                            shared = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .testTag("event_dates"),
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
                val scale by animateFloatAsState(
                    targetValue = if (event.favourite) 1.2f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
                Icon(
                    modifier = Modifier.scale(scale),
                    painter = painterResource(
                        id = if (event.favourite) R.drawable.favorite_filled_24px
                        else R.drawable.favorite_24px
                    ),
                    contentDescription = "Favourite"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsError(
    error: String,
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
            painter = painterResource(id = R.drawable.error_24px),
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimensions.spacing.medium)
                .size(MaterialTheme.dimensions.iconSize.xLarge)
        )
        Text(
            text = error,
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
            onFavouriteClick = { },
            onEventClick = { }
        )
    }
}

@TicketMasterPreview
@Composable
private fun EventsLayoutPreview() {
    TicketMasterTheme {
        EventsList(
            eventListState = EventListState.Success(
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
            onFavouriteClick = {},
            onEventClick = {}
        )
    }
}

@TicketMasterPreview
@Composable
private fun EventsErrorPreview() {
    TicketMasterTheme {
        EventsError(error = "Failed to load events")
    }
}

@TicketMasterPreview
@Composable
private fun EventsLoadingPreview() {
    TicketMasterTheme {
        EventsLoading()
    }
}