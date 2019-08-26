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

package com.arthurivanets.sample.domain.repositories.events

import com.arthurivanets.commons.data.exceptions.NoResultError
import com.arthurivanets.commons.data.network.ktx.ifNetworkAvailable
import com.arthurivanets.commons.data.rx.ktx.flatMapOrError
import com.arthurivanets.commons.data.rx.ktx.successfulResponseOrError
import com.arthurivanets.commons.data.rx.ktx.withNonEmptyResult
import com.arthurivanets.commons.data.rx.ktx.withResult
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.network.NetworkStateProvider
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.rxbus.BusEvent
import com.arthurivanets.sample.data.datastores.events.EventsDataStore
import com.arthurivanets.sample.domain.repositories.base.AbstractRepository
import com.arthurivanets.sample.domain.repositories.characters.toDomainCharactersResponse
import com.arthurivanets.sample.domain.repositories.comics.toDomainComicsResponse
import com.arthurivanets.sample.domain.repositories.util.convertResponse
import com.arthurivanets.sample.domain.util.*
import io.reactivex.Flowable
import io.reactivex.Single

internal class EventsRepositoryImpl(
    private val cacheDataStore : EventsDataStore,
    private val databaseDataStore : EventsDataStore,
    private val serverDataStore : EventsDataStore,
    private val networkStateProvider : NetworkStateProvider,
    schedulerProvider : SchedulerProvider
) : AbstractRepository<BusEvent<*>>(schedulerProvider), EventsRepository {


    override fun getEvent(id : Long) : Single<Response<DomainEvent, Throwable>> {
        return Single.concat(
            getCacheEvent(id),
            getDatabaseEvent(id),
            getServerEvent(id)
        )
        .withResult()
        .first(Response.error(NoResultError("No Result found.")))
        .flatMapOrError(::createCacheEvent)
        .convertResponse { it.toDomainEventResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheEvent(id : Long) : Single<Response<DataEvent, Throwable>> {
        return this.cacheDataStore.getEvent(id)
    }


    private fun getDatabaseEvent(id : Long) : Single<Response<DataEvent, Throwable>> {
        return this.databaseDataStore.getEvent(id)
            .successfulResponseOrError()
    }


    private fun getServerEvent(id : Long) : Single<Response<DataEvent, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEvent(id)
                .flatMapOrError(::createDatabaseEvent)
        ).successfulResponseOrError()
    }


    override fun getEvents(offset : Int, limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return Single.concat(
            getCacheEvents(offset, limit),
            getDatabaseEvents(offset, limit),
            getServerEvents(offset, limit)
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError(::createCacheEvents)
        .convertResponse { it.toDomainEventsResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return this.cacheDataStore.getEvents(offset, limit)
    }


    private fun getDatabaseEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return this.databaseDataStore.getEvents(offset, limit)
            .successfulResponseOrError()
    }


    private fun getServerEvents(offset : Int, limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEvents(offset, limit)
                .flatMapOrError(::createDatabaseEvents)
        ).successfulResponseOrError()
    }


    override fun getAllEvents(offset : Int, limit : Int) : Flowable<Response<List<DomainEvent>, Throwable>> {
        return Flowable.concat(
            getCacheEvents(offset, limit).toFlowable(),
            getDatabaseEvents(offset, limit).toFlowable(),
            getServerEvents(offset, limit).toFlowable()
        )
        .withResult()
        .flatMapOrError { createCacheEvents(it).toFlowable() }
        .convertResponse { it.toDomainEventsResponse() }
        .withAppropriateSchedulers()
    }


    override fun getEventCharacters(event : DomainEvent,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return Single.concat(
            getCacheEventCharacters(
                eventId = event.id,
                offset = offset,
                limit = limit
            ),
            getServerEventCharacters(
                eventId = event.id,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError { createCacheCharacters(event.id, it) }
        .convertResponse { it.toDomainCharactersResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheEventCharacters(eventId : Long,
                                        offset : Int,
                                        limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.cacheDataStore.getEventCharacters(
            eventId = eventId,
            offset = offset,
            limit = limit
        )
    }


    private fun getServerEventCharacters(eventId : Long,
                                         offset : Int,
                                         limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEventCharacters(
                eventId = eventId,
                offset = offset,
                limit = limit
            )
        ).successfulResponseOrError()
    }
    
    
    override fun getEventComics(event : DomainEvent,
                                offset : Int,
                                limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return Single.concat(
            getCacheEventComics(
                eventId = event.id,
                offset = offset,
                limit = limit
            ),
            getServerEventComics(
                eventId = event.id,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError { createCacheComics(event.id, it) }
        .convertResponse { it.toDomainComicsResponse() }
        .withAppropriateSchedulers()
    }
    
    
    private fun getCacheEventComics(eventId : Long,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return this.cacheDataStore.getEventComics(
            eventId = eventId,
            offset = offset,
            limit = limit
        )
    }
    
    
    private fun getServerEventComics(eventId : Long,
                                     offset : Int,
                                     limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEventComics(
                eventId = eventId,
                offset = offset,
                limit = limit
            )
        ).successfulResponseOrError()
    }
    
    
    private fun createDatabaseEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return this.databaseDataStore.saveEvent(event)
            .successfulResponseOrError()
    }
    
    
    private fun createDatabaseEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return this.databaseDataStore.saveEvents(events)
            .successfulResponseOrError()
    }
    
    
    private fun createCacheEvent(event : DataEvent) : Single<Response<DataEvent, Throwable>> {
        return this.cacheDataStore.saveEvent(event)
    }
    
    
    private fun createCacheEvents(events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return this.cacheDataStore.saveEvents(events)
    }
    
    
    private fun createCacheCharacters(eventId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.cacheDataStore.saveEventCharacters(eventId, characters)
    }
    
    
    private fun createCacheComics(eventId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return this.cacheDataStore.saveEventComics(eventId, comics)
    }


}