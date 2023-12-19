package com.example.kotlin

/**
 * Android Design Patterns
 * - Creational Pattern (How to create objects?)
 *   - Singleton - Only one instance throughout the application
 *   - Builder - Step by step approach to build complex object
 *   - Factory - Create multiple objects without specifying the exact class of object that will be created
 *   - Dependency Injection - Passing dependency from outside via constructor
 * - Structural Pattern (How to compose (relationship) objects?)
 *   - Adapter - Converts the interface of a class into another interface that a client wants
 *   - Facade
 *   - Decorator
 * - Behavioural Pattern (How you coordinate object interactions)
 *   - Observer - (Flows, Live data) Pattern where any changes are observed by all subscribers
 *   - Command
 * - Android Architecture Pattern
 *   - MVC
 *   - MVP
 *   - MVVM
 */

/**
 * Singleton Pattern
 */
class Singleton private constructor(){
    companion object {

        private var instance: Singleton? = null
        fun getInstance(): Singleton {
            if(instance == null) {
                synchronized(this) {
                    if(instance == null) {
                        instance = Singleton()
                    }
                }
            }
            return instance!!
        }
    }
}

/**
 * Builder Pattern
 */
class Person private constructor(
    val name: String,
    val sirName: String,
    val age: Int
) {
    class Builder {
        private var name: String = ""
        private var sirName: String = ""
        private var age: Int = 0

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setSirName(sirName: String): Builder {
            this.sirName = sirName
            return this
        }

        fun setAge(age: Int): Builder {
            this.age = age
            return this
        }

        fun build(): Person {
            return Person(name, sirName, age)
        }
    }
}

/**
 * Factory Pattern
 */
interface Coffee {
    fun recipe(): String
}
class CaffeLatte : Coffee {
    override fun recipe(): String  ="Expresso"
}
class Americano : Coffee {
    override fun recipe(): String = "Expresso, Hot water"
}
object CoffeeFactory {
    enum class Type{
        LATTE, AMERICANO
    }
    fun getCoffee(type: Type): Coffee{
        if (type == CoffeeFactory.Type.LATTE){
            return CaffeLatte()
        }else if(type == CoffeeFactory.Type.AMERICANO){
            return Americano()
        }
        throw IllegalArgumentException("Can't handle")
    }
}

/**
 * Adapter Pattern
 */
interface Android {
    fun charge()
}
interface IPhone {
    fun charge()
}
class AndroidCharger : Android {
    override fun charge() {
        println("Android phone is charging")
    }
}
class IPhoneCharger : IPhone {
    override fun charge() {
        println("Iphone is charging")
    }
}
//adapter pattern - changing the function working
class IPhoneToAndroidAdapter(val androidPhone: Android) : IPhone {
    override fun charge() {
        androidPhone.charge()
    }
}

fun main() {

    /**
     * Singleton Pattern
     */
    val singleton1 = Singleton.getInstance()
    val singleton2 = Singleton.getInstance()
    println(singleton1.hashCode() == singleton2.hashCode()) //true

    /**
     * Builder Pattern
     */
    val person = Person.Builder()
        .setName("Khush")
        .setSirName("Panchal")
        .setAge(23)
        .build()
    println(person.name + person.sirName + person.age)

    /**
     * Factory Pattern
     */
    val coffee = CoffeeFactory.getCoffee(CoffeeFactory.Type.LATTE)
    println(coffee.recipe()) //prints latte recipe

    /**
     * Adapter Pattern
     */
    val androidCharger = AndroidCharger()
    val adapter =  IPhoneToAndroidAdapter(androidCharger) //even if implements iPhone, delegated to android
    adapter.charge();

}