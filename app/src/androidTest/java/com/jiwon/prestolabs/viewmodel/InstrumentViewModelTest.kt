package com.jiwon.prestolabs.viewmodel

import android.util.Log
import org.junit.Rule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat


@HiltAndroidTest
class InstrumentViewModelTest{
    private val TAG = InstrumentViewModelTest::class.java.simpleName

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewmodel : MainViewModel

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun initTest(){
        assertThat(::viewmodel.isInitialized).isTrue()
    }

    @Test
    fun testConnection(){
        try{
            viewmodel.connectSocket()
        }catch(e: Exception){
            Log.e(TAG, e.stackTraceToString())
            e.printStackTrace()
            assert(false)
        }
        assert(true)
    }


}