package com.example.kotlin

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"

        fun log(msg: Any?) {
            Log.i(TAG, msg.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        log("Activity create")
    }

    private val handler = Handler()

    override fun onStart() {
        super.onStart()
        //log("Cores: ${Runtime.getRuntime().availableProcessors()}") - 8 processors
        handler.postDelayed({
            log("inside runnable")
            printThreadName() //main thread
        }, 100)

        Thread(null,{
            log("inside new thread") //khush thread
            printThreadName()
        },"khush").start()

        val thread = HandlerThread("khush 1")
        thread.start() //khush 1 thread
        Handler(thread.looper).postDelayed( //passing looper of khush 1 thread
            {
                log("inside runnable")
                printThreadName()//khush 1 thread
            }, 0
        )
    }


    fun printThreadName() {
        log("thread: " + Thread.currentThread().name)
    }

    override fun onDestroy() {
        super.onDestroy()
//        log("Activity destroy")
    }

}