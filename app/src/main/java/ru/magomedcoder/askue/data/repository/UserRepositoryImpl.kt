package ru.magomedcoder.askue.data.repository

import retrofit2.HttpException
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.data.local.UserPreferences
import ru.magomedcoder.askue.data.remote.api.UserApi
import ru.magomedcoder.askue.data.remote.request.AuthRequest
import ru.magomedcoder.askue.domain.model.Login
import ru.magomedcoder.askue.domain.model.User
import ru.magomedcoder.askue.domain.repository.UserRepository
import ru.magomedcoder.askue.utils.Resource
import java.io.IOException

class UserRepositoryImpl(
    private val api: UserApi,
    private val userPreferences: UserPreferences
) : UserRepository {

    override suspend fun saveToken(token: String?) {
        userPreferences.setToken(token.toString())
    }

    override suspend fun saveOrganizationId(organizationId: Int?) {
        userPreferences.setOrganizationId(organizationId.toString())
    }

    override fun isToken(): Boolean {
        val sessionId = userPreferences.getToken()
        return (sessionId != null)
    }

    override fun isOrganizationId(): Boolean {
        val organizationId = userPreferences.getOrganizationId()
        return (organizationId != null)
    }

    override fun getToken(): Login? {
        val token = userPreferences.getToken()
        return if (token != null)
            Login(authToken = token.toString())
        else
            null
    }

    override fun removeToken() {
        userPreferences.removeToken()
        userPreferences.removeOrganizationId()
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

    override suspend fun userInfo(): Resource<User> {
        return try {
            val response = api
                .doUserInfo()
                .toUserDtoResponse()
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(error = e.message ?: R.string.unknown_error.toString())
        } catch (e: HttpException) {
            Resource.Error(error = e.message ?: R.string.unknown_error.toString())
        }
    }

}