package ru.magomedcoder.askue.domain.model

import ru.magomedcoder.askue.ui.base.DisplayableItem

data class ElectronicCounter(
    val devEui: String,
    val SerN: String,
) : DisplayableItem {

    override val itemId: String get() = SerN

}