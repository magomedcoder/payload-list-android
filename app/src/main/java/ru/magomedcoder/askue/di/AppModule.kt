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
import ru.magomedcoder.askue.data.remote.api.UserApi
import ru.magomedcoder.askue.data.repository.UserRepositoryImpl
import ru.magomedcoder.askue.domain.repository.UserRepository
import ru.magomedcoder.askue.utils.NetworkHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    @Provides
    @Singleton
    fun provideAuthApi(): UserApi {
        return Retrofit.Builder()
            .client(NetworkHelper().addHeaderAuthorization())
            .baseUrl(Constants.Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
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
    fun provideSharedPreferences(app: Application):
            SharedPreferences = app.getSharedPreferences(Constants.Pref.NAME, Context.MODE_PRIVATE)

}