package ru.magomedcoder.askue.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BackPressRegistrar {

    private var registeredHandler: BackPressHandler? = null

    override fun registerHandler(handler: BackPressHandler) {
        registeredHandler = handler
    }

    override fun unregisterHandler(handler: BackPressHandler) {
        registeredHandler = null
    }

    override fun onBackPressed() {
        if (registeredHandler?.onBackPressed() != false)
            super.onBackPressed()
    }

}