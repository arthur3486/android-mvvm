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
import com.arthurivanets.commons.data.datastore.AbstractDataStore
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.data.util.resultOrError
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.data.db.Database
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single

/**
 *
 */
internal class EventsDatabaseDataStore(context : Context) : AbstractDataStore(context), EventsDataStore {


    override fun saveEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { saveEventInternal(event) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun saveEventInternal(event : DataEvent) : Response<DataEvent, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .save(event.toDatabaseEvent())

        return@resultOrError event
    }


    override fun saveEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { saveEventsInternal(events) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun saveEventsInternal(events : List<DataEvent>) : Response<List<DataEvent>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .save(events.toDatabaseEvents())

        return@resultOrError events
    }


    override fun updateEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { updateEventInternal(event) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun updateEventInternal(event : DataEvent) : Response<DataEvent, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .update(event.toDatabaseEvent())

        return@resultOrError event
    }


    override fun updateEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { updateEventsInternal(events) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun updateEventsInternal(events : List<DataEvent>) : Response<List<DataEvent>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .update(events.toDatabaseEvents())

        return@resultOrError events
    }


    override fun deleteEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { deleteEventInternal(event) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun deleteEventInternal(event : DataEvent) : Response<DataEvent, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .delete(event.toDatabaseEvent())

        return@resultOrError event
    }


    override fun deleteEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { deleteEventsInternal(events) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun deleteEventsInternal(events : List<DataEvent>) : Response<List<DataEvent>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .delete(events.toDatabaseEvents())

        return@resultOrError events
    }


    override fun getEvent(id : Long) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { getEventInternal(id) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun getEventInternal(id : Long) : Response<DataEvent, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .getById(id)
            ?.toDataEvent()
    }


    override fun getEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { getEventsInternal(offset, limit) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun getEventsInternal(offset : Int, limit : Int) : Response<List<DataEvent>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .eventsTable()
            .get(offset = offset, limit = limit)
            .fromDatabaseToDataEvents()
    }


    override fun saveEventCharacters(eventId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Event Characters cannot be saved into the Database.")
    }


    override fun getEventCharacters(eventId : Long,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Event Characters cannot be fetched from the Database.")
    }
    
    
    override fun saveEventComics(eventId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Event Comics cannot be saved into the Database.")
    }
    
    
    override fun getEventComics(eventId : Long,
                                offset : Int,
                                limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Event Comics cannot be fetched from the Database.")
    }
    
    
}