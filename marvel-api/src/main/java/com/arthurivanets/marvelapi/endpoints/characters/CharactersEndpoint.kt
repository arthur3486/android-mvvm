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

package com.arthurivanets.marvelapi.endpoints.characters

import com.arthurivanets.marvelapi.endpoints.Endpoint
import com.arthurivanets.marvelapi.responses.CharactersResponse
import com.arthurivanets.marvelapi.responses.ComicsResponse
import io.reactivex.Single

interface CharactersEndpoint : Endpoint {

    fun getCharacter(id: Long): Single<CharactersResponse>

    fun getCharacters(offset: Int = 0, limit: Int): Single<CharactersResponse>

    fun getCharacterComics(
        characterId: Long,
        offset: Int = 0,
        limit: Int
    ): Single<ComicsResponse>

}
