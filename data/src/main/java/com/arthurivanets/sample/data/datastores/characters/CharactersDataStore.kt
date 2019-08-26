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

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.datastores.base.DataStore
import com.arthurivanets.sample.data.util.DataCharacter
import com.arthurivanets.sample.data.util.DataComics
import io.reactivex.Single

interface CharactersDataStore : DataStore {

    fun saveCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>>

    fun saveCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>>

    fun updateCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>>

    fun updateCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>>

    fun deleteCharacter(character : DataCharacter) : Single<Response<DataCharacter, Throwable>>

    fun deleteCharacters(characters : List<DataCharacter>) : Single<Response<List<DataCharacter>, Throwable>>

    fun getCharacter(id : Long) : Single<Response<DataCharacter, Throwable>>

    fun getCharacters(offset : Int, limit : Int) : Single<Response<List<DataCharacter>, Throwable>>
    
    fun saveCharacterComics(characterId : Long, comics : List<DataComics>) : Single<Response<List<DataComics>, Throwable>>
    
    fun getCharacterComics(characterId : Long,
                           offset : Int,
                           limit : Int) : Single<Response<List<DataComics>, Throwable>>

}