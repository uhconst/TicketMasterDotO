package com.uhc.presentation.ui

//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
//import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.uhc.domain.model.Event
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventsScreen() {
    // Placeholder for the actual implementation
    /*    Box(modifier = Modifier.fillMaxSize()) {
            Text("Events Layout")
        }*/
    val viewModel: EventListViewModel = koinViewModel()

    val events by viewModel.events.collectAsState()

    viewModel.fetchEvents()

    EventsLayout(
        events = events,
        isLoading = /*viewModel.isLoading.value*/ false,
        onRefresh = { viewModel.fetchEvents() },
        onFavouriteClick = { event -> viewModel.onClickFavouriteEvent(event) },
        onFabClick = { /* Handle FAB click */ }
    )
}

@Composable
fun EventsLayout(
    events: List<Event>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onFavouriteClick: (Event) -> Unit,
    onFabClick: () -> Unit
) {
    LazyColumn {
        items(events) { event ->
            EventItem(
                event = event,
                onFavouriteClick = { onFavouriteClick(event) }
            )
        }
    }
}

@Composable
private fun EventItem(event: Event, onFavouriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = "Event Image",
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(1.8f)
                    .padding(end = 8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = event.venue,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = event.dates,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
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

@Preview
@Composable
fun EventsLayoutPreview() {
    EventsLayout(
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
        onFavouriteClick = {},
        onFabClick = {}
    )
}
