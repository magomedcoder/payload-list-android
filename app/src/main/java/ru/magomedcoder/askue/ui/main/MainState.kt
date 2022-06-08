package ru.magomedcoder.askue.ui.main

import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.domain.model.Event

sealed class MainState {
    class Success(val response: List<ElectronicCounter>) : MainState()
    class Failure(val error: String) : MainState()
    object Empty : MainState()
    object Loading : MainState()
    class Counter(val event: Event) : MainState()
}