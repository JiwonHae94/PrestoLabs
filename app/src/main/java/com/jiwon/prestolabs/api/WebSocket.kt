package com.jiwon.prestolabs.api

import android.util.Log
import okhttp3.*
import okhttp3.WebSocket
import okio.ByteString
import org.json.JSONArray
import org.json.JSONObject

class WebSocket {
    val TAG = WebSocket::class.java.simpleName

    private val client = OkHttpClient.Builder()
        .build()

    private val request : Request = Request.Builder()
        .url(WebSocketURL)
        .build()

    fun openConnection(
        onMessageReceived : (String) -> Unit
    ){
        client.newWebSocket(request, SocketListener(onMessageReceived))
    }

    fun closeConnection(){
        client.dispatcher.executorService.shutdown()
    }

    class SocketListener(
        val onMessageReceived : (String) -> Unit
    ) : WebSocketListener(){
        private val TAG = WebSocket::class.java.simpleName

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            // return msg received for parsing
            onMessageReceived(text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)

            // return msg received for parsing
            onMessageReceived(bytes.toString())
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            // send subscription message back to the socket upon connection
            val jsonObject = JSONObject()
            jsonObject.put("op", "subscribe")
            jsonObject.put("args", JSONArray(arrayOf(Subscription)))

            webSocket.send(
                jsonObject.toString()
            )
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.i(TAG,"Closed : $code / $reason")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.i(TAG,"Closing : $code / $reason")
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
        const val Subscription = "instrument"
        const val WebSocketURL = "wss://ws.bitmex.com/realtime"
    }
}