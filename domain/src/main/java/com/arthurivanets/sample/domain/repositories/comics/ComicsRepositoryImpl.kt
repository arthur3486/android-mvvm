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

package com.arthurivanets.sample.domain.repositories.comics

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
import com.arthurivanets.sample.data.datastores.comics.ComicsDataStore
import com.arthurivanets.sample.domain.repositories.characters.toDomainCharactersResponse
import com.arthurivanets.sample.domain.repositories.events.toDomainEventsResponse
import com.arthurivanets.sample.domain.repositories.util.StubEventBus
import com.arthurivanets.sample.domain.repositories.util.convertResponse
import com.arthurivanets.sample.domain.util.*
import io.reactivex.Flowable
import io.reactivex.Single

internal class ComicsRepositoryImpl(
    val cacheDataStore : ComicsDataStore,
    val databaseDataStore : ComicsDataStore,
    val serverDataStore : ComicsDataStore,
    val networkStateProvider : NetworkStateProvider
) : AbstractRepository<RepositoryEventBus>(StubEventBus()), ComicsRepository {


    override fun getSingleComics(id : Long) : Single<Response<DomainComics, Throwable>> {
        return Single.concat(
            getCacheSingleComics(id),
            getDatabaseSingleComics(id),
            getServerSingleComics(id)
        )
        .withResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheSingleComics(id : Long) : Single<Response<DomainComics, Throwable>> {
        return this.cacheDataStore.getSingleComics(id)
            .convertResponse { it.toDomainSingleComicsResponse() }
    }


    private fun getDatabaseSingleComics(id : Long) : Single<Response<DomainComics, Throwable>> {
        return this.databaseDataStore.getSingleComics(id)
            .cacheSingleComicsResponse()
            .convertResponse { it.toDomainSingleComicsResponse() }
            .successfulResponseOrError()
    }


    private fun getServerSingleComics(id : Long) : Single<Response<DomainComics, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getSingleComics(id).flatMapOrError { comics ->
                this.databaseDataStore.saveComics(comics).successfulResponseOrError()
            }
            .cacheSingleComicsResponse()
            .convertResponse { it.toDomainSingleComicsResponse() }
        )
    }


    override fun getComics(offset : Int, limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return Single.concat(
            getCacheComics(offset, limit),
            getDatabaseComics(offset, limit),
            getServerComics(offset, limit)
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheComics(offset : Int, limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.cacheDataStore.getComics(offset, limit)
            .convertResponse { it.toDomainComicsResponse() }
    }


    private fun getDatabaseComics(offset : Int, limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.databaseDataStore.getComics(offset, limit)
            .cacheComicsResponse()
            .convertResponse { it.toDomainComicsResponse() }
            .successfulResponseOrError()
    }


    private fun getServerComics(offset : Int, limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getComics(offset, limit).flatMapOrError { comics ->
                this.databaseDataStore.saveComics(comics).successfulResponseOrError()
            }
            .cacheComicsResponse()
            .convertResponse { it.toDomainComicsResponse(sortItems = true) }
        )
    }


    override fun getAllComics(offset : Int, limit : Int) : Flowable<Response<List<DomainComics>, Throwable>> {
        return Flowable.concat(
            getCacheComics(offset, limit).toFlowable(),
            getDatabaseComics(offset, limit).toFlowable(),
            getServerComics(offset, limit).toFlowable()
        )
        .withResult()
        .typicalBackgroundWorkSchedulers()
    }


    override fun getComicsCharacters(comics : DomainComics,
                                     offset : Int,
                                     limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return Single.concat(
            getCacheComicsCharacters(
                comics = comics,
                offset = offset,
                limit = limit
            ),
            getServerComicsCharacters(
                comics = comics,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheComicsCharacters(comics : DomainComics,
                                         offset : Int,
                                         limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.cacheDataStore.getComicsCharacters(
            comicsId = comics.id,
            offset = offset,
            limit = limit
        )
        .convertResponse { it.toDomainCharactersResponse() }
    }


    private fun getServerComicsCharacters(comics : DomainComics,
                                          offset : Int,
                                          limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getComicsCharacters(
                comicsId = comics.id,
                offset = offset,
                limit = limit
            )
            .cacheComicsCharacters(comics)
            .convertResponse { it.toDomainCharactersResponse(sortItems = true) }
            .successfulResponseOrError()
        )
    }


    override fun getComicsEvents(comics : DomainComics,
                                 offset : Int,
                                 limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return Single.concat(
            getCacheComicsEvents(
                comics = comics,
                offset = offset,
                limit = limit
            ),
            getServerComicsEvents(
                comics = comics,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheComicsEvents(comics : DomainComics,
                                     offset : Int,
                                     limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return this.cacheDataStore.getComicsEvents(
            comicsId = comics.id,
            offset = offset,
            limit = limit
        )
        .convertResponse { it.toDomainEventsResponse() }
    }


    private fun getServerComicsEvents(comics : DomainComics,
                                      offset : Int,
                                      limit : Int) : Single<Response<List<DomainEvent>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getComicsEvents(
                comicsId = comics.id,
                offset = offset,
                limit = limit
            )
            .cacheComicsEvents(comics)
            .convertResponse { it.toDomainEventsResponse(sortItems = true) }
            .successfulResponseOrError()
        )
    }


    private fun Single<Response<DataComics, Throwable>>.cacheSingleComicsResponse() : Single<Response<DataComics, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveComics(it) }
    }


    private fun Single<Response<List<DataComics>, Throwable>>.cacheComicsResponse() : Single<Response<List<DataComics>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveComics(it) }
    }


    private fun Single<Response<List<DataCharacter>, Throwable>>.cacheComicsCharacters(comics : DomainComics) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveComicsCharacters(comics.id, it) }
    }


    private fun Single<Response<List<DataEvent>, Throwable>>.cacheComicsEvents(comics : DomainComics) : Single<Response<List<DataEvent>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveComicsEvents(comics.id, it) }
    }


}