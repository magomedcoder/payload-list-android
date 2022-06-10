package ru.magomedcoder.askue.domain.model

import ru.magomedcoder.askue.ui.base.DisplayableItem

data class ElectronicCounter(
    val devEui: String,
    val serN: String,
    val placeAddress: PlaceAddress,
    val firstSeenAt: String,
    val lastSeenAt: String,
    val personalAccount: String,
    val deviceProfileName: String,
    val firstPeriodCurrency: String,
    val lastPeriodCurrency: String,
    val allPeriodCurrency: String
) : DisplayableItem {

    override val itemId: String get() = serN

}