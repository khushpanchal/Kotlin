package com.example.kotlin

class Main

interface Action {
    fun eat()
    fun breathe()
}

class Animal(val name: String): Action {
    override fun eat() {
        println("$name eats")
    }
    override fun breathe() {
        println("$name breathes")
    }
}

class Bird(private val name: String): Action by Animal(name){
    fun fly() {
        println("$name flies")
    }
}

class AquaticAnimal(private val name: String): Action by Animal(name){
    fun swim() {
        println("$name swims")
    }
}

interface ApplyOnce {
    fun getAppliedOnce()
}

class IapplyInterface(): ApplyOnce {
    override fun getAppliedOnce() {
        println("I have applied once")
    }
}

class LetsTryApplyInterface(): ApplyOnce by IapplyInterface(){

}


fun main() {
    val parrot = Bird("Parrot")
    parrot.eat()
    parrot.breathe()
    parrot.fly()

    val fish = AquaticAnimal("Fish")
    fish.eat()
    fish.breathe()
    fish.swim()

    LetsTryApplyInterface().getAppliedOnce()

}