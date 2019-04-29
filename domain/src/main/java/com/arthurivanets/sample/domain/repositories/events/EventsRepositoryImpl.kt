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

import com.arthurivanets.commons.data.eventbus.RepositoryEventBus
import com.arthurivanets.commons.data.exceptions.NoResultError
import com.arthurivanets.commons.data.network.ktx.ifNetworkAvailable
import com.arthurivanets.commons.data.repository.AbstractRepository
import com.arthurivanets.commons.data.rx.ktx.flatMapOrError
import com.arthurivanets.commons.data.rx.ktx.successfulResponseOrError
import com.arthurivanets.commons.data.rx.ktx.withNonEmptyResult
import com.arthurivanets.commons.data.rx.ktx.withResult
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.data.util.asError
import com.arthurivanets.commons.network.NetworkStateProvider
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.data.datastores.events.EventsDataStore
import com.arthurivanets.sample.domain.repositories.characters.toDomainCharactersResponse
import com.arthurivanets.sample.domain.repositories.comics.toDomainComicsResponse
import com.arthurivanets.sample.domain.repositories.util.StubEventBus
import com.arthurivanets.sample.domain.repositories.util.convertResponse
import com.arthurivanets.sample.domain.util.*
import io.reactivex.Flowable
import io.reactivex.Single

internal class EventsRepositoryImpl(
    val cacheDataStore : EventsDataStore,
    val databaseDataStore : EventsDataStore,
    val serverDataStore : EventsDataStore,
    val networkStateProvider : NetworkStateProvider
) : AbstractRepository<RepositoryEventBus>(StubEventBus()), EventsRepository {


    override fun getEvent(id : Long) : Single<Response<DomainEvent, Throwable>> {
        return Single.concat(
            getCacheEvent(id),
            getDatabaseEvent(id),
            getServerEvent(id)
        )
        .withResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheEvent(id : Long) : Single<Response<DomainEvent, Throwable>> {
        return this.cacheDataStore.getEvent(id)
            .convertResponse { it.toDomainEventResponse() }
    }


    private fun getDatabaseEvent(id : Long) : Single<Response<DomainEvent, Throwable>> {
        return this.databaseDataStore.getEvent(id)
            .cacheEventResponse()
            .convertResponse { it.toDomainEventResponse() }
            .successfulResponseOrError()
    }


    private fun getServerEvent(id : Long) : Single<Response<DomainEvent, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEvent(id).flatMapOrError { event ->
                this.databaseDataStore.saveEvent(event).successfulResponseOrError()
            }
            .cacheEventResponse()
            .convertResponse { it.toDomainEventResponse() }
        )
    }


    override fun getEvents(offset : Int, limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return Single.concat(
            getCacheEvents(offset, limit),
            getDatabaseEvents(offset, limit),
            getServerEvents(offset, limit)
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheEvents(offset : Int, limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return this.cacheDataStore.getEvents(offset, limit)
            .convertResponse { it.toDomainEventsResponse() }
    }


    private fun getDatabaseEvents(offset : Int, limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return this.databaseDataStore.getEvents(offset, limit)
            .cacheEventsResponse()
            .convertResponse { it.toDomainEventsResponse() }
            .successfulResponseOrError()
    }


    private fun getServerEvents(offset : Int, limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEvents(offset, limit).flatMapOrError { events ->
                this.databaseDataStore.saveEvents(events).successfulResponseOrError()
            }
            .cacheEventsResponse()
            .convertResponse { it.toDomainEventsResponse(sortItems = true) }
        )
    }


    override fun getAllEvents(offset : Int, limit : Int) : Flowable<Response<List<DomainEvent>, Throwable>> {
        return Flowable.concat(
            getCacheEvents(offset, limit).toFlowable(),
            getDatabaseEvents(offset, limit).toFlowable(),
            getServerEvents(offset, limit).toFlowable()
        )
        .withResult()
        .typicalBackgroundWorkSchedulers()
    }


    override fun getEventCharacters(event : DomainEvent,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return Single.concat(
            getCacheEventCharacters(
                event = event,
                offset = offset,
                limit = limit
            ),
            getServerEventCharacters(
                event = event,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheEventCharacters(event : DomainEvent,
                                        offset : Int,
                                        limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.cacheDataStore.getEventCharacters(
            eventId = event.id,
            offset = offset,
            limit = limit
        )
        .convertResponse { it.toDomainCharactersResponse() }
    }


    private fun getServerEventCharacters(event : DomainEvent,
                                         offset : Int,
                                         limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEventCharacters(
                eventId = event.id,
                offset = offset,
                limit = limit
            )
            .cacheEventCharacters(event)
            .convertResponse { it.toDomainCharactersResponse(sortItems = true) }
            .successfulResponseOrError()
        )
    }
    
    
    override fun getEventComics(event : DomainEvent,
                                offset : Int,
                                limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return Single.concat(
            getCacheEventComics(
                event = event,
                offset = offset,
                limit = limit
            ),
            getServerEventComics(
                event = event,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }
    
    
    private fun getCacheEventComics(event : DomainEvent,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.cacheDataStore.getEventComics(
            eventId = event.id,
            offset = offset,
            limit = limit
        )
        .convertResponse { it.toDomainComicsResponse() }
    }
    
    
    private fun getServerEventComics(event : DomainEvent,
                                     offset : Int,
                                     limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getEventComics(
                eventId = event.id,
                offset = offset,
                limit = limit
            )
            .cacheEventComics(event)
            .convertResponse { it.toDomainComicsResponse(sortItems = true) }
            .successfulResponseOrError()
        )
    }
    
    
    private fun Single<Response<DataEvent, Throwable>>.cacheEventResponse() : Single<Response<DataEvent, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveEvent(it) }
    }


    private fun Single<Response<List<DataEvent>, Throwable>>.cacheEventsResponse() : Single<Response<List<DataEvent>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveEvents(it) }
    }


    private fun Single<Response<List<DataCharacter>, Throwable>>.cacheEventCharacters(event : DomainEvent) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveEventCharacters(event.id, it) }
    }
    
    
    private fun Single<Response<List<DataComics>, Throwable>>.cacheEventComics(event : DomainEvent) : Single<Response<List<DataComics>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveEventComics(event.id, it) }
    }


}