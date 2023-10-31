package com.example.kotlin

class Generics

enum class Difficulty {
    EASY, MEDIUM, HARD
}

data class Question<T> (
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

fun <T> doSomething(value: T) {
    println("Doing something with value: $value")// OK
    //println("Doing something with type: ${T::class.simpleName}")  // Error
}

//at compile time, the compiler removes the type argument from the function call. This is called type erasure
//The reified modifier before the type parameter enables the type information to be retained at runtime
//Refied let us use reflection on type parameter
//function must be inline to use reified
inline fun <reified T> doSomethingAgain(value: T) {
    println("Doing something with type: ${T::class.simpleName}")    // OK
}

//Generics out = Covariant, In java (? extends T), Produces of T or its subtype
interface Producer<out T> {
    fun produce(): T
    //fun consumes(item: T) //This will show compile time error
} // can use T and its subtypes (T will be parent class)

//Generics in = Contravariant, In java (? super T), Consumer of T or its subtype
interface Consumer<in T> {
    fun consumes(item: T)
    //fun produce(): T //This will show compile time error
} // can use T and its subtypes (T will be parent class)

open class Annimal
class Dog: Annimal()
class Cat: Annimal()

class Gen: Producer<Annimal>, Consumer<Annimal> {
    private val animalList = mutableListOf<Annimal>()
    override fun produce(): Annimal {
        return animalList.first()
    }

    override fun consumes(item: Annimal) {
        animalList.add(item)
    }

}

fun main() {
//    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
//    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
//    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)
    val gen = Gen()
    gen.consumes(Dog())
    gen.consumes(Cat())
    println(gen.produce())
}

//UIState<Response>, Any Response and its subtype can be used now in Success, Failure
sealed interface UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>
    data class Failure<T>(val throwable: Throwable, val data: T? = null) : UIState<T>
    object Loading : UIState<Nothing>
    object Empty : UIState<Nothing>
}
