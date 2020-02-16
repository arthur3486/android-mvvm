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
import com.arthurivanets.commons.data.util.asResult
import com.arthurivanets.sample.data.datastores.base.AbstractDataStore
import com.arthurivanets.sample.data.datastores.util.take
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single
import java.util.concurrent.ConcurrentHashMap

/**
 *
 */
internal class EventsCacheDataStore(context : Context) : AbstractDataStore(context), EventsDataStore {


    private val eventsCache = ConcurrentHashMap<Long, DataEvent>()
    private val charactersCache = ConcurrentHashMap<Long, MutableMap<Long, DataCharacter>>()
    private val comicsCache = ConcurrentHashMap<Long, MutableMap<Long, DataComics>>()


    override fun saveEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { saveEventInternal(event) }
    }


    private fun saveEventInternal(event : DataEvent) : Response<DataEvent, Throwable> {
        eventsCache[event.id] = event
        return Response.result(event)
    }


    override fun saveEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { saveEventsInternal(events) }
    }


    private fun saveEventsInternal(events : List<DataEvent>) : Response<List<DataEvent>, Throwable> {
        events.forEach { eventsCache[it.id] = it }
        return Response.result(events)
    }


    override fun updateEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return saveEvent(event)
    }


    override fun updateEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return saveEvents(events)
    }


    override fun deleteEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { deleteEventInternal(event) }
    }


    private fun deleteEventInternal(event : DataEvent) : Response<DataEvent, Throwable> {
        return Response.result(eventsCache.remove(event.id))
    }


    override fun deleteEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { deleteEventsInternal(events) }
    }


    private fun deleteEventsInternal(events : List<DataEvent>) : Response<List<DataEvent>, Throwable> {
        val deletedEvents = ArrayList<DataEvent>()

        for(event in events) {
            eventsCache.remove(event.id)?.let { deletedEvents.add(it) }
        }

        return Response.result(deletedEvents)
    }


    override fun getEvent(id : Long) : Single<Response<DataEvent, Throwable>> {
        return Single.fromCallable { getEventInternal(id) }
    }


    private fun getEventInternal(id : Long) : Response<DataEvent, Throwable> {
        return Response.result(eventsCache[id])
    }


    override fun getEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { getEventsInternal(offset, limit) }
    }


    private fun getEventsInternal(offset : Int, limit : Int) : Response<List<DataEvent>, Throwable> {
        return eventsCache.values
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }


    override fun saveEventCharacters(eventId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { saveEventCharactersInternal(eventId, characters) }
    }


    private fun saveEventCharactersInternal(eventId : Long, characters : List<DataCharacter>) : Response<List<DataCharacter>, Throwable> {
        val cachedCharacters = (charactersCache[eventId] ?: ConcurrentHashMap())

        characters.forEach { cachedCharacters[it.id] = it }

        charactersCache[eventId] = cachedCharacters

        return Response.result(characters)
    }


    override fun getEventCharacters(
        eventId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable {
            getEventCharactersInternal(
                eventId = eventId,
                offset = offset,
                limit = limit
            )
        }
    }


    private fun getEventCharactersInternal(
        eventId : Long,
        offset : Int,
        limit : Int
    ) : Response<List<DataCharacter>, Throwable> {
        return (charactersCache[eventId]?.values ?: emptyList<DataCharacter>())
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }
    
    
    override fun saveEventComics(eventId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { saveEventComicsInternal(eventId, comics) }
    }
    
    
    private fun saveEventComicsInternal(eventId : Long, comics : List<DataComics>) : Response<List<DataComics>, Throwable> {
        val cachedComics = (comicsCache[eventId] ?: ConcurrentHashMap())
    
        comics.forEach { cachedComics[it.id] = it }
    
        comicsCache[eventId] = cachedComics
    
        return Response.result(comics)
    }
    
    
    override fun getEventComics(
        eventId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable {
            getEventComicsInternal(
                eventId = eventId,
                offset = offset,
                limit = limit
            )
        }
    }
    
    
    private fun getEventComicsInternal(
        eventId : Long,
        offset : Int,
        limit : Int
    ) : Response<List<DataComics>, Throwable> {
        return (comicsCache[eventId]?.values ?: emptyList<DataComics>())
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }
    
    
}