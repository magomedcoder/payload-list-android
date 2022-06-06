package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.UserDto
import ru.magomedcoder.askue.domain.model.User

data class UserResponse(
    val UserInfo: UserDto
){

    fun toUserDtoResponse(): User {
        return User(
            email = UserInfo.email,
            firstName = UserInfo.first_name,
            lastName = UserInfo.last_name,
            organizationId = UserInfo.organization_id,
            organizationName = UserInfo.organization_name,
            phoneNumber = UserInfo.phone_number,
            username = UserInfo.username
        )
    }

}