/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.work@gmail.com
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

package com.arthurivanets.sample.ui.comics.info

import android.os.Bundle
import com.arthurivanets.adapster.databinding.ObservableTrackableArrayList
import com.arthurivanets.commons.data.rx.ktx.resultOrError
import com.arthurivanets.commons.ktx.extract
import com.arthurivanets.commons.rx.ktx.applyIOWorkSchedulers
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.mvvm.AbstractViewModel
import com.arthurivanets.sample.adapters.characters.CharacterItem
import com.arthurivanets.sample.adapters.characters.SmallCharacterItem
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.repositories.comics.ComicsRepository
import com.arthurivanets.sample.ui.base.GeneralViewStates
import com.arthurivanets.sample.ui.base.MarvelRoutes
import com.arthurivanets.sample.ui.comics.DEFAULT_COMICS_INFO_CHARACTER_LOADING_LIMIT

class ComicsInfoViewModel(
    private val comicsRepository: ComicsRepository,
    private val schedulerProvider: SchedulerProvider
) : AbstractViewModel() {

    var comics = Comics()
    val characterItems = ObservableTrackableArrayList<Long, CharacterItem>()

    private var isDataLoading = false

    override fun onStart() {
        super.onStart()

        loadInitialData()
    }

    override fun onRestoreState(bundle: Bundle) {
        super.onRestoreState(bundle)

        bundle.extract(stateExtractor).also {
            comics = it.comics
        }
    }

    override fun onSaveState(bundle: Bundle) {
        super.onSaveState(bundle)

        bundle.saveState(State(comics = comics))
    }

    fun onCharacterClicked(character: Character) {
        route(MarvelRoutes.CharacterInfoScreen(character))
    }

    private fun loadInitialData() {
        if (characterItems.isEmpty()) {
            loadCharacters()
        }
    }

    private fun loadCharacters() {
        if (isDataLoading) {
            return
        }

        isDataLoading = true

        viewState = GeneralViewStates.Loading<Unit>()

        comicsRepository.getComicsCharacters(
            comics = comics,
            offset = 0,
            limit = DEFAULT_COMICS_INFO_CHARACTER_LOADING_LIMIT
        )
            .resultOrError()
            .applyIOWorkSchedulers(schedulerProvider)
            .subscribe(::onCharactersLoadedSuccessfully, ::onCharacterLoadingFailed)
            .manageLongLivingDisposable()
    }

    private fun onCharactersLoadedSuccessfully(characters: List<Character>) {
        isDataLoading = false

        viewState = GeneralViewStates.Success<Unit>()

        characters.forEach { characterItems.addOrUpdate(SmallCharacterItem(it)) }
    }

    private fun onCharacterLoadingFailed(throwable: Throwable) {
        isDataLoading = false

        viewState = GeneralViewStates.Error<Unit>()

        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }

}
