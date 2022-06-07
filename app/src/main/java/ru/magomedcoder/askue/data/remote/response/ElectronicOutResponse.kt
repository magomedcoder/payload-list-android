package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.ElectronicOutDto

data class ElectronicOutResponse(
    val count: Int,
    val results: List<ElectronicOutDto>
)