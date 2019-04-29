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
import com.arthurivanets.commons.data.datastore.AbstractDataStore
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.data.util.resultOrError
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.data.db.Database
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import io.reactivex.Single

/**
 *
 */
internal class CharactersDatabaseDataStore(context : Context) : AbstractDataStore(context), CharactersDataStore {


    override fun saveCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { saveCharacterInternal(character) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun saveCharacterInternal(character : DataCharacter) : Response<DataCharacter, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .save(character.toDatabaseCharacter())

        return@resultOrError character
    }


    override fun saveCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { saveCharactersInternal(characters) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun saveCharactersInternal(characters : List<DataCharacter>) : Response<List<DataCharacter>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .save(characters.toDatabaseCharacters())

        return@resultOrError characters
    }


    override fun updateCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { updateCharacterInternal(character) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun updateCharacterInternal(character : DataCharacter) : Response<DataCharacter, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .update(character.toDatabaseCharacter())

        return@resultOrError character
    }


    override fun updateCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { updateCharacterInternal(characters) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun updateCharacterInternal(characters : List<DataCharacter>) : Response<List<DataCharacter>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .update(characters.toDatabaseCharacters())

        return@resultOrError characters
    }


    override fun deleteCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { deleteCharacterInternal(character) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun deleteCharacterInternal(character : DataCharacter) : Response<DataCharacter, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .delete(character.toDatabaseCharacter())

        return@resultOrError character
    }


    override fun deleteCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { deleteCharactersInternal(characters) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun deleteCharactersInternal(characters : List<DataCharacter>) : Response<List<DataCharacter>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .delete(characters.toDatabaseCharacters())

        return@resultOrError characters
    }


    override fun getCharacter(id : Long) : Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { getCharacterInternal(id) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun getCharacterInternal(id : Long) : Response<DataCharacter, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .getById(id)
            ?.toDataCharacter()
    }


    override fun getCharacters(offset : Int, limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { getCharactersInternal(offset, limit) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun getCharactersInternal(offset : Int, limit : Int) : Response<List<DataCharacter>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .charactersTable()
            .get(offset = offset, limit = limit)
            .fromDatabaseToDataCharacters()
    }
    
    
    override fun saveCharacterComics(characterId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Character Comics cannot be saved into the Database.")
    }
    
    
    override fun getCharacterComics(characterId : Long,
                                    offset : Int,
                                    limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Character Comics cannot be fetched from the Database.")
    }
    
    
}