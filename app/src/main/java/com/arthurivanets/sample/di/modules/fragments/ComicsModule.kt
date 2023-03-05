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

package com.arthurivanets.sample.di.modules.fragments

import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.mvvm.util.provideViewModel
import com.arthurivanets.sample.domain.repositories.comics.ComicsRepository
import com.arthurivanets.sample.ui.comics.info.ComicsInfoFragment
import com.arthurivanets.sample.ui.comics.info.ComicsInfoViewModel
import com.arthurivanets.sample.ui.comics.list.ComicsFragment
import com.arthurivanets.sample.ui.comics.list.ComicsViewModel
import dagger.Module
import dagger.Provides

@Module
class ComicsModule {

    @Provides
    fun provideComicsViewModel(
        fragment: ComicsFragment,
        comicsRepository: ComicsRepository,
        schedulerProvider: SchedulerProvider
    ): ComicsViewModel {
        return fragment.provideViewModel {
            ComicsViewModel(
                comicsRepository = comicsRepository,
                schedulerProvider = schedulerProvider
            )
        }
    }

    @Provides
    fun provideComicsInfoViewModel(
        fragment: ComicsInfoFragment,
        comicsRepository: ComicsRepository,
        schedulerProvider: SchedulerProvider
    ): ComicsInfoViewModel {
        return fragment.provideViewModel {
            ComicsInfoViewModel(
                comicsRepository = comicsRepository,
                schedulerProvider = schedulerProvider
            )
        }
    }

}
