package ru.magomedcoder.askue.ui.base

interface BackPressRegistrar {
    fun registerHandler(handler: BackPressHandler)
    fun unregisterHandler(handler: BackPressHandler)
}

interface BackPressHandler {
    fun onBackPressed(): Boolean
}