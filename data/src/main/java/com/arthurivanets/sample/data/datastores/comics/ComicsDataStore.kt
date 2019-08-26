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

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.datastores.base.DataStore
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import com.arthurivanets.sample.data.util.DataEvent
import io.reactivex.Single

interface ComicsDataStore : DataStore {

    fun saveComics(comics : DataComics) : Single<Response<DataComics, Throwable>>

    fun saveComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>>

    fun updateComics(comics : DataComics) : Single<Response<DataComics, Throwable>>

    fun updateComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>>

    fun deleteComics(comics : DataComics) : Single<Response<DataComics, Throwable>>

    fun deleteComics(comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>>

    fun getSingleComics(id : Long) : Single<Response<DataComics, Throwable>>

    fun getComics(offset : Int, limit : Int) : Single<Response<List<DataComics>, Throwable>>

    fun saveComicsCharacters(comicsId : Long, characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>>

    fun getComicsCharacters(comicsId : Long,
                            offset : Int,
                            limit : Int) : Single<Response<List<DataCharacter>, Throwable>>

    fun saveComicsEvents(comicsId : Long, events : List<DataEvent>) : Single<Response<List<DataEvent>, Throwable>>

    fun getComicsEvents(comicsId : Long,
                        offset : Int,
                        limit : Int) : Single<Response<List<DataEvent>, Throwable>>

}