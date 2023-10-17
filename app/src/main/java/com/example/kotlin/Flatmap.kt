package com.example.kotlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class Flatmap {
}

fun main() {
    runBlocking {
        flow {
            emit("A")
            emit("B")
            emit("C")
        }.flatMapMerge { //flatMapConcat, flatMapMerge
            flow {
                delay(100)
                emit("1"+"_"+it)
                delay(100)
                emit("2"+"_"+it)
                delay(100)
                emit("3"+"_"+it)
            }
        }.collect {
            print(it+", ")
        }

    }
}
