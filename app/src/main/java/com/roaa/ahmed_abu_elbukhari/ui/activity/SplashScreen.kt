package com.roaa.ahmed_abu_elbukhari.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import com.roaa.ahmed_abu_elbukhari.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
              startSplashScreen()
    }



    private fun startSplashScreen(){
        object : CountDownTimer(5000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                lunchToMainActivity()
            }
        }.start()
    }


    private  fun lunchToMainActivity() {
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        finish()
    }


}
