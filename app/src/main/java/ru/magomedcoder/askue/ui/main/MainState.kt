package ru.magomedcoder.askue.ui.main

import ru.magomedcoder.askue.domain.model.ElectronicCounter

sealed class MainState {
    class Success(val response: List<ElectronicCounter>) : MainState()
    class Failure(val error: String) : MainState()
    object Empty : MainState()
    object Loading : MainState()
}