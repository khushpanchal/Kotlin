package com.example.kotlin

fun main() {
    val listener = object: InterfaceClass.Listener {
        override fun listen() {
            println("main anonymous Listener")
        }
    }
    listener.listen() // call anonymous class listen method

    val common = InterfaceClass.Common()
    common.setOnListeners(listOf(InterfaceClass.A(), InterfaceClass.B(), InterfaceClass.C()))
    common.callAllListenerAtOnce()
}

//added nested class only for purpose of Redeclaration error (creating same class name in multiple files)
object InterfaceClass {

    class Common {

        private var mListenersList: List<Listener>? = null

        fun setOnListeners(listeners: List<Listener>) {
            mListenersList = listeners
        }

        fun callAllListenerAtOnce() {
            mListenersList?.forEach {
                it.listen()
            }
        }
    }

    interface Listener {
        fun listen()
    }

    class A: Listener {
        override fun listen() {
            println(this.javaClass.simpleName)
        }
    }

    class B: Listener {
        override fun listen() {
            println(this.javaClass.simpleName)
        }
    }

    class C: Listener {
        override fun listen() {
            println(this.javaClass.simpleName)
        }
    }
}