package com.example.kotlin.ownlivedata

typealias TestObserver<T> = (T?) -> Unit

// without LifeCycle

class LiveDataNoLifeCycle<T> {

    private var data: T? = null

    private val observers = arrayListOf<TestObserver<T>>()

    fun setValue(value: T) {
        this.data = value
        observers.forEach { it ->
               it.invoke(this.data)
        }
    }

    fun getValue(): T? {
        return this.data
    }

    fun observe(observer: TestObserver<T>){
        observers.add(observer)
    }

}

fun tryLiveDataNoLifeCycle() {
    val testLiveData = LiveDataNoLifeCycle<Int>()

    testLiveData.observe {

    }

    testLiveData.setValue(10)
}