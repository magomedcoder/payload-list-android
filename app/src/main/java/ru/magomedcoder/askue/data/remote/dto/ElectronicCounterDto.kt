package ru.magomedcoder.askue.data.remote.dto

data class ElectronicCounterDto(
    val SerN: String,
    val TypeOfDevice: String,
    val all_period_currency: String,
    val application_id: Int,
    val dev_eui: String,
    val device_profile_name: String,
    val first_period_currency: String,
    val first_period_time: String,
    val first_seen_at: String,
    val last_period_currency: String,
    val last_period_time: String,
    val last_seen_at: String,
    val organization_id: Int,
    val payload: Payload,
    val personal_account: String?,
    val place_address: PlaceAddressDto,
    val sequence_number: Int
) {

    data class Payload(
        val Curr: Int,
        val Reas: Int,
        val SerN: Long
    )

}