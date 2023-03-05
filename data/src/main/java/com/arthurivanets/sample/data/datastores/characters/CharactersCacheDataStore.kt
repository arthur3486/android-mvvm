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
import com.arthurivanets.commons.data.util.asResult
import com.arthurivanets.sample.data.datastores.base.AbstractDataStore
import com.arthurivanets.sample.data.datastores.util.take
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import io.reactivex.Single
import java.util.concurrent.ConcurrentHashMap

/**
 *
 */
internal class CharactersCacheDataStore(context: Context) : AbstractDataStore(context), CharactersDataStore {

    private val cache = ConcurrentHashMap<Long, DataCharacter>()
    private val comicsCache = ConcurrentHashMap<Long, MutableMap<Long, DataComics>>()

    override fun saveCharacter(character: DataCharacter): Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { saveCharacterInternal(character) }
    }

    private fun saveCharacterInternal(character: DataCharacter): Response<DataCharacter, Throwable> {
        cache[character.id] = character
        return Response.result(character)
    }

    override fun saveCharacters(characters: List<DataCharacter>): Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { saveCharactersInternal(characters) }
    }

    private fun saveCharactersInternal(characters: List<DataCharacter>): Response<List<DataCharacter>, Throwable> {
        characters.forEach { cache[it.id] = it }
        return Response.result(characters)
    }

    override fun updateCharacter(character: DataCharacter): Single<Response<DataCharacter, Throwable>> {
        return saveCharacter(character)
    }

    override fun updateCharacters(characters: List<DataCharacter>): Single<Response<List<DataCharacter>, Throwable>> {
        return saveCharacters(characters)
    }

    override fun deleteCharacter(character: DataCharacter): Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { deleteCharacterInternal(character) }
    }

    private fun deleteCharacterInternal(character: DataCharacter): Response<DataCharacter, Throwable> {
        return Response.result(cache.remove(character.id))
    }

    override fun deleteCharacters(characters: List<DataCharacter>): Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { deleteCharactersInternal(characters) }
    }

    private fun deleteCharactersInternal(characters: List<DataCharacter>): Response<List<DataCharacter>, Throwable> {
        val deletedCharacters = ArrayList<DataCharacter>()

        for (character in characters) {
            cache.remove(character.id)?.let { deletedCharacters.add(it) }
        }

        return Response.result(deletedCharacters)
    }

    override fun getCharacter(id: Long): Single<Response<DataCharacter, Throwable>> {
        return Single.fromCallable { getCharacterInternal(id) }
    }

    private fun getCharacterInternal(id: Long): Response<DataCharacter, Throwable> {
        return Response.result(cache[id])
    }

    override fun getCharacters(offset: Int, limit: Int): Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { getCharactersInternal(offset, limit) }
    }

    private fun getCharactersInternal(offset: Int, limit: Int): Response<List<DataCharacter>, Throwable> {
        return cache.values
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }

    override fun saveCharacterComics(characterId: Long, comics: List<DataComics>): Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { saveCharacterComicsInternal(characterId, comics) }
    }

    private fun saveCharacterComicsInternal(characterId: Long, comics: List<DataComics>): Response<List<DataComics>, Throwable> {
        val cachedComics = (comicsCache[characterId] ?: ConcurrentHashMap())

        comics.forEach { cachedComics[it.id] = it }

        comicsCache[characterId] = cachedComics

        return Response.result(comics)
    }

    override fun getCharacterComics(
        characterId: Long,
        offset: Int,
        limit: Int
    ): Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable {
            getCharacterComicsInternal(
                characterId = characterId,
                offset = offset,
                limit = limit
            )
        }
    }

    private fun getCharacterComicsInternal(
        characterId: Long,
        offset: Int,
        limit: Int
    ): Response<List<DataComics>, Throwable> {
        return (comicsCache[characterId]?.values ?: emptyList<DataComics>())
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }

}
