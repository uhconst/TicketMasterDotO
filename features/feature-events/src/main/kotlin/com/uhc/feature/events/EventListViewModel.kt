package com.uhc.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.events.GetEventsUseCase
import com.uhc.domain.events.model.Event
import com.uhc.domain.favourites.DeleteOrSaveFavouriteEventUseCase
import com.uhc.feature.events.state.EventState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EventListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val deleteOrSaveFavouriteEventUseCase: DeleteOrSaveFavouriteEventUseCase
) : ViewModel() {

    private val _eventState = MutableStateFlow<EventState>(EventState.Loading)
    val eventState: StateFlow<EventState> = _eventState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            getEventsUseCase()
                .onStart {
                    _eventState.value = EventState.Loading
                }
                .catch { e ->
                    _eventState.value = EventState.Error(e.message ?: "Unknown error")
                }
                .collect { events ->
                    if (events.isEmpty()) {
                        _eventState.value = EventState.Error("No events found")
                    } else {
                        _eventState.value = EventState.Success(events)
                    }
                }
        }
    }

    fun onClickFavouriteEvent(event: Event) {
        viewModelScope.launch {
            deleteOrSaveFavouriteEventUseCase(event.id, event.favourite)
        }
    }
}