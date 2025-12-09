package com.uhc.feature.events.state

import com.uhc.domain.events.model.Event

sealed class EventListState {
    object Loading : EventListState()
    data class Success(val events: List<Event>) : EventListState()
    data class Error(val message: String) : EventListState()
}
