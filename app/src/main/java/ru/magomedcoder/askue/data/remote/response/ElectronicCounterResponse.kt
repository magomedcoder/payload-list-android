package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.ElectronicCounterDto

data class ElectronicCounterResponse(
    val results: List<ElectronicCounterDto>
)