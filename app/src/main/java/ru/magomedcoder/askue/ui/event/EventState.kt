package ru.magomedcoder.askue.ui.event

import ru.magomedcoder.askue.domain.model.ElectronicEvent

sealed class EventState {
    class Success(val response: List<ElectronicEvent>) : EventState()
    class Failure(val error: String) : EventState()
    object Empty : EventState()
    object Loading : EventState()
}