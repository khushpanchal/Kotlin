package com.example.kotlin

class OperatorAndInvoke {
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point): Point {
        return Point(this.x + point.x, this.y + point.y)
    }

    //invoke function can get called as function declaration to object
    operator fun invoke(point: Point) {
        println(point.toString())
    }
    operator fun invoke(str: String) {
        println(str)
    }
}

class MathFunction(val block: (Int)->(Int)) {
    companion object {
        operator fun invoke(str: String) {
            println(str)
        }
    }

    operator fun invoke(x: Int) {
        println(block(x))
    }

}

fun main() {
    val x = Point(1,2)
    val y = Point(2,3)
    //"+" will give error without operator fun (overloads the plus operator)
    val z = x+y

    z(z) // seems like calling some function -> this will delgate the call to invoke(point)
    z("khush")

    val squareNum = MathFunction {num -> num*num}
    squareNum(5) //this will call invoke function (invoke is built in function that we are overloading)

    val squareNum2 = MathFunction {num -> num*num}
    //squareNum2("khush") //Error, since invoke function is inside companion object, therefore not associated with this class object
    MathFunction("khush") // this will print khush, as this will invoke companion object

    /**
    class MathFunction(val block: (Int)->(Int)) {
        companion object A {
            operator fun invoke(str: String) {
                println(str)
            }
        }
    }

     if give some name to companion object you can call
     MathFunction.A("khush") to call the invoke funtion
     */

}