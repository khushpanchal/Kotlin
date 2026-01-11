package com.example.kotlin.ownviewmodel

import com.example.kotlin.ownviewmodel.core.MyViewModel
import com.example.kotlin.ownviewmodel.core.MyViewModelProvider
import kotlin.reflect.KClass

class CounterViewModelFactory: MyViewModelProvider.MyViewModelFactory {
    override fun <T : MyViewModel> create(modelClass: KClass<T>): T {
        return CounterViewModel() as T
    }
}