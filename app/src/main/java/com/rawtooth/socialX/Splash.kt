package com.rawtooth.socialX

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView

import socialX.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    lateinit var lottie : LottieAnimationView
    lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        splashBinding.lottie.animate().translationY(0f).setDuration(1000).setStartDelay(2500)
        Handler().postDelayed({
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}