package ru.magomedcoder.askue.domain.model

import ru.magomedcoder.askue.ui.base.DisplayableItem

data class ElectronicCounter(
    val devEui: String,
    val serN: String,
    val placeAddress: PlaceAddress,
    val lastSeenAt: String,
    val personalAccount: String,
) : DisplayableItem {

    override val itemId: String get() = serN

    data class PlaceAddress(
        val city: String,
        val number: Int,
        val street: String,
        val unit: String
    )

}