package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.ElectronicEventDto

data class ElectronicEventResponse(
    val count: Int,
    val results: List<ElectronicEventDto>
)