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
import com.arthurivanets.commons.rx.ktx.asSingle
import com.arthurivanets.commons.rx.ktx.typicalBackgroundWorkSchedulers
import com.arthurivanets.sample.data.api.MarvelApi
import com.arthurivanets.sample.data.datastores.characters.toResponse
import com.arthurivanets.sample.data.datastores.events.toResponse
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single

internal class ComicsServerDataStore(context : Context) : AbstractDataStore(context), ComicsDataStore {


    override fun saveComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        throw UnsupportedOperationException("Comics creation on the Server Side is unsupported.")
    }


    override fun saveComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Comics creation on the Server Side is unsupported.")
    }


    override fun updateComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        throw UnsupportedOperationException("Comics modification on the Server Side is unsupported.")
    }


    override fun updateComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Comics modification on the Server Side is unsupported.")
    }


    override fun deleteComics(comics : DataComics) : Single<Response<DataComics, Throwable>> {
        throw UnsupportedOperationException("Comics deletion on the Server Side is unsupported.")
    }


    override fun deleteComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>> {
        throw UnsupportedOperationException("Comics deletion on the Server Side is unsupported.")
    }


    override fun getSingleComics(id : Long) : Single<Response<DataComics, Throwable>> {
        return MarvelApi.INSTANCE.comics.getSingleComics(id)
            .flatMap { it.toSingleItemResponse().asSingle() }
            .typicalBackgroundWorkSchedulers()
    }


    override fun getComics(offset : Int, limit : Int) : Single<Response<List<DataComics>, Throwable>> {
        return MarvelApi.INSTANCE.comics.getComics(
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .typicalBackgroundWorkSchedulers()
    }


    override fun saveComicsCharacters(comicsId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>> {
        throw UnsupportedOperationException("Comics Characters cannot be saved on the side of the Server.")
    }


    override fun getComicsCharacters(comicsId : Long,
                                     offset : Int,
                                     limit : Int) : Single<Response<List<DataCharacter>, Throwable>> {
        return MarvelApi.INSTANCE.comics.getComicsCharacters(
            comicsId = comicsId,
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .typicalBackgroundWorkSchedulers()
    }


    override fun saveComicsEvents(comicsId : Long, events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>> {
        throw UnsupportedOperationException("Comics Events cannot be saved on the side of the Server.")
    }


    override fun getComicsEvents(comicsId : Long,
                                 offset : Int,
                                 limit : Int) : Single<Response<List<DataEvent>, Throwable>> {
        return MarvelApi.INSTANCE.comics.getComicsEvents(
            comicsId = comicsId,
            offset = offset,
            limit = limit
        )
        .flatMap { it.toResponse().asSingle() }
        .typicalBackgroundWorkSchedulers()
    }


}