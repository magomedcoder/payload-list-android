package ru.magomedcoder.askue.ui.auth

import ru.magomedcoder.askue.domain.model.Login

sealed class AuthState {
    class Success(val response: Login) : AuthState()
    class Failure(val error: String) : AuthState()
    object Empty : AuthState()
}