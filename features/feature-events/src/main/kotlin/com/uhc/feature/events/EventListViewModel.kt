package com.uhc.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.events.exception.DefaultException
import com.uhc.domain.events.GetEventsUseCase
import com.uhc.domain.events.model.Event
import com.uhc.domain.favourites.DeleteFavouriteEventUseCase
import com.uhc.domain.favourites.SaveFavouriteEventUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val saveFavouriteEventUseCase: SaveFavouriteEventUseCase,
    private val deleteFavouriteEventUseCase: DeleteFavouriteEventUseCase
) : ViewModel() {

    sealed class State {
        data object GoToFavourite : State()
    }

    private var size: Int = 50

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<DefaultException?>(null)
    val error: StateFlow<DefaultException?> = _error

    private val _state = MutableStateFlow<State?>(null)
    val state: StateFlow<State?> = _state

    fun fetchEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val events = getEventsUseCase.getEvents(size)
                _events.value = events.sortedByDescending { it.favourite }
            } catch (e: DefaultException) {
                _error.value = e
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onClickFavouriteEvent(event: Event) {
        viewModelScope.launch {
            try {
                if (event.favourite) {
                    deleteFavouriteEventUseCase(event.id)
                } else {
                    saveFavouriteEventUseCase(event.id)
                }
                fetchEvents()
            } catch (e: DefaultException) {
                _error.value = e
            }
        }
    }

    fun onClickFavourite() {
        _state.value = State.GoToFavourite
    }
}