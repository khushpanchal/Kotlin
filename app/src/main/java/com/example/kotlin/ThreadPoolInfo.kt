package com.example.kotlin

import android.os.Handler
import android.os.Looper
import android.os.Process
import java.util.concurrent.Executor
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class ThreadInfo {
}

fun main() {

}

class PriorityThreadFactory(private val mThreadPriority: Int): ThreadFactory {

    override fun newThread(runnable: Runnable?): Thread {
        return Thread {
            kotlin.runCatching {
                Process.setThreadPriority(mThreadPriority)
            }
            runnable?.run()
        }
    }

}

class MainThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(runnable: Runnable) {
        handler.post(runnable)
    }
}

/*
* Singleton class for default executor supplier
*/
object DefaultExecutorSupplier {
    /*
    * Number of cores to decide the number of threads
    */
    val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    /*
    * thread pool executor for background tasks
    */
    private lateinit var mForBackgroundTasks: PriorityThreadPoolExecutor
    /*
    * thread pool executor for light weight background tasks
    */
    private lateinit var mForLightWeightBackgroundTasks: ThreadPoolExecutor
    /*
    * thread pool executor for main thread tasks
    */
    private lateinit var mMainThreadExecutor: Executor

    /*
    * constructor for  DefaultExecutorSupplier
    */
    init {
        // setting the thread factory
        val backgroundPriorityThreadFactory =
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        // setting the thread pool executor for mForBackgroundTasks;
        mForBackgroundTasks = PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
        NUMBER_OF_CORES * 2,
        60L,
        TimeUnit.SECONDS,
        backgroundPriorityThreadFactory
        )

        // setting the thread pool executor for mForLightWeightBackgroundTasks;
        mForLightWeightBackgroundTasks = ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
        NUMBER_OF_CORES * 2,
        60L,
        TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(),
        backgroundPriorityThreadFactory
        )

        // setting the thread pool executor for mMainThreadExecutor;
        mMainThreadExecutor = MainThreadExecutor()
    }

    /*
    * returns the thread pool executor for background task
    */
    public fun forBackgroundTasks(): PriorityThreadPoolExecutor {
        return mForBackgroundTasks;
    }

    /*
    * returns the thread pool executor for light weight background task
    */
    public fun forLightWeightBackgroundTasks(): ThreadPoolExecutor {
        return mForLightWeightBackgroundTasks
    }

    /*
    * returns the thread pool executor for main thread task
    */
    public fun forMainThreadTasks(): Executor {
        return mMainThreadExecutor
    }
}

/*
* Using it for Background Tasks
*/
fun doSomeBackgroundWork() {
    DefaultExecutorSupplier.forBackgroundTasks()
        .execute(Runnable {
            // do some background work here.
        })
}

/*
* Using it for Light-Weight Background Tasks
*/
fun doSomeLightWeightBackgroundWork() {
    DefaultExecutorSupplier.forLightWeightBackgroundTasks()
        .execute(Runnable {
            // do some light-weight background work here.
        })
}

/*
* Using it for MainThread Tasks
*/
fun doSomeMainThreadWork() {
    DefaultExecutorSupplier.forMainThreadTasks()
        .execute(Runnable {
            // do some Main Thread work here.
        })
}

/**
 * How do I cancel a task?
 * To cancel a task, you have to get the future of that task.
 * So instead of using execute, you would need to use submit,
 * which will return the future. Now, this future can be used to cancel the task.
 */

fun cancelTask() {
    /*
    * Get the future of the task by submitting it to the pool
    */
    val future = DefaultExecutorSupplier.forBackgroundTasks()
        .submit(Runnable {
            // do some background work here.
        })

    /*
    * cancelling the task
    */
    future.cancel(true)
}

/**
 * How to set the priority of a task?
 * Let’s say there are 20 tasks in a queue, and the thread pool only holds 4 threads.
 * We execute new tasks based on their priority since the thread pool can only execute 4 at a time.
 *
 * But let’s say we need the last task that we have pushed in the queue to be executed first.
 * We would need to set the IMMEDIATE priority for that task so that when the thread takes a new task from the queue,
 * it executes this task first (since it has the highest priority).
 *
 * To set the priority of a task, we need to create a thread pool executor.
 *
 * Create an ENUM for Priority:
 */

/*
 * Priority levels
 */
enum class Priority {
    /*
     * NOTE: DO NOT CHANGE ORDERING OF THOSE CONSTANTS UNDER ANY CIRCUMSTANCES.
     * Doing so will make ordering incorrect.
     */
    /*
     * Lowest priority level. Used for prefetches of data.
     */
    LOW,

    /*
     * Medium priority level. Used for warming of data that might soon get visible.
     */
    MEDIUM,

    /*
     * Highest priority level. Used for data that are currently visible on screen.
     */
    HIGH,

    /*
     * Highest priority level. Used for data that are required instantly(mainly for emergency).
     */
    IMMEDIATE
}

open class PriorityRunnable(val priority: Priority) : Runnable {

    override fun run() {
        // nothing to do here.
    }
}

class PriorityThreadPoolExecutor(
    corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long,
    unit: TimeUnit?, threadFactory: ThreadFactory?
) :
    ThreadPoolExecutor(
        corePoolSize,
        maximumPoolSize,
        keepAliveTime,
        unit,
        PriorityBlockingQueue<Runnable>(),
        threadFactory
    ) {
    override fun submit(task: Runnable): Future<*> {
        val futureTask = PriorityFutureTask(task as PriorityRunnable)
        execute(futureTask)
        return futureTask
    }

    private class PriorityFutureTask(private val priorityRunnable: PriorityRunnable) :
        FutureTask<PriorityRunnable?>(priorityRunnable, null),
        Comparable<PriorityFutureTask?> {
        /*
         * compareTo() method is defined in interface java.lang.Comparable and it is used
         * to implement natural sorting on java classes. natural sorting means the the sort
         * order which naturally applies on object e.g. lexical order for String, numeric
         * order for Integer or Sorting employee by there ID etc. most of the java core
         * classes including String and Integer implements CompareTo() method and provide
         * natural sorting.
         */
        override fun compareTo(other: PriorityFutureTask?): Int {
            val p1 = priorityRunnable.priority
            val p2 = other!!.priorityRunnable.priority
            return p2.ordinal - p1.ordinal
        }
    }
}

/*
* First of all, in DefaultExecutorSupplier, instead of ThreadPoolExecutor, use PriorityThreadPoolExecutor like this:
* Here’s an example of how we can set HIGH priority to a task:
*/

/*
* do some task at high priority
*/
fun doSomeTaskAtHighPriority() {
    DefaultExecutorSupplier.forBackgroundTasks()
        .submit(object : PriorityRunnable(Priority.HIGH) {
            override fun run() {
                // do some background work here at high priority.
            }
        })
}

