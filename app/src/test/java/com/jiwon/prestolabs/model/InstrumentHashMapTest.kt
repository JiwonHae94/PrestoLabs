package com.jiwon.prestolabs.model

import com.jiwon.prestolabs.model.Instrument.Companion.CompareByLastPrice
import org.junit.Assert.*
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.jiwon.prestolabs.model.Instrument.Companion.CompareByLastChangedPercentage

class InstrumentHashMapTest{
    private val testSymbols = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H"
    )

    val testData = InstrumentHashMap().apply{
        add(Instrument(symbol = "A", state = InstrumentState.Open, lastPrice = 0, isInverse = true, lastChangePercentage = 100.0, volume24 = 0))
        add(Instrument(symbol = "B", state = InstrumentState.Open, lastPrice = 2, isInverse = true, lastChangePercentage = 99.0, volume24 = 0))
        add(Instrument(symbol = "C", state = InstrumentState.Open, lastPrice = 3, isInverse = true, lastChangePercentage = 98.0, volume24 = 0))
        add(Instrument(symbol = "D", state = InstrumentState.Open, lastPrice = 4, isInverse = true, lastChangePercentage = 97.0, volume24 = 0))
        add(Instrument(symbol = "E", state = InstrumentState.Open, lastPrice = 5, isInverse = true, lastChangePercentage = 96.0, volume24 = 0))
        add(Instrument(symbol = "F", state = InstrumentState.Open, lastPrice = 6, isInverse = true, lastChangePercentage = 95.0, volume24 = 0))
        add(Instrument(symbol = "G", state = InstrumentState.Open, lastPrice = 7, isInverse = true, lastChangePercentage = 94.0, volume24 = 0))
        add(Instrument(symbol = "H", state = InstrumentState.Open, lastPrice = 8, isInverse = true, lastChangePercentage = 93.0, volume24 = 0))
    }

    @Test
    fun `sort instrument hashmap by price`(){
        val sortedDataByPrice = testData.sortedMapBy(::CompareByLastPrice)
        sortedDataByPrice.forEachIndexed { index, instrument ->
            println(testSymbols[index] + " == " + instrument.symbol)
            assertThat(testSymbols[index].equals(instrument.symbol)).isTrue()
        }
    }

    @Test
    fun `sort instrument hashmap by price inverse`(){
        val sortedDataByPrice = testData.sortedMapBy(::CompareByLastPrice, reverse = true)
        sortedDataByPrice.forEachIndexed { index, instrument ->
            println(testSymbols[testSymbols.size - index - 1] + " == " + instrument.symbol)
            assertThat(testSymbols[testSymbols.size - index - 1].equals(instrument.symbol)).isTrue()
        }
    }

    @Test
    fun `sort instrument hashmap by last changed`(){
        val sortedDataByPrice = testData.sortedMapBy(::CompareByLastChangedPercentage)
        sortedDataByPrice.forEachIndexed { index, instrument ->
            println(testSymbols[testSymbols.size - index - 1] + " == " + instrument.symbol)
            assertThat(testSymbols[testSymbols.size - index - 1].equals(instrument.symbol)).isTrue()
        }
    }

    @Test
    fun `sort instrument hashmap by last changed inverse`(){
        val sortedDataByPrice = testData.sortedMapBy(::CompareByLastChangedPercentage, reverse = true)
        sortedDataByPrice.forEachIndexed { index, instrument ->
            println(testSymbols[index] + " == " + instrument.symbol)
            assertThat(testSymbols[index].equals(instrument.symbol)).isTrue()
        }
    }
}