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

package com.arthurivanets.sample.data.datastores.comics

import android.content.Context
import com.arthurivanets.commons.data.datastore.AbstractDataStore
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.data.util.resultOrError
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.data.db.Database
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single

internal class ComicsDatabaseDataStore(context : Context) : AbstractDataStore(context), ComicsDataStore {


    override fun saveComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { saveComicsInternal(comics) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun saveComicsInternal(comics : DataComics) : Response<DataComics, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .save(comics.toDatabaseComics())

        return@resultOrError comics
    }


    override fun saveComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { saveComicsInternal(comics) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun saveComicsInternal(comics : List<DataComics>) : Response<List<DataComics>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .save(comics.toDatabaseComics())

        return@resultOrError comics
    }


    override fun updateComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { updateComicsInternal(comics) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun updateComicsInternal(comics : DataComics) : Response<DataComics, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .update(comics.toDatabaseComics())

        return@resultOrError comics
    }


    override fun updateComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { updateComicsInternal(comics) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun updateComicsInternal(comics : List<DataComics>) : Response<List<DataComics>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .update(comics.toDatabaseComics())

        return@resultOrError comics
    }


    override fun deleteComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { deleteComicsInternal(comics) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun deleteComicsInternal(comics : DataComics) : Response<DataComics, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .delete(comics.toDatabaseComics())

        return@resultOrError comics
    }


    override fun deleteComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { deleteComicsInternal(comics) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun deleteComicsInternal(comics : List<DataComics>) : Response<List<DataComics>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .delete(comics.toDatabaseComics())

        return@resultOrError comics
    }


    override fun getSingleComics(id : Long) : Single<Response<DataComics, Throwable>> {
        return Single.fromCallable { getSingleComicsInternal(id) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun getSingleComicsInternal(id : Long) : Response<DataComics, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .getById(id)
            ?.toDataComics()
    }


    override fun getComics(offset : Int, limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return Single.fromCallable { getComicsInternal(offset, limit) }
            .typicalBackgroundWorkSchedulers()
    }


    private fun getComicsInternal(offset : Int, limit : Int) : Response<List<DataComics>, Throwable> = resultOrError {
        Database.getInstance(internalContext)
            .comicsTable()
            .get(offset = offset, limit = limit)
            .fromDatabaseToDataComics()
    }


    override fun saveComicsCharacters(comicsId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Comics Characters cannot be saved into the Database.")
    }


    override fun getComicsCharacters(comicsId : Long,
                                     offset : Int,
                                     limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Comics Characters cannot be fetched from the Database.")
    }


    override fun saveComicsEvents(comicsId : Long, events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        throw UnsupportedOperationException("Comics Events cannot be saved into the Database.")
    }


    override fun getComicsEvents(comicsId : Long,
                                 offset : Int,
                                 limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        throw UnsupportedOperationException("Comics Events cannot be fetched from the Database.")
    }


}