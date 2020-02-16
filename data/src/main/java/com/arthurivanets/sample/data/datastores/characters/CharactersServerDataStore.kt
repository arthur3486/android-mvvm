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

package com.arthurivanets.sample.data.datastores.characters

import android.content.Context
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.rx.ktx.applyIOWorkSchedulers
import com.arthurivanets.commons.rx.ktx.asSingle
import com.arthurivanets.marvelapi.MarvelApi

import com.arthurivanets.sample.data.datastores.base.AbstractDataStore
import com.arthurivanets.sample.data.datastores.comics.toResponse
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import io.reactivex.Single

/**
 *
 */
internal class CharactersServerDataStore(context : Context) : AbstractDataStore(context), CharactersDataStore {


    override fun saveCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        throw UnsupportedOperationException("Character creation on the Server Side is unsupported.")
    }


    override fun saveCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Character creation on the Server Side is unsupported.")
    }


    override fun updateCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        throw UnsupportedOperationException("Character modification on the Server Side is unsupported.")
    }


    override fun updateCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Character modification on the Server Side is unsupported.")
    }


    override fun deleteCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        throw UnsupportedOperationException("Character deletion on the Server Side is unsupported.")
    }


    override fun deleteCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Character deletion on the Server Side is unsupported.")
    }


    override fun getCharacter(id : Long) : Single<Response<DataCharacter, Throwable>> {
        return MarvelApi.INSTANCE.characters.getCharacter(id)
            .flatMap { it.toSingleItemResponse().asSingle() }
            .applyIOWorkSchedulers()
    }


    override fun getCharacters(offset : Int, limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return MarvelApi.INSTANCE.characters.getCharacters(
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .applyIOWorkSchedulers()
    }
    
    
    override fun saveCharacterComics(characterId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Character Comics cannot be saved on the side of the Server.")
    }
    
    
    override fun getCharacterComics(
        characterId : Long,
        offset : Int,
        limit : Int
    ) : Single<Response<List<DataComics>, Throwable>> {
        return MarvelApi.INSTANCE.characters.getCharacterComics(
            characterId = characterId,
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .applyIOWorkSchedulers()
    }
    
    
}