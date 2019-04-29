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

import androidx.lifecycle.ViewModelProviders
import com.arthurivanets.mvvm.ViewModelProviderFactory
import com.arthurivanets.sample.domain.repositories.characters.CharactersRepository
import com.arthurivanets.sample.ui.characters.info.CharacterInfoFragment
import com.arthurivanets.sample.ui.characters.info.CharacterInfoViewModel
import com.arthurivanets.sample.ui.characters.info.CharacterInfoViewModelImpl
import com.arthurivanets.sample.ui.characters.list.CharactersFragment
import com.arthurivanets.sample.ui.characters.list.CharactersViewModel
import com.arthurivanets.sample.ui.characters.list.CharactersViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class CharactersModule {


    @Provides
    fun provideCharactersViewModel(fragment : CharactersFragment,
                                   charactersRepository : CharactersRepository) : CharactersViewModel {
        val viewModelFactory = ViewModelProviderFactory(CharactersViewModelImpl(charactersRepository))
        return ViewModelProviders.of(fragment, viewModelFactory).get(CharactersViewModelImpl::class.java)
    }


    @Provides
    fun provideCharacterInfoViewModel(fragment : CharacterInfoFragment,
                                      charactersRepository : CharactersRepository) : CharacterInfoViewModel {
        val viewModelFactory = ViewModelProviderFactory(CharacterInfoViewModelImpl(charactersRepository))
        return ViewModelProviders.of(fragment, viewModelFactory).get(CharacterInfoViewModelImpl::class.java)
    }


}