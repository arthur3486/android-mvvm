package com.arthurivanets.mvvm.commons

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CommonsModule::class])
interface CoreComponent : ExposesDependencies {
    
    
    fun inject(application : Application)
    
    
    @Component.Builder
    interface Builder {
        
        @BindsInstance
        fun application(application : Application) : Builder
        
        fun build() : CoreComponent
        
    }
    
    
}