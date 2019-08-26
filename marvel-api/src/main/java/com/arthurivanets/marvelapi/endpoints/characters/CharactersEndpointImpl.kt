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

package com.arthurivanets.marvelapi.endpoints.characters

import com.arthurivanets.marvelapi.endpoints.AbstractEndpoint
import com.arthurivanets.marvelapi.responses.CharactersResponse
import com.arthurivanets.marvelapi.responses.ComicsResponse
import com.arthurivanets.marvelapi.services.CharactersService
import io.reactivex.Single

internal class CharactersEndpointImpl(charactersService : CharactersService) : AbstractEndpoint<CharactersService>(charactersService),
    CharactersEndpoint {


    override fun getCharacter(id : Long) : Single<CharactersResponse> {
        return service.getCharacterAsync(id)
    }


    override fun getCharacters(offset : Int, limit : Int) : Single<CharactersResponse> {
        return service.getCharactersAsync(
            offset = offset,
            limit = limit
        )
    }


    override fun getCharacterComics(characterId : Long,
                                    offset : Int,
                                    limit : Int) : Single<ComicsResponse> {
        return service.getCharacterComicsAsync(
            characterId = characterId,
            offset = offset,
            limit = limit
        )
    }


}