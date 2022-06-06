package ru.magomedcoder.askue.ui.auth

import ru.magomedcoder.askue.domain.model.User

sealed class UserState {
    class Success(val response: User) : UserState()
    class Failure(val error: String) : UserState()
    object Empty : UserState()
}