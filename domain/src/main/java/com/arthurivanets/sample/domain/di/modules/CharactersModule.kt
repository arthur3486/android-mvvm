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

package com.arthurivanets.sample.domain.di.modules

import android.content.Context
import com.arthurivanets.commons.network.NetworkStateProvider
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.sample.data.datastores.DataStoreFactory
import com.arthurivanets.sample.data.datastores.characters.CharactersDataStore
import com.arthurivanets.sample.data.datastores.characters.CharactersDataStoreFactory
import com.arthurivanets.sample.domain.di.qualifiers.Source
import com.arthurivanets.sample.domain.repositories.characters.CharactersRepository
import com.arthurivanets.sample.domain.repositories.characters.CharactersRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class CharactersModule {

    @Source(Source.Type.CACHE)
    @Provides
    @Singleton
    fun provideCacheDataStore(context: Context): CharactersDataStore {
        return CharactersDataStoreFactory(context).create(DataStoreFactory.Type.CACHE)
    }

    @Source(Source.Type.DATABASE)
    @Provides
    @Singleton
    fun provideDatabaseDataStore(context: Context): CharactersDataStore {
        return CharactersDataStoreFactory(context).create(DataStoreFactory.Type.DATABASE)
    }

    @Source(Source.Type.SERVER)
    @Provides
    @Singleton
    fun provideServerDataStore(context: Context): CharactersDataStore {
        return CharactersDataStoreFactory(context).create(DataStoreFactory.Type.SERVER)
    }

    @Provides
    @Singleton
    fun provideRepository(
        @Source(Source.Type.CACHE) cacheDataStore: CharactersDataStore,
        @Source(Source.Type.DATABASE) databaseDataStore: CharactersDataStore,
        @Source(Source.Type.SERVER) serverDataStore: CharactersDataStore,
        networkStateProvider: NetworkStateProvider,
        schedulerProvider: SchedulerProvider
    ): CharactersRepository {
        return CharactersRepositoryImpl(
            cacheDataStore = cacheDataStore,
            databaseDataStore = databaseDataStore,
            serverDataStore = serverDataStore,
            networkStateProvider = networkStateProvider,
            schedulerProvider = schedulerProvider
        )
    }

}
