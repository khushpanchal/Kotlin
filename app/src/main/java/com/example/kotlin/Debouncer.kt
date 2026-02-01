package com.example.kotlin

import kotlinx.coroutines.*

class Debouncer(
    private val delayMillis: Long,
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    // Executes the action after the delay, cancelling pending actions
    fun call(action: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            action()
        }
    }
}


fun main() {
    val scope = CoroutineScope(Dispatchers.IO)
    val searchDebouncer = Debouncer(300L, scope)

    runBlocking {
        searchDebouncer.call {
            println("a")
        }

        delay(350)

        searchDebouncer.call {
            println("b")
        }

        searchDebouncer.call {
            println("c")
        }
        delay(10000)
    }
}