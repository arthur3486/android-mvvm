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

package com.arthurivanets.sample.di.components

import android.app.Application
import com.arthurivanets.sample.di.modules.ActivityBuilder
import com.arthurivanets.sample.di.modules.FragmentBuilder
import com.arthurivanets.sample.di.modules.general.AppModule
import com.arthurivanets.sample.di.modules.general.ItemResourcesModule
import com.arthurivanets.sample.di.modules.general.UtilitiesModule
import com.arthurivanets.sample.domain.di.modules.CharactersModule
import com.arthurivanets.sample.domain.di.modules.ComicsModule
import com.arthurivanets.sample.domain.di.modules.DomainUtilsModule
import com.arthurivanets.sample.domain.di.modules.EventsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class,
    FragmentBuilder::class,
    CharactersModule::class,
    EventsModule::class,
    ComicsModule::class,
    ItemResourcesModule::class,
    DomainUtilsModule::class,
    UtilitiesModule::class
])
interface AppDependenciesComponent : Injectables {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application : Application) : Builder

        fun build() : AppDependenciesComponent

    }


}