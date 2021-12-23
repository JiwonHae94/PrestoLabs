package com.jiwon.prestolabs.model.helper

object QuickSort {
    // Sort element with quick sort
    fun <T>Array<out T>.quickSortedWith(comparator : Comparator<in T>) : List<T>{
        if(size < 2)
            return this.toList()

        qsort(this, comparator)
        return this.toList()
    }

    private fun <T> qsort(
        array : Array<T>,
        comparator : Comparator<in T>,
        left : Int = 0,
        right : Int = array.size - 1
    ) {
        val index = partition(array, comparator, left, right)
        if (left < index - 1) {
            qsort(array, comparator, left, index - 1)
        }
        if (index < right) {
            qsort(array, comparator, index, right)
        }
    }

    private fun <T> partition(
        array: Array<T>,
        comparator : Comparator<in T>,
        start: Int,
        end: Int
    ): Int {
        var left = start
        var right = end
        val pivot = array[(left + right) / 2]

        while (left <= right) {
            while (comparator.compare(array[left], pivot) < 0) {
                left++
            }

            while (comparator.compare(array[right], pivot) > 0) {
                right--
            }

            if (left <= right) {
                val temp = array[left]
                array[left] = array[right]
                array[right] = temp
                left++
                right--
            }
        }
        return left
    }
}