package com.uhc.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.events.GetEventsUseCase
import com.uhc.domain.events.model.Event
import com.uhc.domain.favourites.DeleteOrSaveFavouriteEventUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val deleteOrSaveFavouriteEventUseCase: DeleteOrSaveFavouriteEventUseCase
) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(Unit)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    @OptIn(ExperimentalCoroutinesApi::class)
    val events: StateFlow<List<Event>> = refreshTrigger
        .flatMapLatest { getEventsUseCase() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun refreshEvents() {
        _isLoading.value = true
        refreshTrigger.value = Unit

        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
        }
    }

    fun onClickFavouriteEvent(event: Event) {
        viewModelScope.launch {
            deleteOrSaveFavouriteEventUseCase(event.id, event.favourite)
        }
    }
}