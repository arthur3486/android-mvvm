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
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.adapters.comics.SmallComicsItem
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.repositories.characters.CharactersRepository
import com.arthurivanets.sample.ui.base.AbstractDataLoadingViewModel
import com.arthurivanets.sample.ui.characters.DEFAULT_CHARACTER_INFO_COMICS_LOADING_LIMIT

class CharacterInfoViewModelImpl(
    private val charactersRepository : CharactersRepository
) : AbstractDataLoadingViewModel(), CharacterInfoViewModel {
    
    
    private var character = Character()
    
    override val comicsItems = ObservableTrackableArrayList<Long, ComicsItem>()
    
    
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
    
    
    override fun onComicsClicked(item : ComicsItem) {
        dispatchEvent(CharacterInfoViewModelEvents.OpenComicsInfoScreen(item.itemModel))
    }
    
    
    override fun setCharacter(character : Character) {
        this.character = character
    }
    
    
    override fun getCharacter() : Character {
        return this.character
    }
    
    
    private fun loadInitialData() {
        if(comicsItems.isEmpty()) {
            loadCharacters()
        }
    }
    
    
    private fun loadCharacters() {
        if(isLoading) {
            return
        }
        
        isLoading = true
        
        charactersRepository.getCharacterComics(
            character = character,
            offset = 0,
            limit = DEFAULT_CHARACTER_INFO_COMICS_LOADING_LIMIT
        )
        .resultOrError()
        .typicalBackgroundWorkSchedulers()
        .subscribe(::onComicsLoadedSuccessfully, ::onComicsLoadingFailed)
        .manageLongLivingDisposable()
    }
    
    
    private fun onComicsLoadedSuccessfully(comics : List<Comics>) {
        isLoading = false
        
        comics.forEach { comicsItems.addOrUpdate(SmallComicsItem(it)) }
    }
    
    
    private fun onComicsLoadingFailed(throwable : Throwable) {
        isLoading = false
        
        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }
    
    
}