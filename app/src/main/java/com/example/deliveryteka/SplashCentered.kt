package com.example.deliveryteka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.deliveryteka.activities.LoginActivity

class SplashCentered : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val logoAnim = AnimationUtils.loadAnimation(this,R.anim.logo_splash_screen)
        findViewById<ImageView>(R.id.splash_screen).animation = logoAnim

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}