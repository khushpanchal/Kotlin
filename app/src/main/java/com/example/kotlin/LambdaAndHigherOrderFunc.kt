package com.example.kotlin

class LambdaAndHigherOrderFunc {
}

//lambda expression (surrounded by curly braces), passed as function to a variable
val performAddition: (Int, Int) -> Int = { i, j -> i+j }

//This function twice the value of result from the calculation passed in lambdaVariable
fun twiceMathFunc(a: Int, b: Int, lambdaVariable: (Int,Int)->Int): Int {
    val res = lambdaVariable.invoke(a,b) //or can be written as lambdaVariable(a,b)
    return res*2
}

//higher order function (function taking function as an argument) that takes block (which is lambda) and runs at appropriate time
fun calculateTimeInMs(block: ()->Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

fun main() {
    val res = twiceMathFunc(3,5, performAddition) //(3+5)*2 = 16
    val res2 = twiceMathFunc(3, 5) {x,y->
        x*y
    } //3*5*2 = 30
    println(res)
    println(res2)

    val timeTaken = calculateTimeInMs {
        for(i in 1 until  10000000000) {

        }
    }
    println(timeTaken) //around 3 seconds

}