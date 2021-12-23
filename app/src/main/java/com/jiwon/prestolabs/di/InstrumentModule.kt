package com.jiwon.prestolabs.di

import com.jiwon.prestolabs.repository.InstrumentRepository
import com.jiwon.prestolabs.viewmodel.MainViewModel
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
    fun providesInstrumentRepository() = InstrumentRepository()

    @Provides
    @Singleton
    fun providesInstrumentViewModel(
        repository : InstrumentRepository
    ) = MainViewModel(repository)

}