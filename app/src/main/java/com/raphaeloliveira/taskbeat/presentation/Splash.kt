package com.raphaeloliveira.taskbeat.presentation

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.raphaeloliveira.taskbeat.R

class Splash : AppCompatActivity() {

    private val splashTime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation)
        val splashLayout = findViewById<LinearLayout>(R.id.ll_splash)

        splashLayout.startAnimation(fadeInAnimation)

        splashLayout.postDelayed({
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }
}