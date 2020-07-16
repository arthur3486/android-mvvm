package com.arthurivanets.mvvm

import android.os.Handler
import android.os.Looper
import java.util.*


typealias Consumer<T> = (T) -> Unit


interface Channel<T> {
    
    var consumer : Consumer<T>?

}


interface WritableChannel<T> : Channel<T> {
    
    fun post(payload : T)
    
}


abstract class AbstractChannel<T> internal constructor() : WritableChannel<T> {
    
    
    final override var consumer : Consumer<T>? = null
        set(value) {
            field = value
            
            if (value != null) {
                onConsumerAttached(value)
            } else {
                onConsumerDetached()
            }
        }
    
    protected val isConsumerAttached : Boolean
        get() = (consumer != null)
    
    
    protected open fun onConsumerAttached(consumer : Consumer<T>) {
        // to be overridden.
    }
    
    
    protected open fun onConsumerDetached() {
        // to be overridden.
    }
    
    
}


class BufferedChannel<T> : AbstractChannel<T>() {
    
    
    private val bufferedEvents = ArrayDeque<T>()
    
    
    override fun onConsumerAttached(consumer : Consumer<T>) {
        while (bufferedEvents.isNotEmpty()) {
            bufferedEvents.poll()?.let { consumer(it) }
        }
    }
    
    
    override fun post(payload : T) {
        if (isConsumerAttached) {
            consumer?.invoke(payload)
        } else {
            bufferedEvents.add(payload)
        }
    }
    
    
}


private class MainThreadChannel<T>(private val channel : WritableChannel<T>) : WritableChannel<T> {
    
    
    private val handler = Handler(Looper.getMainLooper())
    
    override var consumer : Consumer<T>?
        set(value) { channel.consumer = value }
        get() = channel.consumer
    
    private var postAction : Runnable? = null
    
    
    override fun post(payload : T) {
        postAction?.let { handler.removeCallbacks(it) }
        postAction = Runnable { channel.post(payload) }
        
        handler.post(postAction)
    }
    
    
}


fun <T> WritableChannel<T>.asMainThreadChannel() : WritableChannel<T> {
    return MainThreadChannel(this)
}