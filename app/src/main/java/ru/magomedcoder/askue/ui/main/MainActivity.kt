package ru.magomedcoder.askue.ui.main

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}