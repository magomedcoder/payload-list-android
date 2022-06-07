package ru.magomedcoder.askue.domain.model

import ru.magomedcoder.askue.ui.base.DisplayableItem

class ElectronicArchive(
    val id: Int,
    val alarmReset: Boolean,
    val dateTime: String,
    val event: Event,
    val modelOfDevice: String,
    val placeAddress: PlaceAddress
) : DisplayableItem {

    override val itemId: String get() = id.toString()

}