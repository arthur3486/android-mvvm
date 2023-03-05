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

package com.arthurivanets.marvelapi.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Comics(
    @JsonProperty(Properties.ID) var id: Long = -1L,
    @JsonProperty(Properties.DIGITAL_ID) var digitalId: Long = -1L,
    @JsonProperty(Properties.TITLE) var title: String = "",
    @JsonProperty(Properties.ISSUE_NUMBER) var issueNumber: Double = 0.0,
    @JsonProperty(Properties.VARIANT_DESCRIPTION) var variantDescription: String = "",
    @JsonProperty(Properties.DESCRIPTION) var description: String? = "",
    @JsonProperty(Properties.MODIFICATION_TIME) var modificationTime: String = "",
    @JsonProperty(Properties.ISBN) var isbn: String = "",
    @JsonProperty(Properties.UPC) var upc: String = "",
    @JsonProperty(Properties.DIAMOND_CODE) var diamondCode: String = "",
    @JsonProperty(Properties.EAN) var ean: String = "",
    @JsonProperty(Properties.ISSN) var issn: String = "",
    @JsonProperty(Properties.FORMAT) var format: String = "",
    @JsonProperty(Properties.PAGE_COUNT) var pageCount: Int = 0,
    @JsonProperty(Properties.URLS) var urls: List<Url> = emptyList(),
    @JsonProperty(Properties.THUMBNAIL) var thumbnail: Image = Image(),
    @JsonProperty(Properties.IMAGES) var images: List<Image> = emptyList(),
    @JsonProperty(Properties.CREATORS) var creators: CreatorList = CreatorList()
) {

    object Properties {

        const val ID = "id"
        const val DIGITAL_ID = "digitalId"
        const val TITLE = "title"
        const val ISSUE_NUMBER = "issueNumber"
        const val VARIANT_DESCRIPTION = "variantDescription"
        const val DESCRIPTION = "description"
        const val MODIFICATION_TIME = "modified"
        const val ISBN = "isbn"
        const val UPC = "upc"
        const val DIAMOND_CODE = "diamondCode"
        const val EAN = "ean"
        const val ISSN = "issn"
        const val FORMAT = "format"
        const val PAGE_COUNT = "pageCount"
        const val URLS = "urls"
        const val THUMBNAIL = "thumbnail"
        const val IMAGES = "images"
        const val CREATORS = "creators"

    }

}
