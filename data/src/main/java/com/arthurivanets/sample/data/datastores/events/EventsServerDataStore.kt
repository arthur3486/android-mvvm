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
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.rx.ktx.applyIOWorkSchedulers
import com.arthurivanets.commons.rx.ktx.asSingle
import com.arthurivanets.marvelapi.MarvelApi
import com.arthurivanets.sample.data.datastores.base.AbstractDataStore
import com.arthurivanets.sample.data.datastores.characters.toResponse
import com.arthurivanets.sample.data.datastores.comics.toResponse
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single

internal class EventsServerDataStore(context : Context) : AbstractDataStore(context), EventsDataStore {


    override fun saveEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        throw UnsupportedOperationException("Event creation on the Server Side is unsupported.")
    }


    override fun saveEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        throw UnsupportedOperationException("Event creation on the Server Side is unsupported.")
    }


    override fun updateEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        throw UnsupportedOperationException("Event modification on the Server Side is unsupported.")
    }


    override fun updateEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        throw UnsupportedOperationException("Event modification on the Server Side is unsupported.")
    }


    override fun deleteEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        throw UnsupportedOperationException("Event deletion on the Server Side is unsupported.")
    }


    override fun deleteEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        throw UnsupportedOperationException("Event deletion on the Server Side is unsupported.")
    }


    override fun getEvent(id : Long) : Single<Response<DataEvent, Throwable>> {
        return MarvelApi.INSTANCE.events.getEvent(id)
            .flatMap { it.toSingleItemResponse().asSingle() }
            .applyIOWorkSchedulers()
    }


    override fun getEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return MarvelApi.INSTANCE.events.getEvents(
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .applyIOWorkSchedulers()
    }


    override fun saveEventCharacters(eventId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Event Characters cannot be saved on the side of the Server.")
    }


    override fun getEventCharacters(
        eventId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataCharacter>, Throwable>> {
        return MarvelApi.INSTANCE.events.getEventCharacters(
            eventId = eventId,
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .applyIOWorkSchedulers()
    }
    
    
    override fun saveEventComics(eventId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Event Comics cannot be saved on the side of the Server.")
    }
    
    
    override fun getEventComics(
        eventId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataComics>, Throwable>> {
        return MarvelApi.INSTANCE.events.getEventComics(
            eventId = eventId,
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .applyIOWorkSchedulers()
    }
    
    
}