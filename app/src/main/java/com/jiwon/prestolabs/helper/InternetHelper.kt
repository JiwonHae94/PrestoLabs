package com.jiwon.prestolabs.helper

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

object InternetHelper {
    fun hasConnection(context: Context) : Boolean {
        val mgr: ConnectivityManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = mgr.getActiveNetworkInfo()
        return netInfo?.isConnected ?: false
    }
}