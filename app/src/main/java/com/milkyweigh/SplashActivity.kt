package com.milkyweigh

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

/* This SplashActivity is the opening or loading screen of the MilkyWeigh app.
It plays a Lottie animation (a lightweight, high-quality animation format) when the app launches.
Once the planets aligned, it indicates that the loading part is done. Hence, it will automatically send the user to the main part of the app (MainActivity).
The animation listener makes sure this transition only happens once the animation ends.
This is used to create a smooth and engaging first impression while the app gets ready behind the scenes. */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val animationView: LottieAnimationView = findViewById(R.id.lottieAnimationView)
        animationView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            }
        })
    }
}