/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.work@gmail.com
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

package com.arthurivanets.sample.data.datastores.comics

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
internal class ComicsCacheDataStore(context: Context) : AbstractDataStore(context), ComicsDataStore {

    private val comicsCache = ConcurrentHashMap<Long, DataComics>()
    private val charactersCache = ConcurrentHashMap<Long, MutableMap<Long, DataCharacter>>()
    private val eventsCache = ConcurrentHashMap<Long, MutableMap<Long, DataEvent>>()

    override fun saveComics(comics: DataComics): Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { saveComicsInternal(comics) }
    }

    private fun saveComicsInternal(comics: DataComics): Response<DataComics, Throwable> {
        comicsCache[comics.id] = comics
        return Response.result(comics)
    }

    override fun saveComics(comics: List<DataComics>): Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { saveComicsInternal(comics) }
    }

    private fun saveComicsInternal(comics: List<DataComics>): Response<List<DataComics>, Throwable> {
        comics.forEach { comicsCache[it.id] = it }
        return Response.result(comics)
    }

    override fun updateComics(comics: DataComics): Single<Response<DataComics, Throwable>> {
        return saveComics(comics)
    }

    override fun updateComics(comics: List<DataComics>): Single<Response<List<DataComics>, Throwable>> {
        return saveComics(comics)
    }

    override fun deleteComics(comics: DataComics): Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { deleteComicsInternal(comics) }
    }

    private fun deleteComicsInternal(comics: DataComics): Response<DataComics, Throwable> {
        return Response.result(comicsCache.remove(comics.id))
    }

    override fun deleteComics(comics: List<DataComics>): Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { deleteComicsInternal(comics) }
    }

    private fun deleteComicsInternal(comics: List<DataComics>): Response<List<DataComics>, Throwable> {
        val deletedComics = ArrayList<DataComics>()

        for (singleComics in comics) {
            comicsCache.remove(singleComics.id)?.let { deletedComics.add(it) }
        }

        return Response.result(deletedComics)
    }

    override fun getSingleComics(id: Long): Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { getSingleComicsInternal(id) }
    }

    private fun getSingleComicsInternal(id: Long): Response<DataComics, Throwable> {
        return Response.result(comicsCache[id])
    }

    override fun getComics(offset: Int, limit: Int): Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { getComicsInternal(offset, limit) }
    }

    private fun getComicsInternal(offset: Int, limit: Int): Response<List<DataComics>, Throwable> {
        return comicsCache.values
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }

    override fun saveComicsCharacters(
        comicsId: Long,
        characters: List<DataCharacter>
    ): Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable { saveComicsCharactersInternal(comicsId, characters) }
    }

    private fun saveComicsCharactersInternal(comicsId: Long, characters: List<DataCharacter>): Response<List<DataCharacter>, Throwable> {
        val cachedCharacters = (charactersCache[comicsId] ?: ConcurrentHashMap())

        characters.forEach { cachedCharacters[it.id] = it }

        charactersCache[comicsId] = cachedCharacters

        return Response.result(characters)
    }

    override fun getComicsCharacters(
        comicsId: Long,
        offset: Int,
        limit: Int
    ): Single<Response<List<DataCharacter>, Throwable>> {
        return Single.fromCallable {
            getComicsCharactersInternal(
                comicsId = comicsId,
                offset = offset,
                limit = limit
            )
        }
    }

    private fun getComicsCharactersInternal(
        comicsId: Long,
        offset: Int,
        limit: Int
    ): Response<List<DataCharacter>, Throwable> {
        return (charactersCache[comicsId]?.values ?: emptyList<DataCharacter>())
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }

    override fun saveComicsEvents(comicsId: Long, events: List<DataEvent>): Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable { saveComicsEventsInternal(comicsId, events) }
    }

    private fun saveComicsEventsInternal(comicsId: Long, events: List<DataEvent>): Response<List<DataEvent>, Throwable> {
        val cachedEvents = (eventsCache[comicsId] ?: ConcurrentHashMap())

        events.forEach { cachedEvents[it.id] = it }

        eventsCache[comicsId] = cachedEvents

        return Response.result(events)
    }

    override fun getComicsEvents(
        comicsId: Long,
        offset: Int,
        limit: Int
    ): Single<Response<List<DataEvent>, Throwable>> {
        return Single.fromCallable {
            getComicsEventsInternal(
                comicsId = comicsId,
                offset = offset,
                limit = limit
            )
        }
    }

    private fun getComicsEventsInternal(
        comicsId: Long,
        offset: Int,
        limit: Int
    ): Response<List<DataEvent>, Throwable> {
        return (eventsCache[comicsId]?.values ?: emptyList<DataEvent>())
            .sortedBy { it.id }
            .take(offset, limit)
            .asResult()
    }

}
