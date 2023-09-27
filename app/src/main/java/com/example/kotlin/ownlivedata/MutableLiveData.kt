package com.example.kotlin.ownlivedata

class MutableLiveData<T> : LiveData<T>() {

    public override fun setValue(value: T) {
        super.setValue(value)
    }

}