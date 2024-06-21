package com.raphaeloliveira.taskbeat.presentation

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.raphaeloliveira.taskbeat.R
import com.raphaeloliveira.taskbeat.databinding.SplashBinding

class Splash : AppCompatActivity() {

    private lateinit var binding: SplashBinding

    private val splashTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation)
        binding.llSplash.startAnimation(animation)

        binding.llSplash.postDelayed({
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }
}