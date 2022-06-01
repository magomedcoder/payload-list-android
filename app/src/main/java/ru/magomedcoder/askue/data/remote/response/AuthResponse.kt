package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.domain.model.Login

data class AuthResponse(
    val auth_token: String
) {

    fun toLoginResponse(): Login {
        return Login(authToken = auth_token)
    }

}