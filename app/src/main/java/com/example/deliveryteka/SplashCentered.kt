package com.example.deliveryteka

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.deliveryteka.activities.LoginActivity
import com.example.deliveryteka.activities.MainActivity
import com.example.deliveryteka.utility.Constants

class SplashCentered : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val logoAnim = AnimationUtils.loadAnimation(this,R.anim.logo_splash_screen)
        findViewById<ImageView>(R.id.splash_screen).animation = logoAnim

        val sharedPref = this.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        val password = sharedPref?.getString(Constants.USER_PASSWORD, "")

        Handler().postDelayed({
            if (password.isNullOrEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 1500)

    }
}