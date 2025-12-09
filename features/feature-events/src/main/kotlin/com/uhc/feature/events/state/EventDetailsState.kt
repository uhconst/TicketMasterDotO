package com.uhc.feature.events.state

import com.uhc.domain.events.model.Event

sealed class EventDetailsState {
    object Loading : EventDetailsState()
    data class Success(val event: Event) : EventDetailsState()
    data class Error(val message: String) : EventDetailsState()
}
