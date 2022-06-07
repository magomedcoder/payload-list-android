package ru.magomedcoder.askue.data.remote.dto

class ElectronicArchiveDto(
    val id: Int,
    val alarm_reset: Boolean,
    val date_time: String,
    val event: Event,
    val model_of_device: String,
    val place_address: PlaceAddress
) {

    data class Event(
        val orange_level: Any,
        val red_level: Any,
        val yellow_level: String
    )

    data class PlaceAddress(
        val city: String,
        val number: Int,
        val street: String,
        val unit: String
    )

}