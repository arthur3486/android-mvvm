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
import com.arthurivanets.sample.data.datastores.characters.CharactersDataStore
import com.arthurivanets.sample.domain.repositories.comics.toDomainComicsResponse
import com.arthurivanets.sample.domain.repositories.util.StubEventBus
import com.arthurivanets.sample.domain.repositories.util.convertResponse
import com.arthurivanets.sample.domain.util.DataCharacter
import com.arthurivanets.sample.domain.util.DataComics
import com.arthurivanets.sample.domain.util.DomainCharacter
import com.arthurivanets.sample.domain.util.DomainComics
import io.reactivex.Flowable
import io.reactivex.Single

internal class CharactersRepositoryImpl(
    val cacheDataStore : CharactersDataStore,
    val databaseDataStore : CharactersDataStore,
    val serverDataStore : CharactersDataStore,
    val networkStateProvider : NetworkStateProvider
) : AbstractRepository<RepositoryEventBus>(StubEventBus()), CharactersRepository {


    override fun getCharacter(id : Long) : Single<Response<DomainCharacter, Throwable>> {
        return Single.concat(
            getCacheCharacter(id),
            getDatabaseCharacter(id),
            getServerCharacter(id)
        )
        .withResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheCharacter(id : Long) : Single<Response<DomainCharacter, Throwable>> {
        return this.cacheDataStore.getCharacter(id)
            .convertResponse { it.toDomainCharacterResponse() }
    }


    private fun getDatabaseCharacter(id : Long) : Single<Response<DomainCharacter, Throwable>> {
        return this.databaseDataStore.getCharacter(id)
            .cacheCharacterResponse()
            .convertResponse { it.toDomainCharacterResponse() }
            .successfulResponseOrError()
    }


    private fun getServerCharacter(id : Long) : Single<Response<DomainCharacter, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getCharacter(id).flatMapOrError { character ->
                this.databaseDataStore.saveCharacter(character).successfulResponseOrError()
            }
            .cacheCharacterResponse()
            .convertResponse { it.toDomainCharacterResponse() }
        )
    }


    override fun getCharacters(offset : Int, limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return Single.concat(
            getCacheCharacters(offset, limit),
            getDatabaseCharacters(offset, limit),
            getServerCharacters(offset, limit)
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }


    private fun getCacheCharacters(offset : Int, limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.cacheDataStore.getCharacters(offset, limit)
            .convertResponse { it.toDomainCharactersResponse() }
    }


    private fun getDatabaseCharacters(offset : Int, limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.databaseDataStore.getCharacters(offset, limit)
            .cacheCharactersResponse()
            .convertResponse { it.toDomainCharactersResponse() }
            .successfulResponseOrError()
    }


    private fun getServerCharacters(offset : Int, limit : Int) : Single<Response<List<DomainCharacter>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getCharacters(offset, limit).flatMapOrError { characters ->
                this.databaseDataStore.saveCharacters(characters).successfulResponseOrError()
            }
            .cacheCharactersResponse()
            .convertResponse { it.toDomainCharactersResponse(sortItems = true) }
        )
    }


    override fun getAllCharacters(offset : Int, limit : Int) : Flowable<Response<List<DomainCharacter>, Throwable>> {
        return Flowable.concat(
            getCacheCharacters(offset, limit).toFlowable(),
            getDatabaseCharacters(offset, limit).toFlowable(),
            getServerCharacters(offset, limit).toFlowable()
        )
        .withResult()
        .typicalBackgroundWorkSchedulers()
    }
    
    
    override fun getCharacterComics(character : DomainCharacter,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return Single.concat(
            getCacheCharacterComics(
                character = character,
                offset = offset,
                limit = limit
            ),
            getServerCharacterComics(
                character = character,
                offset = offset,
                limit = limit
            )
        )
        .withNonEmptyResult()
        .first(NoResultError("No Result found.").asError())
        .typicalBackgroundWorkSchedulers()
    }
    
    
    private fun getCacheCharacterComics(character : DomainCharacter,
                                        offset : Int,
                                        limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.cacheDataStore.getCharacterComics(
            characterId = character.id,
            offset = offset,
            limit = limit
        )
        .convertResponse { it.toDomainComicsResponse() }
    }
    
    
    private fun getServerCharacterComics(character : DomainCharacter,
                                         offset : Int,
                                         limit : Int) : Single<Response<List<DomainComics>, Throwable>> {
        return this.networkStateProvider.ifNetworkAvailable(
            this.serverDataStore.getCharacterComics(
                characterId = character.id,
                offset = offset,
                limit = limit
            )
            .cacheCharacterComics(character)
            .convertResponse { it.toDomainComicsResponse(sortItems = true) }
            .successfulResponseOrError()
        )
    }
    
    
    private fun Single<Response<DataCharacter, Throwable>>.cacheCharacterResponse() : Single<Response<DataCharacter, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveCharacter(it) }
    }


    private fun Single<Response<List<DataCharacter>, Throwable>>.cacheCharactersResponse() : Single<Response<List<DataCharacter>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveCharacters(it) }
    }
    
    
    private fun Single<Response<List<DataComics>, Throwable>>.cacheCharacterComics(character : DomainCharacter) : Single<Response<List<DataComics>, Throwable>> {
        return this.flatMapOrError { cacheDataStore.saveCharacterComics(character.id, it) }
    }


}