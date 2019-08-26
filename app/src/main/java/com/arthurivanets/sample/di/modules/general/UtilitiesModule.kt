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

package com.arthurivanets.sample.di.modules.general

import com.arthurivanets.commons.rx.schedulers.DefaultSchedulerProvider
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.sample.imageloading.ImageLoader
import com.arthurivanets.sample.imageloading.glide.GlideImageLoader
import dagger.Module
import dagger.Provides

@Module
class UtilitiesModule {


    @Provides
    fun provideImageLoader() : ImageLoader {
        return GlideImageLoader()
    }
    
    
    @Provides
    fun provideSchedulerProvider() : SchedulerProvider {
        return DefaultSchedulerProvider()
    }


}