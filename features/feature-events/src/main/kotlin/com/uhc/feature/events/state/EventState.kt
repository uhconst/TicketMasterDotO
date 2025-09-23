package com.uhc.feature.events.state

import com.uhc.domain.events.model.Event

sealed class EventState {
    object Loading : EventState()
    data class Success(val events: List<Event>) : EventState()
    data class Error(val message: String) : EventState()
}