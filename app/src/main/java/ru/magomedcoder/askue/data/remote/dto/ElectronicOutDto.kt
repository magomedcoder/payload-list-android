package ru.magomedcoder.askue.data.remote.dto

class ElectronicOutDto(
    val SerN: String,
    val dev_eui: String,
    val last_seen_at: String,
    val personal_account: String,
    val place_address: PlaceAddressDto,
    val sequence_number: Int
)