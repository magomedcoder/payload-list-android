package ru.magomedcoder.askue.data.remote.dto

class ElectronicEventDto(
    val alarm_reset: Boolean,
    val date_time: String,
    val event: EventDto,
    val id: Int,
    val model_of_device: String,
    val place_address: PlaceAddressDto,
)