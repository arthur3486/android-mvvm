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

package com.arthurivanets.sample.ui.comics.list

import com.arthurivanets.adapster.databinding.ObservableTrackableArrayList
import com.arthurivanets.commons.data.rx.ktx.resultOrError
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.repositories.comics.ComicsRepository
import com.arthurivanets.sample.ui.base.AbstractDataLoadingViewModel
import com.arthurivanets.sample.ui.comics.DEFAULT_COMICS_LOADING_LIMIT

class ComicsViewModelImpl(
    private val comicsRepository : ComicsRepository
) : AbstractDataLoadingViewModel(), ComicsViewModel {


    override val items = ObservableTrackableArrayList<Long, ComicsItem>()


    override fun onStart() {
        super.onStart()

        loadInitialData()
    }


    override fun onComicsClicked(item : ComicsItem) {
        dispatchEvent(ComicsViewModelEvents.OpenComicsInfoScreen(item.itemModel))
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

        comicsRepository.getComics(0, DEFAULT_COMICS_LOADING_LIMIT)
            .resultOrError()
            .typicalBackgroundWorkSchedulers()
            .subscribe(::onLoadedSuccessfully, ::onLoadingFailed)
            .manageLongLivingDisposable()
    }


    private fun onLoadedSuccessfully(comics : List<Comics>) {
        isLoading = false

        comics.forEach { items.addOrUpdate(ComicsItem(it)) }
    }


    private fun onLoadingFailed(throwable : Throwable) {
        isLoading = false

        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }


}