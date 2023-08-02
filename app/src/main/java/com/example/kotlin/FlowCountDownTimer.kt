package com.example.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class FlowCountDownTimer {
}

suspend fun startTimer(timeInSec: Int) {
    var timeLeft = timeInSec
    CoroutineScope(Dispatchers.IO).launch {
        flow {
            while (timeLeft>=0) {
                emit(timeLeft)
                delay(1000)
                timeLeft--
            }
        }.collect {
            println(it)
        }
    }.join()
}

fun main() {
    runBlocking {
        startTimer(10)
    }
}