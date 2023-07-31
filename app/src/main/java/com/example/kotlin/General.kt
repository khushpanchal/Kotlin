package com.example.kotlin

class General {
    class A (x: Int, val y: Int) {
        //x is constructor parameter, and y is property as declared with val or var
        val z = x //can access x here
        fun print() {
            println(y)
            //println(x)//error, cannot access x
            println(z)
        }
    }

    val x = 2
    class B {
        fun print() {
            //println(x) // error, cannot access outer class object
        }
    }
    inner class C {
        fun print() {
            println(x) // no error, now can access outer class object
        }
    }
}

fun main() {
    General.A(4,5).print()
}
