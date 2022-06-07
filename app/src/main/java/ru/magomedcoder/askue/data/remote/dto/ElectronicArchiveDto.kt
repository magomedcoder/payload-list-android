package ru.magomedcoder.askue.data.remote.dto

class ElectronicArchiveDto(
    val id: Int,
    val alarm_reset: Boolean,
    val date_time: String,
    val event: EventDto,
    val model_of_device: String,
    val place_address: PlaceAddressDto,
)