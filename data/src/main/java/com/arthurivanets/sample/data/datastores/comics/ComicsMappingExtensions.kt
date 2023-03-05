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

@file:JvmName("ComicsMappingUtils")

package com.arthurivanets.sample.data.datastores.comics

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.datastores.util.*
import com.arthurivanets.sample.data.util.*

internal fun com.arthurivanets.marvelapi.responses.ComicsResponse.toSingleItemResponse(): Response<DataComics, Throwable> {
    return this.toSingleItemResponse { it.toDataComics() }
}

internal fun com.arthurivanets.marvelapi.responses.ComicsResponse.toResponse(): Response<List<DataComics>, Throwable> {
    return this.toResponse { it.toDataComics() }
}

internal fun Collection<ApiComics>.toDataComics(): List<DataComics> {
    return this.map { it.toDataComics() }
}

internal fun Collection<DatabaseComics>.fromDatabaseToDataComics(): List<DataComics> {
    return this.map { it.toDataComics() }
}

internal fun Collection<DataComics>.toDatabaseComics(): List<DatabaseComics> {
    return this.map { it.toDatabaseComics() }
}

internal fun ApiComics.toDataComics(): DataComics {
    return DataComics(
        id = this.id,
        digitalId = this.digitalId,
        title = this.title,
        issueNumber = this.issueNumber,
        variantDescription = this.variantDescription,
        description = (this.description ?: ""),
        modificationTimeInMillis = this.modificationTime.toMillisFromGeneralTime(),
        isbn = this.isbn,
        upc = this.upc,
        diamondCode = this.diamondCode,
        ean = this.ean,
        issn = this.issn,
        format = this.format,
        pageCount = this.pageCount,
        thumbnail = this.thumbnail.toDataImage(),
        images = this.images.toDataImages(),
        creators = this.creators.items.toDataCreators(),
        urls = this.urls.toDataUrls()
    )
}

internal fun DatabaseComics.toDataComics(): DataComics {
    return DataComics(
        id = this.id,
        digitalId = this.digitalId,
        title = this.title,
        issueNumber = this.issueNumber,
        variantDescription = this.variantDescription,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        isbn = this.isbn,
        upc = this.upc,
        diamondCode = this.diamondCode,
        ean = this.ean,
        issn = this.issn,
        format = this.format,
        pageCount = this.pageCount,
        thumbnail = this.thumbnail.toObject(DataImage::class.java),
        images = this.images.toList(DataImage::class.java),
        creators = this.creators.toList(DataCreator::class.java),
        urls = this.urls.toList(DataUrl::class.java)
    )
}

internal fun DataComics.toDatabaseComics(): DatabaseComics {
    return DatabaseComics(
        id = this.id,
        digitalId = this.digitalId,
        title = this.title,
        issueNumber = this.issueNumber,
        variantDescription = this.variantDescription,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        isbn = this.isbn,
        upc = this.upc,
        diamondCode = this.diamondCode,
        ean = this.ean,
        issn = this.issn,
        format = this.format,
        pageCount = this.pageCount,
        thumbnail = this.thumbnail.toJsonString(),
        images = this.images.toJsonString(),
        creators = this.creators.toJsonString(),
        urls = this.urls.toJsonString()
    )
}
