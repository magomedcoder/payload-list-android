package ru.magomedcoder.askue.data.remote.dto

data class UserDto(
    val email: String,
    val first_name: String,
    val last_name: String,
    val organization_id: Int,
    val organization_name: String,
    val phone_number: String,
    val username: String,
)