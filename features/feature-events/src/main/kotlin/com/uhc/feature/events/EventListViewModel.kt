package com.uhc.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.events.GetEventsUseCase
import com.uhc.domain.events.model.Event
import com.uhc.domain.favourites.SetFavouriteEventUseCase
import com.uhc.feature.events.state.EventListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EventListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val setFavouriteEventUseCase: SetFavouriteEventUseCase
) : ViewModel() {

    private val _eventListState = MutableStateFlow<EventListState>(EventListState.Loading)
    val eventListState: StateFlow<EventListState> = _eventListState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            getEventsUseCase()
                .onStart {
                    _eventListState.value = EventListState.Loading
                }
                .catch { e ->
                    _eventListState.value = EventListState.Error(e.message ?: "Unknown error")
                }
                .collect { events ->
                    if (events.isEmpty()) {
                        _eventListState.value = EventListState.Error("No events found")
                    } else {
                        _eventListState.value = EventListState.Success(events)
                    }
                }
        }
    }

    fun onClickFavouriteEvent(event: Event) {
        viewModelScope.launch {
            setFavouriteEventUseCase(event.id, !event.favourite)
        }
    }
}