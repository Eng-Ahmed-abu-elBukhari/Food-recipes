package com.roaa.ahmed_abu_elbukhari.data.utils

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class RateApp constructor(private val context: Context) {

        fun rateMyApp(){
            try {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
           // val uri  = Uri.parse("market://details?id=${application.packageName}")
            intent.data = Uri.parse("market://details?id=${context.packageName}")
                context.startActivity(intent)
            }catch (e: ActivityNotFoundException){
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse("http://play.google.com/store/apps/details?id=${context.packageName}")
                context.startActivity(intent)
            }
        }

}