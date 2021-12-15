package com.jiwon.prestolabs.di

import com.jiwon.prestolabs.api.WebSocket
import com.jiwon.prestolabs.viewmodel.InstrumentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InstrumentModule {
    @Provides
    @Singleton
    fun providesWebSocket() = WebSocket()

    @Provides
    @Singleton
    fun providesInstrumentViewModel(
        webSocket: WebSocket
    ) = InstrumentViewModel(webSocket)

}