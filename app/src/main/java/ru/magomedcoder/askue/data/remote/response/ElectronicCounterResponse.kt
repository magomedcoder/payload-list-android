package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.ElectronicCounterDto

data class ElectronicCounterResponse(
    val count: Int,
    val page_size: Int,
    val pages_count: Double,
    val results: List<ElectronicCounterDto>
)