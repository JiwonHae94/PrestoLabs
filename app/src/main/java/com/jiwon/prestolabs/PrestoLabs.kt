package com.jiwon.prestolabs

import android.app.Application
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PrestoLabs : Application() {
    override fun onCreate() {
        super.onCreate()

        // add gif image loader to coil image loader
        val imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(this@PrestoLabs))
                } else {
                    add(GifDecoder())
                }
            }
            .build()

        Coil.setImageLoader(imageLoader)
    }
}