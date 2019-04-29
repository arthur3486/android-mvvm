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

package com.arthurivanets.sample.di.modules

import com.arthurivanets.sample.di.modules.fragments.CharactersModule
import com.arthurivanets.sample.di.modules.fragments.ComicsModule
import com.arthurivanets.sample.di.modules.fragments.DashboardModule
import com.arthurivanets.sample.di.modules.fragments.EventsModule
import com.arthurivanets.sample.ui.characters.info.CharacterInfoFragment
import com.arthurivanets.sample.ui.characters.list.CharactersFragment
import com.arthurivanets.sample.ui.comics.info.ComicsInfoFragment
import com.arthurivanets.sample.ui.comics.list.ComicsFragment
import com.arthurivanets.sample.ui.dashboard.DashboardFragment
import com.arthurivanets.sample.ui.events.info.EventInfoFragment
import com.arthurivanets.sample.ui.events.list.EventsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {


    @ContributesAndroidInjector(modules = [DashboardModule::class])
    abstract fun buildDashboardFragment() : DashboardFragment


    @ContributesAndroidInjector(modules = [ComicsModule::class])
    abstract fun buildComicsFragment() : ComicsFragment
    
    
    @ContributesAndroidInjector(modules = [ComicsModule::class])
    abstract fun buildComicsInfoFragment() : ComicsInfoFragment


    @ContributesAndroidInjector(modules = [EventsModule::class])
    abstract fun buildEventsFragment() : EventsFragment
    
    
    @ContributesAndroidInjector(modules = [EventsModule::class])
    abstract fun buildEventInfoFragment() : EventInfoFragment


    @ContributesAndroidInjector(modules = [CharactersModule::class])
    abstract fun buildCharactersFragment() : CharactersFragment
    
    
    @ContributesAndroidInjector(modules = [CharactersModule::class])
    abstract fun buildCharacterInfoFragment() : CharacterInfoFragment


}