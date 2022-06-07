package ru.magomedcoder.askue.domain.model

import ru.magomedcoder.askue.ui.base.DisplayableItem

class ElectronicOut(
    val serN: String,
    val devEui: String,
    val lastSeenAt: String,
    val personalAccount: String,
    val placeAddress: PlaceAddress,
    val sequenceNumber: Int
) : DisplayableItem {

    override val itemId: String get() = serN

    data class PlaceAddress(
        val city: String,
        val number: Int,
        val street: String,
        val unit: String
    )

}