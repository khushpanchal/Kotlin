package com.example.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FlowCountdownTimer(
    private val scope: CoroutineScope,
    private val totalTimeMillis: Long,
    private val intervalMillis: Long = 1000L
) {
    private var timerJob: Job? = null
    private var timeLeft = totalTimeMillis

    // Using MutableStateFlow to emit time remaining
    private val _timerState = MutableStateFlow(totalTimeMillis)
    val timerState: StateFlow<Long> = _timerState.asStateFlow()

    // Using MutableStateFlow to track if timer is running
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    fun start() {
        if (timerJob?.isActive == true) return

        _isRunning.value = true
        timerJob = scope.launch {
            while (timeLeft > 0) {
                _timerState.value = timeLeft
                delay(intervalMillis)
                timeLeft -= intervalMillis
            }
            _timerState.value = 0
            _isRunning.value = false
        }
    }

    fun pause() {
        timerJob?.cancel()
        _isRunning.value = false
    }

    fun resume() {
        if (timerJob?.isActive == true || timeLeft <= 0) return
        start()
    }

    fun reset() {
        timerJob?.cancel()
        timeLeft = totalTimeMillis
        _timerState.value = totalTimeMillis
        _isRunning.value = false
    }
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