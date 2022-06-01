package ru.magomedcoder.askue.data.repository

import retrofit2.HttpException
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.data.local.UserPreferences
import ru.magomedcoder.askue.data.remote.api.AuthApi
import ru.magomedcoder.askue.data.remote.request.AuthRequest
import ru.magomedcoder.askue.domain.model.Login
import ru.magomedcoder.askue.domain.model.User
import ru.magomedcoder.askue.domain.repository.AuthRepository
import ru.magomedcoder.askue.utils.Resource
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun saveToken(token: String?) {
        userPreferences.setToken(token.toString())
    }

    override fun getToken(): User? {
        val token = userPreferences.getToken()
        return if (token != null)
            User(token = token.toString())
        else
            null
    }

    override suspend fun removeToken() {
        userPreferences.removeToken()
    }

    override suspend fun login(username: String, password: String): Resource<Login> {
        return try {
            val response = api
                .doLogin(AuthRequest(username, password))
                .toLoginResponse()
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(error = e.message ?: R.string.unknown_error.toString())
        } catch (e: IOException) {
            Resource.Error(error = e.message ?: R.string.unknown_error.toString())
        }
    }

}