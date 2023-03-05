/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurivanets.sample

import androidx.multidex.MultiDexApplication
import com.arthurivanets.mvvm.commons.DaggerCoreComponent
import com.arthurivanets.sample.di.components.AppDependenciesComponent
import com.arthurivanets.sample.di.components.DaggerAppDependenciesComponent
import com.arthurivanets.sample.domain.di.DaggerDomainComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseApplication : MultiDexApplication(), HasAndroidInjector {

    lateinit var dependenciesComponent: AppDependenciesComponent
        private set

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    final override fun onCreate() {
        super.onCreate()

        initDagger()
        onInjectDependencies(dependenciesComponent)
        onInit()
    }

    private fun initDagger() {
        val coreComponent = DaggerCoreComponent.builder()
            .application(this)
            .build()

        val domainComponent = DaggerDomainComponent.builder()
            .coreComponent(coreComponent)
            .build()

        DaggerAppDependenciesComponent.builder()
            .coreComponent(coreComponent)
            .domainComponent(domainComponent)
            .build()
            .also { dependenciesComponent = it }
            .inject(this)
    }

    protected open fun onInjectDependencies(injector: AppDependenciesComponent) {
        //
    }

    protected open fun onInit() {
        //
    }

    final override fun androidInjector(): AndroidInjector<Any> {
        return activityDispatchingAndroidInjector
    }

    final override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        if (level >= TRIM_MEMORY_BACKGROUND) {
            onCleanUpResources()
        }
    }

    /**
     *
     */
    protected open fun onCleanUpResources() {
        //
    }

}
