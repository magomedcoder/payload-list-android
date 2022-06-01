package ru.magomedcoder.askue.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthRequest(

    @Expose
    @SerializedName("username")
    private val username: String,

    @Expose
    @SerializedName("password")
    private val password: String

)