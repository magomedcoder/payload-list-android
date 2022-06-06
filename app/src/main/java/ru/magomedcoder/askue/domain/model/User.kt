package ru.magomedcoder.askue.domain.model

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val organizationId: Int,
    val organizationName: String,
    val phoneNumber: String,
    val username: String
)