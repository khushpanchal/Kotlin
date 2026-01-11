package com.example.kotlin

import android.app.Application
import com.example.kotlin.ownviewmodel.core.MyViewModelStore
import java.util.HashMap

class MainApplication : Application() {
    private val viewModelStores = HashMap<String, MyViewModelStore>()

    fun getViewModelStore(key: String): MyViewModelStore {
        return viewModelStores.getOrPut(key) { MyViewModelStore() }
    }

    fun clearViewModelStore(key: String) {
        viewModelStores.get(key)?.clear()
        viewModelStores.remove(key)
    }
}
