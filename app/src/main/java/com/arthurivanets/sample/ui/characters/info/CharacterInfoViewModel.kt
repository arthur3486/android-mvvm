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

package com.arthurivanets.sample.ui.characters.info

import android.os.Bundle
import com.arthurivanets.adapster.databinding.ObservableTrackableArrayList
import com.arthurivanets.commons.data.rx.ktx.resultOrError
import com.arthurivanets.commons.ktx.extract
import com.arthurivanets.commons.rx.ktx.applyIOWorkSchedulers
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.mvvm.AbstractViewModel
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.adapters.comics.SmallComicsItem
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.repositories.characters.CharactersRepository
import com.arthurivanets.sample.ui.base.GeneralViewStates
import com.arthurivanets.sample.ui.base.MarvelRoutes
import com.arthurivanets.sample.ui.characters.DEFAULT_CHARACTER_INFO_COMICS_LOADING_LIMIT

class CharacterInfoViewModel(
    private val charactersRepository : CharactersRepository,
    private val schedulerProvider : SchedulerProvider
) : AbstractViewModel() {
    
    
    var character = Character()
    val comicsItems = ObservableTrackableArrayList<Long, ComicsItem>()
    
    private var isDataLoading = false
    
    
    override fun onStart() {
        super.onStart()
        
        loadInitialData()
    }
    
    
    override fun onRestoreState(bundle : Bundle) {
        super.onRestoreState(bundle)
        
        bundle.extract(stateExtractor).also {
            character = it.character
        }
    }
    
    
    override fun onSaveState(bundle : Bundle) {
        super.onSaveState(bundle)
        
        bundle.saveState(State(character = character))
    }
    
    
    fun onComicsClicked(comics : Comics) {
        route(MarvelRoutes.ComicsInfoScreen(comics))
    }
    
    
    private fun loadInitialData() {
        if(comicsItems.isEmpty()) {
            loadCharacters()
        }
    }
    
    
    private fun loadCharacters() {
        if(isDataLoading) {
            return
        }
    
        isDataLoading = true
        
        changeViewState(GeneralViewStates.Loading<Unit>())
        
        charactersRepository.getCharacterComics(
            character = character,
            offset = 0,
            limit = DEFAULT_CHARACTER_INFO_COMICS_LOADING_LIMIT
        )
        .resultOrError()
        .applyIOWorkSchedulers(schedulerProvider)
        .subscribe(::onComicsLoadedSuccessfully, ::onComicsLoadingFailed)
        .manageLongLivingDisposable()
    }
    
    
    private fun onComicsLoadedSuccessfully(comics : List<Comics>) {
        isDataLoading = false
        
        changeViewState(GeneralViewStates.Success<Unit>())
        
        comics.forEach { comicsItems.addOrUpdate(SmallComicsItem(it)) }
    }
    
    
    private fun onComicsLoadingFailed(throwable : Throwable) {
        isDataLoading = false
        
        changeViewState(GeneralViewStates.Error<Unit>())
        
        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }
    
    
}