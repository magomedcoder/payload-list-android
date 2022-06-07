package ru.magomedcoder.askue.ui.out

import ru.magomedcoder.askue.domain.model.ElectronicOut

sealed class OutState {
    class Success(val response: List<ElectronicOut>) : OutState()
    class Failure(val error: String) : OutState()
    object Empty : OutState()
    object Loading : OutState()
}