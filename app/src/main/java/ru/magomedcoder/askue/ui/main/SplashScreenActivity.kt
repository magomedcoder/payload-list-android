package ru.magomedcoder.askue.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.magomedcoder.askue.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splashImage = findViewById<ImageView>(R.id.splashScreenLogo)
        splashImage.alpha = 0f
        splashImage
            .animate()
            .setDuration(1500)
            .alpha(1f).withEndAction {
                val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
    }

}