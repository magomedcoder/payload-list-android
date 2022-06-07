package ru.magomedcoder.askue.domain.model

import ru.magomedcoder.askue.ui.base.DisplayableItem

class ElectronicEvent(
    val id: Int,
    val alarmReset: Boolean,
    val dateTime: String,
    val event: Event,
    val modelOfDevice: String,
    val placeAddress: PlaceAddress
) : DisplayableItem {

    override val itemId: String get() = id.toString()

    data class Event(
      //  val orangeLevel: Any,
     //   val redLevel: Any,
        val yellowLevel: String
    )

    data class PlaceAddress(
        val city: String,
        val number: Int,
        val street: String,
        val unit: String
    )

}