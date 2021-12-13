package com.jiwon.prestolabs.apis

import android.util.Log
import okhttp3.*
import okhttp3.WebSocket
import okio.ByteString

class WebSocket {
    val TAG = WebSocket::class.java.simpleName

    private val client = OkHttpClient.Builder()
        .build()

    private val request : Request = Request.Builder()
        .url(WebSocketURL)
        .build()

    fun openConnection(
        onMessageReceived : () -> Unit
    ){
        client.newWebSocket(request, SocketListener(onMessageReceived))
    }

    fun closeConnection(){
        client.dispatcher.executorService.shutdown()
    }


    class SocketListener(
        onMessageReceived : () -> Unit
    ) : WebSocketListener(){
        private val TAG = WebSocket::class.java.simpleName

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            Log.d(TAG, "Message : $text")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)

            Log.d(TAG, "Message : $bytes")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            // TODO send subcription message
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("Socket","Closed : $code / $reason")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("Socket","Closing : $code / $reason")
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            webSocket.cancel()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.e(TAG, t.stackTraceToString())
        }

        companion object{
            private const val NORMAL_CLOSURE_STATUS = 1000
        }

    }

    companion object{
        const val WebSocketURL = "wss://ws.bitmex.com/realtime"
        const val Subcription = "instrument"
    }
}