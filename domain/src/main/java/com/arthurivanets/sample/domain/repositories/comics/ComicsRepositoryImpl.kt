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
import com.arthurivanets.sample.data.datastores.comics.ComicsDataStore
import com.arthurivanets.sample.domain.repositories.base.AbstractRepository
import com.arthurivanets.sample.domain.repositories.characters.toDomainCharactersResponse
import com.arthurivanets.sample.domain.repositories.events.toDomainEventsResponse
import com.arthurivanets.sample.domain.repositories.util.convertResponse
import com.arthurivanets.sample.domain.util.*
import io.reactivex.Flowable
import io.reactivex.Single

internal class ComicsRepositoryImpl(
    private val cacheDataStore : ComicsDataStore,
    private val databaseDataStore : ComicsDataStore,
    private val serverDataStore : ComicsDataStore,
    private val networkStateProvider : NetworkStateProvider,
    schedulerProvider : SchedulerProvider
) : AbstractRepository<BusEvent<*>>(schedulerProvider), ComicsRepository {


    override fun getSingleComics(id : Long) : Single<Response<DomainComics, Throwable>> {
        return Single.concat(
            getCacheSingleComics(id),
            getDatabaseSingleComics(id),
            getServerSingleComics(id)
        )
        .withResult()
        .first(Response.error(NoResultError("No Result found.")))
        .flatMapOrError(::createCacheComics)
        .convertResponse { it.toDomainSingleComicsResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheSingleComics(id : Long) : Single<Response<DataComics, Throwable>> {
        return this.cacheDataStore.getSingleComics(id)
    }


    private fun getDatabaseSingleComics(id : Long) : Single<Response<DataComics, Throwable>> {
        return this.databaseDataStore.getSingleComics(id)
            .successfulResponseOrError()
    }


    private fun getServerSingleComics(id : Long) : Single<Response<DataComics, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getSingleComics(id)
                .flatMapOrError(::createDatabaseComics)
        ).successfulResponseOrError()
    }


    override fun getComics(offset : Int, limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return Single.concat(
            getCacheComics(offset, limit),
            getDatabaseComics(offset, limit),
            getServerComics(offset, limit)
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError(::createCacheComics)
        .convertResponse { it.toDomainComicsResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheComics(offset : Int, limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return this.cacheDataStore.getComics(offset, limit)
    }


    private fun getDatabaseComics(offset : Int, limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return this.databaseDataStore.getComics(offset, limit)
            .successfulResponseOrError()
    }


    private fun getServerComics(offset : Int, limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getComics(offset, limit)
                .flatMapOrError(::createDatabaseComics)
        ).successfulResponseOrError()
    }


    override fun getAllComics(offset : Int, limit : Int) : Flowable<Response<List<DomainComics>, Throwable>> {
        return Flowable.concat(
            getCacheComics(offset, limit).toFlowable(),
            getDatabaseComics(offset, limit).toFlowable(),
            getServerComics(offset, limit).toFlowable()
        )
        .withResult()
        .flatMapOrError { createCacheComics(it).toFlowable() }
        .convertResponse { it.toDomainComicsResponse() }
        .withAppropriateSchedulers()
    }


    override fun getComicsCharacters(
        comics : DomainComics,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DomainCharacter>, Throwable>> {
        return Single.concat(
            getCacheComicsCharacters(
                comicsId = comics.id,
                offset = offset,
                limit = limit
            ),
            getServerComicsCharacters(
                comicsId = comics.id,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError { createCacheCharacters(comics.id, it) }
        .convertResponse { it.toDomainCharactersResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheComicsCharacters(
        comicsId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.cacheDataStore.getComicsCharacters(
            comicsId = comicsId,
            offset = offset,
            limit = limit
        )
    }


    private fun getServerComicsCharacters(
        comicsId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getComicsCharacters(
                comicsId = comicsId,
                offset = offset,
                limit = limit
            )
        ).successfulResponseOrError()
    }


    override fun getComicsEvents(
        comics : DomainComics,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DomainEvent>, Throwable>> {
        return Single.concat(
            getCacheComicsEvents(
                comicsId = comics.id,
                offset = offset,
                limit = limit
            ),
            getServerComicsEvents(
                comicsId = comics.id,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError { createCacheEvents(comics.id, it) }
        .convertResponse { it.toDomainEventsResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheComicsEvents(
        comicsId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataEvent>, Throwable>> {
        return this.cacheDataStore.getComicsEvents(
            comicsId = comicsId,
            offset = offset,
            limit = limit
        )
    }


    private fun getServerComicsEvents(
        comicsId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataEvent>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getComicsEvents(
                comicsId = comicsId,
                offset = offset,
                limit = limit
            )
        ).successfulResponseOrError()
    }
    
    
    private fun createDatabaseComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        return this.databaseDataStore.saveComics(comics)
            .successfulResponseOrError()
    }
    
    
    private fun createDatabaseComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return this.databaseDataStore.saveComics(comics)
            .successfulResponseOrError()
    }
    
    
    private fun createCacheComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        return this.cacheDataStore.saveComics(comics)
    }
    
    
    private fun createCacheComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return this.cacheDataStore.saveComics(comics)
    }
    
    
    private fun createCacheEvents(comicsId : Long, events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        return this.cacheDataStore.saveComicsEvents(comicsId, events)
    }
    
    
    private fun createCacheCharacters(comicsId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.cacheDataStore.saveComicsCharacters(comicsId, characters)
    }


}