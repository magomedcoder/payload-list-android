package ru.magomedcoder.askue.data.remote.dto

class ElectronicOutDto(
    val SerN: String,
    val dev_eui: String,
    val last_seen_at: String,
    val personal_account: String,
    val place_address: PlaceAddress,
    val sequence_number: Int
) {

    data class PlaceAddress(
        val city: String,
        val number: Int,
        val street: String,
        val unit: String
    )

}