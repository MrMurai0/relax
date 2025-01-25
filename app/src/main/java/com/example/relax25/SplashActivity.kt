package com.example.relax25

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // 2 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val textView = findViewById<TextView>(R.id.textView) // замените ваш id
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation)
        textView.startAnimation(animation)



        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java) // Замените MainActivity
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}