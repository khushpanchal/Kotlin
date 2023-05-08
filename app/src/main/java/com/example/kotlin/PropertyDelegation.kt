package com.example.kotlin

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PropertyDelegation

fun main() {
    val lastName = Name("Khush", "Panchal").lastName
    println(lastName)
}

class Name {
    constructor(firstName: String?, lastName: String?) {
        this.firstName = firstName
        this.lastName = lastName
    }
    var firstName: String? by NameDelegate()


    var lastName: String? by NameDelegate()

}

class NameDelegate : ReadWriteProperty<Any?, String?>{
    var formattedVal: String? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return formattedVal
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if(!value.isNullOrEmpty()) {
            formattedVal = value
        }
    }


}
