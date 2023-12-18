package com.example.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
//        val m = MutexTest()
//        m.a()
//        m.b()
//
//        val d = DeadLockTest()
//        CoroutineScope(Dispatchers.IO).launch {
//            d.a()
//        }

        val ds = DeadLockTestSync()
        CoroutineScope(Dispatchers.IO).launch {
            ds.a("f")
        }

        CoroutineScope(Dispatchers.IO).launch {
            ds.a("s")
        }

        delay(20000)
    }
}

class DeadLockTestSync() {

    suspend fun a(string: String) {
        synchronized(this) {
            for(i in 1 .. 10) print("A${string}")
            println()
        }
        for(i in 1 .. 100) print(string)
    }

    suspend fun b() {
        synchronized(this) {
            for(i in 1 .. 10) print("B")
            println()
        }
    }
}

class DeadLockTest() {
    val lock = Mutex()
    suspend fun a() {
        lock.withLock {
            for(i in 1 .. 10) print("A")
            println()
            b() //<--- deadlock
        }
    }

    suspend fun b() {
        lock.withLock {
            for(i in 1 .. 10) print("B")
            println()
        }
    }
}

class MutexTest() {
    val lock = Mutex()
    val scope = CoroutineScope(Dispatchers.Unconfined)

    fun a() {
        scope.launch {
            lock.withLock {
                withContext(Dispatchers.IO) {
                    delay(1000)
                    println("A")
                }
            }
        }
    }

    fun b() {
        scope.launch {
            lock.withLock {
                withContext(Dispatchers.IO) {
                    delay(1000)
                    println("B")
                }
            }
        }
    }
}

