package com.jiwon.prestolabs.model.helper

import android.util.Log
import com.jiwon.prestolabs.model.Instrument.Companion.PercentageChangeCompator
import com.jiwon.prestolabs.model.Instrument.Companion.PriceComparator
import com.jiwon.prestolabs.model.Instrument.Companion.SymbolComparator
import com.jiwon.prestolabs.model.Instrument.Companion.Volume24Compator
import com.jiwon.prestolabs.model.helper.QuickSort.quickSortedWith
import com.jiwon.prestolabs.repository.InstrumentRepository.Companion.getDummyData
import org.junit.Test
import java.util.logging.Logger

class QuickSortTest{

    @Test
    fun `sort Instruments via Symbol comparator`(){
        val data = getDummyData().values.toTypedArray()

        val sortStart = System.nanoTime()
        val sortedData = data.sortedWith(SymbolComparator)
        val normalSortTimeTaken = System.nanoTime() - sortStart
        println("time elapsed for normal sort = $normalSortTimeTaken")

        val quickSortStart = System.nanoTime()
        val quickSortedData = data.quickSortedWith(SymbolComparator)
        val quickSortTimeTaken = System.nanoTime() - quickSortStart
        println("time elapsed for quick sort = ${quickSortTimeTaken}")
        println("quick sort is faster than just sort by ${normalSortTimeTaken - quickSortTimeTaken}")

        for(i in 0 until data.size){
//            println(
//                "sort : ${sortedData.get(i)} <====> quick sort : ${quickSortedData.get(i)}"
//            )

            val isSame = sortedData[i].symbol.get().equals(quickSortedData[i].symbol.get())
            if(!isSame){
                println("symbol compared original : ${sortedData[i].symbol} ==== implemented ${quickSortedData[i].symbol}")
            }
            assert(isSame)
        }
    }
    @Test
    fun `sort Instruments via Price comparator`(){
        val data = getDummyData().values.toTypedArray()
        val sortStart = System.nanoTime()
        val sortedData = data.sortedWith(PriceComparator)
        val normalSortTimeTaken = System.nanoTime() - sortStart
        println("time elapsed for normal sort = $normalSortTimeTaken")

        val quickSortStart = System.nanoTime()
        val quickSortedData = data.quickSortedWith(PriceComparator)
        val quickSortTimeTaken = System.nanoTime() - quickSortStart
        println("time elapsed for quick sort = ${quickSortTimeTaken}")
        println("quick sort is faster than just sort by ${normalSortTimeTaken - quickSortTimeTaken}")

        for(i in 0 until data.size){
//            println(
//                "sort : ${sortedData.get(i)} <====> quick sort : ${quickSortedData.get(i)}"
//            )


            val isSame = sortedData[i].symbol.equals(quickSortedData[i].symbol)
                    || sortedData[i].lastPrice.get() == quickSortedData[i].lastPrice.get()
            if(!isSame){
                println("price compared original : ${sortedData[i].symbol.get()}, ${sortedData[i].lastPrice.get()} ==== implemented ${quickSortedData[i].symbol.get()}, ${quickSortedData[i].lastPrice.get()}")
            }
            assert(isSame)

        }
    }

    @Test
    fun `sort Instruments via Change comparator`(){
        val data = getDummyData().values.toTypedArray()


        val sortStart = System.nanoTime()
        val sortedData = data.sortedWith(PercentageChangeCompator)
        val normalSortTimeTaken = System.nanoTime() - sortStart
        println("time elapsed for normal sort = $normalSortTimeTaken")

        val quickSortStart = System.nanoTime()
        val quickSortedData = data.quickSortedWith(PercentageChangeCompator)
        val quickSortTimeTaken = System.nanoTime() - quickSortStart
        println("time elapsed for quick sort = ${quickSortTimeTaken}")
        println("quick sort is faster than just sort by ${normalSortTimeTaken - quickSortTimeTaken}")

        for(i in 0 until data.size){
//            println(
//                "sort : ${sortedData.get(i)} <====> quick sort : ${quickSortedData.get(i)}"
//            )

            val isSame = sortedData[i].symbol.get().equals(quickSortedData[i].symbol.get())
                    || sortedData[i].lastChangePercentage.get() == quickSortedData[i].lastChangePercentage.get()
            if(!isSame){
                println("lastChangePercentage compared original : ${sortedData[i].symbol}, ${sortedData[i].lastChangePercentage} ==== implemented ${quickSortedData[i].symbol}, ${quickSortedData[i].lastChangePercentage}")
            }
            assert(isSame)
        }
    }

    @Test
    fun `sort Instruments via volume comparator`(){
        val data = getDummyData().values.toTypedArray()

        val sortStart = System.nanoTime()
        val sortedData = data.sortedWith(Volume24Compator)
        val normalSortTimeTaken = System.nanoTime() - sortStart
        println("time elapsed for normal sort = $normalSortTimeTaken")

        val quickSortStart = System.nanoTime()
        val quickSortedData = data.quickSortedWith(Volume24Compator)
        val quickSortTimeTaken = System.nanoTime() - quickSortStart
        println("time elapsed for quick sort = ${quickSortTimeTaken}")
        println("quick sort is faster than just sort by ${normalSortTimeTaken - quickSortTimeTaken}")

        for(i in 0 until data.size){
//            println(
//                "sort : ${sortedData.get(i)} <====> quick sort : ${quickSortedData.get(i)}"
//            )


            val isSame = sortedData[i].symbol.get().equals(quickSortedData[i].symbol.get())
                    || sortedData[i].volume24.get() == quickSortedData[i].volume24.get()
            if(!isSame){
                println("volume24 compared : original : ${sortedData[i].symbol.get()}, ${sortedData[i].lastChangePercentage.get()} ==== implemented ${quickSortedData[i].symbol.get()}, ${quickSortedData[i].lastChangePercentage.get()}")
            }
            assert(isSame)
        }
    }
}