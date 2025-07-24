package com.uhc.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.exception.DefaultException
import com.uhc.domain.interactor.FavouriteEventsUseCase
import com.uhc.domain.interactor.GetEventsUseCase
import com.uhc.domain.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val favouriteEventsUseCase: FavouriteEventsUseCase
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
                favouriteEventsUseCase.updateFavouriteEvent(event)
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