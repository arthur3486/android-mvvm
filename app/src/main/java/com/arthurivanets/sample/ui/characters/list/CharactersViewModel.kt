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

package com.arthurivanets.sample.ui.characters.list

import com.arthurivanets.adapster.databinding.ObservableTrackableArrayList
import com.arthurivanets.commons.data.rx.ktx.resultOrError
import com.arthurivanets.commons.rx.ktx.applyIOWorkSchedulers
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.mvvm.AbstractViewModel
import com.arthurivanets.sample.adapters.characters.CharacterItem
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.repositories.characters.CharactersRepository
import com.arthurivanets.sample.ui.base.GeneralViewStates
import com.arthurivanets.sample.ui.base.MarvelRoutes
import com.arthurivanets.sample.ui.characters.DEFAULT_CHARACTER_LOADING_LIMIT

class CharactersViewModel(
    private val charactersRepository: CharactersRepository,
    private val schedulerProvider: SchedulerProvider
) : AbstractViewModel() {

    val items = ObservableTrackableArrayList<Long, CharacterItem>()

    private var isDataLoading = false

    override fun onStart() {
        super.onStart()

        loadInitialData()
    }

    fun onCharacterClicked(character: Character) {
        route(MarvelRoutes.CharacterInfoScreen(character))
    }

    private fun loadInitialData() {
        if (items.isEmpty()) {
            loadData()
        }
    }

    private fun loadData() {
        if (isDataLoading) {
            return
        }

        isDataLoading = true

        viewState = GeneralViewStates.Loading<Unit>()

        charactersRepository.getCharacters(0, DEFAULT_CHARACTER_LOADING_LIMIT)
            .resultOrError()
            .applyIOWorkSchedulers(schedulerProvider)
            .subscribe(::onLoadedSuccessfully, ::onLoadingFailed)
            .manageLongLivingDisposable()
    }

    private fun onLoadedSuccessfully(characters: List<Character>) {
        isDataLoading = false

        viewState = GeneralViewStates.Success<Unit>()

        characters.forEach { items.addOrUpdate(CharacterItem(it)) }
    }

    private fun onLoadingFailed(throwable: Throwable) {
        isDataLoading = false

        viewState = GeneralViewStates.Error<Unit>()

        // TODO the proper error handling should be done here
        throwable.printStackTrace()
    }

}
