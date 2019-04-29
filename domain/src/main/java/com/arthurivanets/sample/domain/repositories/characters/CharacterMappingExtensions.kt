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

@file:JvmName("CharacterMappingUtils")

package com.arthurivanets.sample.domain.repositories.characters

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.domain.repositories.util.toDomainImage
import com.arthurivanets.sample.domain.repositories.util.toDomainUrls
import com.arthurivanets.sample.domain.repositories.util.toResponse
import com.arthurivanets.sample.domain.util.DataCharacter
import com.arthurivanets.sample.domain.util.DomainCharacter


internal fun Response<DataCharacter, Throwable>.toDomainCharacterResponse() : Response<DomainCharacter, Throwable> {
    return this.toResponse { it.toDomainCharacter() }
}


internal fun Response<List<DataCharacter>, Throwable>.toDomainCharactersResponse(sortItems : Boolean = false) : Response<List<DomainCharacter>, Throwable> {
    return this.toResponse { it.toDomainCharacters(sortItems) }
}


internal fun List<DataCharacter>.toDomainCharacters(sort : Boolean = false) : List<DomainCharacter> {
    return this.map { it.toDomainCharacter() }
        .let { characters -> (if(sort) characters.sortedBy { it.id } else characters) }
}


internal fun DataCharacter.toDomainCharacter() : DomainCharacter {
    return DomainCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        thumbnail = this.thumbnail.toDomainImage(),
        urls = this.urls.toDomainUrls()
    )
}