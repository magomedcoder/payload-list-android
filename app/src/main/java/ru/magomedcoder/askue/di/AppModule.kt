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
import ru.magomedcoder.askue.domain.useCase.FetchElectronicArchiveUseCase
import ru.magomedcoder.askue.domain.useCase.FetchElectronicCounterUseCase
import ru.magomedcoder.askue.domain.useCase.FetchElectronicEventUseCase
import ru.magomedcoder.askue.domain.useCase.FetchElectronicOutUseCase
import ru.magomedcoder.askue.utils.network.AccessTokenAuthenticator
import java.util.concurrent.TimeUnit
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
                    .readTimeout(Constants.NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(Constants.NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
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
                    .readTimeout(Constants.NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(Constants.NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
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
        counterApi: CounterApi,
        userPreferences: UserPreferences
    ): CounterRepository {
        return CounterRepositoryImpl(counterApi, userPreferences)
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

    @Provides
    @Singleton
    fun provideFetchElectronicEventUseCase(
        repository: CounterRepository
    ): FetchElectronicEventUseCase {
        return FetchElectronicEventUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchElectronicArchiveUseCase(
        repository: CounterRepository
    ): FetchElectronicArchiveUseCase {
        return FetchElectronicArchiveUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchElectronicOutUseCase(
        repository: CounterRepository
    ): FetchElectronicOutUseCase {
        return FetchElectronicOutUseCase(repository)
    }

}