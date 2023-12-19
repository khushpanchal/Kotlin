package com.example.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FlowAndChannel {
    val myFlow = MutableSharedFlow<Job>(extraBufferCapacity = Int.MAX_VALUE)
    val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            myFlow.collect {
                it.join()
            }
        }
    }

    // CoroutineStart.LAZY purpose is, it launch when join()
    fun emit() {
        myFlow.tryEmit(
            scope.launch(start = CoroutineStart.LAZY) {
                println("a started")
                delay(1000)
                println("a completed")
            }
        )


        myFlow.tryEmit(
            scope.launch(start = CoroutineStart.LAZY) {
                println("b started")
                delay(1000)
                println("b completed")
            }
        )

        myFlow.tryEmit(
            scope.launch(start = CoroutineStart.LAZY) {
                println("c started")
                delay(1000)
                println("c completed")
            }
        )
    }
}

fun main() {
    runBlocking {
        val fc = FlowAndChannel()
        fc.emit()
        delay(5000)
    }
}