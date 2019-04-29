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

package com.arthurivanets.sample.domain.repositories.comics

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.domain.repositories.util.*
import com.arthurivanets.sample.domain.util.DataComics
import com.arthurivanets.sample.domain.util.DomainComics


internal fun Response<DataComics, Throwable>.toDomainSingleComicsResponse() : Response<DomainComics, Throwable> {
    return this.toResponse { it.toDomainComics() }
}


internal fun Response<List<DataComics>, Throwable>.toDomainComicsResponse(sortItems : Boolean = false) : Response<List<DomainComics>, Throwable> {
    return this.toResponse { it.toDomainComics(sortItems) }
}


internal fun Collection<DataComics>.toDomainComics(sort : Boolean = false) : List<DomainComics> {
    return this.map { it.toDomainComics() }
        .let { comics -> (if(sort) comics.sortedBy { it.id } else comics) }
}


internal fun DataComics.toDomainComics() : DomainComics {
    return DomainComics(
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
        thumbnail = this.thumbnail.toDomainImage(),
        images = this.images.toDomainImages(),
        creators = this.creators.toDomainCreators(),
        urls = this.urls.toDomainUrls()
    )
}