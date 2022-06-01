package ru.magomedcoder.askue.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.magomedcoder.askue.Constants
import ru.magomedcoder.askue.data.local.UserPreferences
import ru.magomedcoder.askue.data.remote.api.AuthApi
import ru.magomedcoder.askue.data.repository.AuthRepositoryImpl
import ru.magomedcoder.askue.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(authApi, userPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application):
            SharedPreferences = app.getSharedPreferences(Constants.Pref.NAME, Context.MODE_PRIVATE)

}