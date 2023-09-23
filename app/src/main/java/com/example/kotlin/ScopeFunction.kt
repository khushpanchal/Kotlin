package com.example.kotlin

class ScopeFunction {
}

class A(var name: String? = null)

fun main() {

    data class Person(var name: String = "Khush")
    val personObject = Person()

    //block(this) (creates copy, so it is thread safe)
    val x = personObject.let {
        it.name + " Panchal"
    } //Return by lambda (Use personObject but can't modify)
    println(x) // Khush Panchal
    println(personObject.name) // Khush


    //block() (Extension function, so use this)
    val x1 = personObject.run {
        this.name + " Panchal"
    } //Return by lambda (lambda gets object itself, this) (Use personObject but can't modify)
    println(x1) // Khush Panchal
    println(personObject.name) // Khush


    //this
    val y = personObject.also {
        it.name = "Khush Panchal"
        "returning random string" //unused
    } // return personObject itself after lamda calling on personObject
    println(y.name) // Khush Panchal
    println(personObject.name) // Khush panchal


    //this
    val y1 = personObject.apply {
        this.name = "Khush Panchal 1"
        "returning random string" //unused
    } // return personObject itself after lamda calling on personObject (lambda gets object itself, this)
    println(y.name) // Khush Panchal 1
    println(personObject.name) // Khush Panchal 1

    val z = with(personObject) {
        this.name = "Khush Panchal 2"
        "Return random string"
    } //Return by lambda and also apply lambda on personObject
    println(z) //"Return random string"
    println(personObject.name) // Khush Panchal 2

}