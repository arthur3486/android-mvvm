/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.l@gmail.com
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

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.arthurivanets.dagger.androidx.AndroidXHasSupportFragmentInjector
import com.arthurivanets.sample.di.components.AppDependenciesComponent
import com.arthurivanets.sample.di.components.DaggerAppDependenciesComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

abstract class BaseApplication : MultiDexApplication(), HasActivityInjector, AndroidXHasSupportFragmentInjector {


    lateinit var dependenciesComponent : AppDependenciesComponent
        private set

    @Inject
    lateinit var activityDispatchingAndroidInjector : DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>


    final override fun onCreate() {
        super.onCreate()

        initDagger()
        onInjectDependencies(dependenciesComponent)
        onInit()
    }


    private fun initDagger() {
        dependenciesComponent = DaggerAppDependenciesComponent.builder()
            .application(this)
            .build()

        dependenciesComponent.inject(this)
    }


    protected open fun onInjectDependencies(injector : AppDependenciesComponent) {
        //
    }


    protected open fun onInit() {
        //
    }


    final override fun activityInjector() : AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }


    final override fun supportFragmentInjector() : AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }


    final override fun onTrimMemory(level : Int) {
        super.onTrimMemory(level)

        if(level >= TRIM_MEMORY_BACKGROUND) {
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