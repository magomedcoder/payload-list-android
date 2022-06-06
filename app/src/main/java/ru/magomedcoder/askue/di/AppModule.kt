package ru.magomedcoder.askue.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.magomedcoder.askue.Constants
import ru.magomedcoder.askue.data.local.UserPreferences
import ru.magomedcoder.askue.data.remote.api.CounterApi
import ru.magomedcoder.askue.data.remote.api.UserApi
import ru.magomedcoder.askue.data.repository.CounterRepositoryImpl
import ru.magomedcoder.askue.data.repository.UserRepositoryImpl
import ru.magomedcoder.askue.domain.repository.CounterRepository
import ru.magomedcoder.askue.domain.repository.UserRepository
import ru.magomedcoder.askue.domain.useCase.FetchElectronicCounterUseCase
import ru.magomedcoder.askue.utils.network.AccessTokenAuthenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    @Provides
    @Singleton
    fun provideAuthApi(
        accessTokenAuthenticator: AccessTokenAuthenticator,
    ): UserApi {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .authenticator(accessTokenAuthenticator)
                    .build()
            )
            .baseUrl(Constants.Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCounterApi(
        accessTokenAuthenticator: AccessTokenAuthenticator,
    ): CounterApi {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .authenticator(accessTokenAuthenticator)
                    .build()
            )
            .baseUrl(Constants.Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CounterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: UserApi,
        userPreferences: UserPreferences
    ): UserRepository {
        return UserRepositoryImpl(authApi, userPreferences)
    }

    @Provides
    @Singleton
    fun provideCounterRepository(
        counterApi: CounterApi
    ): CounterRepository {
        return CounterRepositoryImpl(counterApi)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application):
            SharedPreferences = app.getSharedPreferences(Constants.Pref.NAME, Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun provideFetchElectronicCounterUseCase(
        repository: CounterRepository
    ): FetchElectronicCounterUseCase {
        return FetchElectronicCounterUseCase(repository)
    }

}