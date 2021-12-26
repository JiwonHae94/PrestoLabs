package com.jiwon.prestolabs.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jiwon.prestolabs.databinding.ActivityMainBinding
import com.jiwon.prestolabs.viewmodel.MainViewModel
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.jiwon.prestolabs.R
import com.jiwon.prestolabs.helper.InternetHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // UI binding to reference binding
    private lateinit var binding : ActivityMainBinding

    private val viewmodel : MainViewModel by viewModels()

    private val intentFilter = IntentFilter().apply {
        addAction("android.net.conn.CONNECTIVITY_CHANGE")
        addAction("android.net.wifi.WIFI_STATE_CHANGED")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewmodel = viewmodel

        // set action custom action bar
        binding.actionBar.overflowIcon = getDrawable(R.mipmap.hamburger)
        setSupportActionBar(binding.actionBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setContentView(binding.root)

        if(savedInstanceState == null){
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                add<MainFragment>(R.id.main_fragment_container)
            }
        }
        registerReceiver(InternetReceiver(::onConnectionChange), intentFilter)
    }

    private fun onConnectionChange(hasConnection : Boolean){
        if(hasConnection){
            viewmodel.connectSocket()
        }
    }

    private class InternetReceiver(val onConnectionChange : (Boolean) -> Unit) : BroadcastReceiver(){
        private val TAG = InternetReceiver::class.java.simpleName

        override fun onReceive(p0: Context, p1: Intent) {
            Log.d(TAG, "intent received : ${p1.action}")
            onConnectionChange(InternetHelper.hasConnection(p0))
        }
    }


    companion object{

    }



}