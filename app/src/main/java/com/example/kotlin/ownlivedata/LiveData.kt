package com.example.kotlin.ownlivedata

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

typealias Observer<T> = (T?) -> Unit

open class LiveData<T> {

    private var data: T? = null
    private val observerToWrapperMap: HashMap<Observer<T?>, LifecycleObserverWrapper> = HashMap()

    protected open fun setValue(value: T) {
        this.data = value
        observerToWrapperMap.values.forEach {
            if (it.shouldNotify()) {
                notifyChange(it)
            }
        }
    }

    fun getValue(): T? {
        return this.data
    }

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        val lifecycleObserverWrapper = LifecycleObserverWrapper(owner, observer)
        owner.lifecycle.addObserver(lifecycleObserverWrapper)
        observerToWrapperMap[observer] = lifecycleObserverWrapper
    }

    fun removeObserver(observer: Observer<T>) {
        val lifecycleObserverWrapper = observerToWrapperMap.remove(observer)
        lifecycleObserverWrapper?.owner?.lifecycle?.removeObserver(lifecycleObserverWrapper)
    }

    fun removeObservers(owner: LifecycleOwner) {
        val toBeRemovedObservers = arrayListOf<Observer<T>>()
        observerToWrapperMap.values.forEach {
            if (it.owner == owner) {
                toBeRemovedObservers.add(it.observer)
            }
        }
        toBeRemovedObservers.forEach {
            removeObserver(it)
        }
    }

    private fun notifyChange(lifecycleObserverWrapper: LifecycleObserverWrapper) {
        lifecycleObserverWrapper.observer.invoke(this.data)
    }

    private inner class LifecycleObserverWrapper(
        val owner: LifecycleOwner,
        val observer: Observer<T>
    ) : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            notifyChange(this)
        }

        override fun onResume(owner: LifecycleOwner) {
            notifyChange(this)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            removeObserver(observer)
        }

        fun shouldNotify(): Boolean {
            return owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        }

    }

}