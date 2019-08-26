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

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.datastores.base.DataStore
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single

interface EventsDataStore : DataStore {

    fun saveEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>>

    fun saveEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>>

    fun updateEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>>

    fun updateEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>>

    fun deleteEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>>

    fun deleteEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>>

    fun getEvent(id : Long) : Single<Response<DataEvent, Throwable>>

    fun getEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>>

    fun saveEventCharacters(eventId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>>

    fun getEventCharacters(eventId : Long,
                           offset : Int,
                           limit : Int) : Single<Response<List<DataCharacter>, Throwable>>
    
    fun saveEventComics(eventId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>>
    
    fun getEventComics(eventId : Long,
                       offset : Int,
                       limit : Int) : Single<Response<List<DataComics>, Throwable>>

}