package com.example.kotlin.ownviewmodel.core

import kotlin.reflect.KClass

class MyViewModelProvider(
    private val owner: MyViewModelStoreOwner,
    private val factory: MyViewModelFactory
) {
    fun <T : MyViewModel> get(modelClass: KClass<T>): T {
        val key = modelClass.qualifiedName!!
        var viewModel = owner.getMyViewModelStore().get(key)
        if (modelClass.isInstance(viewModel)) {
            return viewModel as T
        } else {
            viewModel = factory.create(modelClass)
            owner.getMyViewModelStore().put(key, viewModel)
        }
        return viewModel as T
    }

    interface MyViewModelFactory {
        fun <T : MyViewModel> create(modelClass: KClass<T>): T
    }

}
