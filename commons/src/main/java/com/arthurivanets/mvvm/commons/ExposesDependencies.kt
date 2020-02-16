package com.arthurivanets.mvvm.commons

import android.content.Context
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider

interface ExposesDependencies {
    
    fun exposeContext() : Context
    
    fun exposeSchedulerProvider() : SchedulerProvider
    
}