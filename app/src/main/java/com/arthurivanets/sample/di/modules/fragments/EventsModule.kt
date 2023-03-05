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
import com.arthurivanets.sample.domain.repositories.events.EventsRepository
import com.arthurivanets.sample.ui.events.info.EventInfoFragment
import com.arthurivanets.sample.ui.events.info.EventInfoViewModel
import com.arthurivanets.sample.ui.events.list.EventsFragment
import com.arthurivanets.sample.ui.events.list.EventsViewModel
import dagger.Module
import dagger.Provides

@Module
class EventsModule {

    @Provides
    fun provideEventsViewModel(
        fragment: EventsFragment,
        eventsRepository: EventsRepository,
        schedulerProvider: SchedulerProvider
    ): EventsViewModel {
        return fragment.provideViewModel {
            EventsViewModel(
                eventsRepository = eventsRepository,
                schedulerProvider = schedulerProvider
            )
        }
    }

    @Provides
    fun provideEventInfoViewModel(
        fragment: EventInfoFragment,
        eventsRepository: EventsRepository,
        schedulerProvider: SchedulerProvider
    ): EventInfoViewModel {
        return fragment.provideViewModel {
            EventInfoViewModel(
                eventsRepository = eventsRepository,
                schedulerProvider = schedulerProvider
            )
        }
    }

}
