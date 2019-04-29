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

package com.arthurivanets.sample.data.datastores.characters

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.api.responses.CharactersResponse
import com.arthurivanets.sample.data.datastores.util.toDataImage
import com.arthurivanets.sample.data.datastores.util.toDataUrls
import com.arthurivanets.sample.data.datastores.util.toResponse
import com.arthurivanets.sample.data.datastores.util.toSingleItemResponse
import com.arthurivanets.sample.data.util.*


internal fun CharactersResponse.toSingleItemResponse() : Response<DataCharacter, Throwable> {
    return this.toSingleItemResponse { it.toDataCharacter() }
}


internal fun CharactersResponse.toResponse() : Response<List<DataCharacter>, Throwable> {
    return this.toResponse { it.toDataCharacters() }
}


internal fun Collection<ApiCharacter>.toDataCharacters() : List<DataCharacter> {
    return this.map { it.toDataCharacter() }
}


internal fun Collection<DatabaseCharacter>.fromDatabaseToDataCharacters() : List<DataCharacter> {
    return this.map { it.toDataCharacter() }
}


internal fun Collection<DataCharacter>.toDatabaseCharacters() : List<DatabaseCharacter> {
    return this.map { it.toDatabaseCharacter() }
}


internal fun ApiCharacter.toDataCharacter() : DataCharacter {
    return DataCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        modificationTimeInMillis = this.modificationTime.toMillisFromGeneralTime(),
        thumbnail = this.thumbnail.toDataImage(),
        urls = this.urls.toDataUrls()
    )
}


internal fun DatabaseCharacter.toDataCharacter() : DataCharacter {
    return DataCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        thumbnail = this.thumbnail.toObject(DataImage::class.java),
        urls = this.urls.toList(DataUrl::class.java)
    )
}


internal fun DataCharacter.toDatabaseCharacter() : DatabaseCharacter {
    return DatabaseCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        thumbnail = this.thumbnail.toJsonString(),
        urls = this.urls.toJsonString()
    )
}