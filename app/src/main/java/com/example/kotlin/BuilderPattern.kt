package com.example.kotlin

class BuilderPattern {
}

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

fun main() {
    val person = Person.Builder()
        .setName("Khush")
        .setSirName("Panchal")
        .setAge(23)
        .build()

    println(person.name + person.sirName + person.age)
}