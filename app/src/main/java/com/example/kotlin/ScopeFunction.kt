package com.example.kotlin

/**
 * let   -  T.let(block: (T) -> R): R              ------- {it-> },         Returns last line
 * run   -  T.run(block: T.() -> R): R             ------- //object itself, Returns last line
 * also  -  T.also(block: (T) -> Unit): T          ------- {it-> },         Returns nothing
 * apply -  T.apply(block: T.() -> Unit): T        ------- //object itself, Returns nothing
 * with  -  with(receiver: T, block: T.() -> R): R ------- //object itself, Returns last line
 */

fun main() {

    data class Person(var name: String = "Khush")
    val personObject = Person() //Khush

    //(creates copy, so it is thread safe)
    //Return block(this)
    val x = personObject.let {
        it.name + " Panchal"
    } //Return by lambda
    println(x) // Khush Panchal
    println(personObject.name) // Khush


    //Return block() (Extension function, so use this) (lambda gets object itself, this)
    val x1 = personObject.run {
        this.name + " Panchal"
    } //Return by lambda (lambda gets object itself, this)
    println(x1) // Khush Panchal
    println(personObject.name) // Khush


    //Return this
    val y = personObject.also {
        it.name = "Khush Panchal"
        "returning random string" //unused
    }
    println(y.name) // Khush Panchal
    println(personObject.name) // Khush panchal


    //Return this (Extension function, so use this) (lambda gets object itself, this)
    val y1 = personObject.apply {
        this.name = "Khush Panchal 1"
        "returning random string" //unused
    }
    println(y1.name) // Khush Panchal 1
    println(personObject.name) // Khush Panchal 1

    //Return block() (Extension function, so use this)
    val z = with(personObject) {
        this.name = "Khush Panchal 2"
        "Return random string"
    }
    println(z) //"Return random string"
    println(personObject.name) // Khush Panchal 2

}