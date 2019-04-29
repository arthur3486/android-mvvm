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

package com.arthurivanets.sample.ui.events.info

import android.os.Bundle
import com.arthurivanets.adapster.databinding.ObservableTrackableArrayList
import com.arthurivanets.commons.data.rx.ktx.resultOrError
import com.arthurivanets.commons.ktx.extract
import com.arthurivanets.commons.rx.ktx.combineResults
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.adapters.characters.CharacterItem
import com.arthurivanets.sample.adapters.characters.SmallCharacterItem
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.adapters.comics.SmallComicsItem
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.entities.Event
import com.arthurivanets.sample.domain.repositories.events.EventsRepository
import com.arthurivanets.sample.ui.base.AbstractDataLoadingViewModel
import com.arthurivanets.sample.ui.events.DEFAULT_EVENT_INFO_CHARACTER_LOADING_LIMIT
import com.arthurivanets.sample.ui.events.DEFAULT_EVENT_INFO_COMICS_LOADING_LIMIT
import io.reactivex.Single

class EventInfoViewModelImpl(
    private val eventsRepository : EventsRepository
) : AbstractDataLoadingViewModel(), EventInfoViewModel {
    
    
    private var event = Event()
    
    override val comicsItems = ObservableTrackableArrayList<Long, ComicsItem>()
    override val characterItems = ObservableTrackableArrayList<Long, CharacterItem>()
    
    
    override fun onStart() {
        super.onStart()
        
        loadInitialData()
    }
    
    
    override fun onRestoreState(bundle : Bundle) {
        super.onRestoreState(bundle)
        
        bundle.extract(stateExtractor).also {
            event = it.event
        }
    }
    
    
    override fun onSaveState(bundle : Bundle) {
        super.onSaveState(bundle)
        
        bundle.saveState(State(event = event))
    }
    
    
    override fun onComicsClicked(item : ComicsItem) {
        dispatchEvent(EventInfoViewModelEvents.OpenComicsInfoScreen(item.itemModel))
    }
    
    
    override fun onCharacterClicked(item : CharacterItem) {
        dispatchEvent(EventInfoViewModelEvents.OpenCharacterInfoScreen(item.itemModel))
    }
    
    
    override fun setEvent(event : Event) {
        this.event = event
    }
    
    
    override fun getEvent() : Event {
        return this.event
    }
    
    
    private fun loadInitialData() {
        if(characterItems.isEmpty()) {
            loadCharacters()
        }
    }
    
    
    private fun loadCharacters() {
        if(isLoading) {
            return
        }
        
        isLoading = true
        
        Single.zip(
            eventsRepository.getEventComics(
                event = event,
                offset = 0,
                limit = DEFAULT_EVENT_INFO_COMICS_LOADING_LIMIT
            ).resultOrError(),
            eventsRepository.getEventCharacters(
                event = event,
                offset = 0,
                limit = DEFAULT_EVENT_INFO_CHARACTER_LOADING_LIMIT
            ).resultOrError(),
            combineResults()
        )
        .typicalBackgroundWorkSchedulers()
        .subscribe(::onDataLoadedSuccessfully, ::onDataLoadingFailed)
        .manageLongLivingDisposable()
    }
    
    
    private fun onDataLoadedSuccessfully(data : Pair<List<Comics>, List<Character>>) {
        isLoading = false
        
        data.first.forEach { comicsItems.addOrUpdate(SmallComicsItem(it)) }
        data.second.forEach { characterItems.addOrUpdate(SmallCharacterItem(it)) }
    }
    
    
    private fun onDataLoadingFailed(throwable : Throwable) {
        isLoading = false
        
        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }
    
    
}