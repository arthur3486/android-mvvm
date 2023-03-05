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

package com.arthurivanets.sample.data.datastores.events

import android.content.Context
import com.arthurivanets.sample.data.datastores.AbstractDataStoreFactory
import com.arthurivanets.sample.data.datastores.DataStoreFactory

class EventsDataStoreFactory(context: Context) : AbstractDataStoreFactory<EventsDataStore>(context) {

    override fun create(type: DataStoreFactory.Type): EventsDataStore {
        return when (type) {
            DataStoreFactory.Type.CACHE -> EventsCacheDataStore(internalContext)
            DataStoreFactory.Type.DATABASE -> EventsDatabaseDataStore(internalContext)
            DataStoreFactory.Type.SERVER -> EventsServerDataStore(internalContext)
        }
    }

}
