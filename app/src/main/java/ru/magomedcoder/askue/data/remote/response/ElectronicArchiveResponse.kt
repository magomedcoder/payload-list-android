package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.ElectronicArchiveDto

data class ElectronicArchiveResponse(
    val count: Int,
    val results: List<ElectronicArchiveDto>
)