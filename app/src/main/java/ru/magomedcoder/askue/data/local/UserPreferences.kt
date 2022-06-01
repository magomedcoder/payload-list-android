package ru.magomedcoder.askue.data.local

import android.content.SharedPreferences
import ru.magomedcoder.askue.Constants
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPreferences @Inject constructor(
    private val prefs: SharedPreferences
) {

    fun setToken(token: String) = prefs
        .edit()
        .putString(Constants.Pref.AUTH_TOKEN, token)
        .apply()

    fun getToken(): String? = prefs.getString(Constants.Pref.AUTH_TOKEN, null)

    fun removeToken() = prefs
        .edit()
        .remove(Constants.Pref.AUTH_TOKEN)
        .apply()
}