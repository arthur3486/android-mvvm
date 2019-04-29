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

package com.arthurivanets.sample.ui.events.list

import com.arthurivanets.adapster.databinding.ObservableTrackableArrayList
import com.arthurivanets.commons.data.rx.ktx.resultOrError
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.adapters.events.EventItem
import com.arthurivanets.sample.domain.entities.Event
import com.arthurivanets.sample.domain.repositories.events.EventsRepository
import com.arthurivanets.sample.ui.base.AbstractDataLoadingViewModel
import com.arthurivanets.sample.ui.events.DEFAULT_EVENT_LOADING_LIMIT

class EventsViewModelImpl(
    private val eventsRepository : EventsRepository
) : AbstractDataLoadingViewModel(), EventsViewModel {


    override val items = ObservableTrackableArrayList<Long, EventItem>()


    override fun onStart() {
        super.onStart()

        loadInitialData()
    }


    override fun onEventClicked(item : EventItem) {
        dispatchEvent(EventsViewModelEvents.OpenEventInfoScreen(item.itemModel))
    }


    private fun loadInitialData() {
        if(items.isEmpty()) {
            loadData()
        }
    }


    private fun loadData() {
        if(isLoading) {
            return
        }

        isLoading = true

        eventsRepository.getEvents(0, DEFAULT_EVENT_LOADING_LIMIT)
            .resultOrError()
            .typicalBackgroundWorkSchedulers()
            .subscribe(::onLoadedSuccessfully, ::onLoadingFailed)
            .manageLongLivingDisposable()
    }


    private fun onLoadedSuccessfully(events : List<Event>) {
        isLoading = false

        events.forEach { items.addOrUpdate(EventItem(it)) }
    }


    private fun onLoadingFailed(throwable : Throwable) {
        isLoading = false

        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }


}