package com.example.kotlin

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegation in Kotlin: Passing the responsibility to other class or method
 */
//interface
interface Action {
    fun eat()
    fun breathe()
}
//Animal implements action
class Animal(val name: String): Action {
    override fun eat() {
        println("$name eats")
    }
    override fun breathe() {
        println("$name breathes")
    }
}
//Bird implements action, but implementation is same as other animals, delegate to implementation details to Animal
class Bird(private val name: String): Action by Animal(name){
    fun fly() {
        println("$name flies")
    }
}
//Same as Bird, have to implement Action, but same as other animal, so delegate
class AquaticAnimal(private val name: String): Action by Animal(name){
    fun swim() {
        println("$name swims")
    }
}

//Other example
interface ApplyOnce {
    fun getAppliedOnce()
}
class IapplyInterface(): ApplyOnce {
    override fun getAppliedOnce() {
        println("I have applied once")
    }
}
class LetsTryApplyInterface: ApplyOnce by IapplyInterface(){
}



/**
 * Property Delegation: Delegate Property (pass responsibility of getter and setter) to other class
 */
class Name {
    constructor(firstName: String?, lastName: String?) {
        this.firstName = firstName
        this.lastName = lastName
    }
    var firstName: String? by NameDelegate()
    var lastName: String? by NameDelegate()
}

class NameDelegate : ReadWriteProperty<Any?, String?> {
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
fun main() {
    /**
     * Delegation
     */
    val parrot = Bird("Parrot")
    parrot.eat()
    parrot.breathe()
    parrot.fly()

    val fish = AquaticAnimal("Fish")
    fish.eat()
    fish.breathe()
    fish.swim()

    LetsTryApplyInterface().getAppliedOnce()

    /**
     * Property Delegation
     */
    val lastName = Name("Khush", "Panchal").lastName
    println(lastName)

}