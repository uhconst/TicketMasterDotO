package com.uhc.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.events.GetEventByIdUseCase
import com.uhc.feature.events.state.EventDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val eventId: String
) : ViewModel() {

    private val _eventDetailsState = MutableStateFlow<EventDetailsState>(EventDetailsState.Loading)
    val eventDetailsState: StateFlow<EventDetailsState> = _eventDetailsState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            val event = getEventByIdUseCase(eventId)
            _eventDetailsState.value = EventDetailsState.Success(event)
        }
    }
}