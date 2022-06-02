package ru.magomedcoder.askue.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.magomedcoder.askue.data.remote.request.AuthRequest
import ru.magomedcoder.askue.data.remote.response.AuthResponse
import javax.inject.Singleton

@Singleton
interface UserApi {

    @POST("auth/token/login")
    suspend fun doLogin(@Body request: AuthRequest): AuthResponse

}