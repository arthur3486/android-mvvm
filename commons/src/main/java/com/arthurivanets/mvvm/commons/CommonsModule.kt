package com.arthurivanets.mvvm.commons

import android.app.Application
import android.content.Context
import com.arthurivanets.commons.rx.schedulers.DefaultSchedulerProvider
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
internal class CommonsModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return DefaultSchedulerProvider()
    }

}
