package com.jiwon.prestolabs.viewmodel

import androidx.lifecycle.ViewModel
import com.jiwon.prestolabs.apis.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InstrumentViewModel @Inject constructor(
    val webSocket : WebSocket
) : ViewModel() {
    fun connectSocket(){
        webSocket.openConnection(::onSocketMessageReceived)
    }

    fun onSocketMessageReceived(
        msg : String
    ) : String{
        // TODO handle socket message
        return msg
    }
}