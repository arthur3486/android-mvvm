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

package com.arthurivanets.sample.domain.di.modules

import android.content.Context
import com.arthurivanets.commons.network.NetworkStateProvider
import com.arthurivanets.sample.data.datastores.DataStoreFactory
import com.arthurivanets.sample.data.datastores.events.EventsDataStore
import com.arthurivanets.sample.data.datastores.events.EventsDataStoreFactory
import com.arthurivanets.sample.domain.di.qualifiers.Source
import com.arthurivanets.sample.domain.repositories.events.EventsRepository
import com.arthurivanets.sample.domain.repositories.events.EventsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EventsModule {


    @Source(Source.Type.CACHE)
    @Provides
    @Singleton
    fun provideCacheDataStore(context : Context) : EventsDataStore {
        return EventsDataStoreFactory(context).create(DataStoreFactory.Type.CACHE)
    }


    @Source(Source.Type.DATABASE)
    @Provides
    @Singleton
    fun provideDatabaseDataStore(context : Context) : EventsDataStore {
        return EventsDataStoreFactory(context).create(DataStoreFactory.Type.DATABASE)
    }


    @Source(Source.Type.SERVER)
    @Provides
    @Singleton
    fun provideServerDataStore(context : Context) : EventsDataStore {
        return EventsDataStoreFactory(context).create(DataStoreFactory.Type.SERVER)
    }


    @Provides
    @Singleton
    fun provideRepository(@Source(Source.Type.CACHE) cacheDataStore : EventsDataStore,
                          @Source(Source.Type.DATABASE) databaseDataStore : EventsDataStore,
                          @Source(Source.Type.SERVER) serverDataStore : EventsDataStore,
                          networkStateProvider : NetworkStateProvider) : EventsRepository {
        return EventsRepositoryImpl(
            cacheDataStore = cacheDataStore,
            databaseDataStore = databaseDataStore,
            serverDataStore = serverDataStore,
            networkStateProvider = networkStateProvider
        )
    }


}