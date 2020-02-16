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

package com.arthurivanets.sample.domain.repositories.characters

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
import com.arthurivanets.sample.data.datastores.characters.CharactersDataStore
import com.arthurivanets.sample.domain.repositories.base.AbstractRepository
import com.arthurivanets.sample.domain.repositories.comics.toDomainComicsResponse
import com.arthurivanets.sample.domain.repositories.util.convertResponse
import com.arthurivanets.sample.domain.util.DataCharacter
import com.arthurivanets.sample.domain.util.DataComics
import com.arthurivanets.sample.domain.util.DomainCharacter
import com.arthurivanets.sample.domain.util.DomainComics
import io.reactivex.Flowable
import io.reactivex.Single

internal class CharactersRepositoryImpl(
    private val cacheDataStore : CharactersDataStore,
    private val databaseDataStore : CharactersDataStore,
    private val serverDataStore : CharactersDataStore,
    private val networkStateProvider : NetworkStateProvider,
    schedulerProvider : SchedulerProvider
) : AbstractRepository<BusEvent<*>>(schedulerProvider), CharactersRepository {


    override fun getCharacter(id : Long) : Single<Response<DomainCharacter, Throwable>> {
        return Single.concat(
            getCacheCharacter(id),
            getDatabaseCharacter(id),
            getServerCharacter(id)
        )
        .withResult()
        .first(Response.error(NoResultError("No Result found.")))
        .flatMapOrError(::createCacheCharacter)
        .convertResponse { it.toDomainCharacterResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheCharacter(id : Long) : Single<Response<DataCharacter, Throwable>> {
        return this.cacheDataStore.getCharacter(id)
    }


    private fun getDatabaseCharacter(id : Long) : Single<Response<DataCharacter, Throwable>> {
        return this.databaseDataStore.getCharacter(id)
    }


    private fun getServerCharacter(id : Long) : Single<Response<DataCharacter, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getCharacter(id)
                .flatMapOrError(::createDatabaseCharacter)
        ).successfulResponseOrError()
    }


    override fun getCharacters(offset : Int, limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return Single.concat(
            getCacheCharacters(offset, limit),
            getDatabaseCharacters(offset, limit),
            getServerCharacters(offset, limit)
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError(::createCacheCharacters)
        .convertResponse { it.toDomainCharactersResponse() }
        .withAppropriateSchedulers()
    }


    private fun getCacheCharacters(offset : Int, limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.cacheDataStore.getCharacters(offset, limit)
    }


    private fun getDatabaseCharacters(offset : Int, limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.databaseDataStore.getCharacters(offset, limit)
            .successfulResponseOrError()
    }


    private fun getServerCharacters(offset : Int, limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getCharacters(offset, limit)
                .flatMapOrError(::createDatabaseCharacters)
        ).successfulResponseOrError()
    }


    override fun getAllCharacters(offset : Int, limit : Int) : Flowable<Response<List<DomainCharacter>, Throwable>> {
        return Flowable.concat(
            getCacheCharacters(offset, limit).toFlowable(),
            getDatabaseCharacters(offset, limit).toFlowable(),
            getServerCharacters(offset, limit).toFlowable()
        )
        .withResult()
        .flatMapOrError { createCacheCharacters(it).toFlowable() }
        .convertResponse { it.toDomainCharactersResponse() }
        .withAppropriateSchedulers()
    }
    
    
    override fun getCharacterComics(
        character : DomainCharacter,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DomainComics>, Throwable>> {
        return Single.concat(
            getCacheCharacterComics(
                characterId = character.id,
                offset = offset,
                limit = limit
            ),
            getServerCharacterComics(
                characterId = character.id,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(Response.result(emptyList()))
        .flatMapOrError { createCacheComics(character.id, it) }
        .convertResponse { it.toDomainComicsResponse() }
        .withAppropriateSchedulers()
    }
    
    
    private fun getCacheCharacterComics(
        characterId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataComics>, Throwable>> {
        return this.cacheDataStore.getCharacterComics(
            characterId = characterId,
            offset = offset,
            limit = limit
        )
    }
    
    
    private fun getServerCharacterComics(
        characterId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataComics>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getCharacterComics(
                characterId = characterId,
                offset = offset,
                limit = limit
            )
        ).successfulResponseOrError()
    }
    
    
    private fun createDatabaseCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        return this.databaseDataStore.saveCharacter(character)
            .successfulResponseOrError()
    }
    
    
    private fun createDatabaseCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.databaseDataStore.saveCharacters(characters)
            .successfulResponseOrError()
    }
    
    
    private fun createCacheCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        return this.cacheDataStore.saveCharacter(character)
    }
    
    
    private fun createCacheCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return this.cacheDataStore.saveCharacters(characters)
    }
    
    
    private fun createCacheComics(characterId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return this.cacheDataStore.saveCharacterComics(characterId, comics)
    }


}