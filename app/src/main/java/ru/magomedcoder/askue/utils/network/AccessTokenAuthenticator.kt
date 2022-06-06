package ru.magomedcoder.askue.utils.network

import dagger.Lazy
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.magomedcoder.askue.domain.repository.UserRepository
import javax.inject.Inject

class AccessTokenAuthenticator @Inject constructor(
    private val userRepository: Lazy<UserRepository>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val token = userRepository.get().getToken()?.authToken
        return response.request()
            .newBuilder()
            .addHeader("Authorization", "Token $token")
            .build()
    }

}