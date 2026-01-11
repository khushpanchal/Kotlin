package com.example.kotlin.ownviewmodel.core

import java.util.HashMap

class MyViewModelStore {
    private val map = HashMap<String, MyViewModel>()

    fun put(key: String, viewModel: MyViewModel) {
        val oldViewModel = map.get(key)
        oldViewModel?.onCleared()
        map[key] = viewModel
    }

    fun get(key: String): MyViewModel? {
        return map[key]
    }

    fun clear() {
        for (vm in map.values) {
            vm.onCleared()
        }
        map.clear()
    }
}
