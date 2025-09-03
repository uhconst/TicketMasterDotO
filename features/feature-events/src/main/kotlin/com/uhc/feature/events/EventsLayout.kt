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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.uhc.domain.events.model.Event
import com.uhc.lib.compose.utils.annotations.TicketMasterPreview
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.lib.compose.utils.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventsLayout() {
    val viewModel: EventListViewModel = koinViewModel()

    val events by viewModel.events.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    EventsList(
        events = events,
        isLoading = isLoading,
        onRefresh = { viewModel.refreshEvents() },
        onFavouriteClick = { event -> viewModel.onClickFavouriteEvent(event) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventsList(
    events: List<Event>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onFavouriteClick: (Event) -> Unit
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isLoading,
        onRefresh = onRefresh,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isLoading,
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
                items = events,
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
                    text = event.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = event.venue,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = event.dates,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onFavouriteClick) {
                Icon(
                    imageVector = if (event.favourite) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Favourite"
                )
            }
        }
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
            events = listOf(
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
            ),
            isLoading = false,
            onRefresh = {},
            onFavouriteClick = {}
        )
    }
}
