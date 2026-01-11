package com.example.kotlin.ownviewmodel

import android.util.Log
import com.example.kotlin.ownviewmodel.core.MyViewModel

class CounterViewModel : MyViewModel {
    private var count = 0

    init {
        Log.i("MyTestingViewModel", "ViewModel created")
    }

    fun getCount(): Int {
        return count
    }

    fun incrementCount() {
        count++
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MyTestingViewModel", "ViewModel cleared")
    }
}
