package ru.magomedcoder.askue.domain.repository

import ru.magomedcoder.askue.domain.model.Login
import ru.magomedcoder.askue.domain.model.User
import ru.magomedcoder.askue.utils.Resource

interface UserRepository {

    suspend fun saveToken(token: String?)

    suspend fun saveOrganizationId(organizationId: Int?)

    fun isToken(): Boolean

    fun isOrganizationId(): Boolean

    fun getToken(): Login?

    fun removeToken()

    suspend fun login(username: String, password: String): Resource<Login>

    suspend fun userInfo(): Resource<User>

}