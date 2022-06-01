package ru.magomedcoder.askue.domain.repository

import ru.magomedcoder.askue.domain.model.Login
import ru.magomedcoder.askue.domain.model.User
import ru.magomedcoder.askue.utils.Resource

interface AuthRepository {

    suspend fun saveToken(token: String?)

    fun getToken(): User?

    suspend fun removeToken()

    suspend fun login(username: String, password: String): Resource<Login>

}