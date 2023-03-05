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
data class Event(
    @JsonProperty(Properties.ID) var id: Long = -1L,
    @JsonProperty(Properties.TITLE) var title: String = "",
    @JsonProperty(Properties.DESCRIPTION) var description: String = "",
    @JsonProperty(Properties.URLS) var urls: List<Url> = ArrayList(),
    @JsonProperty(Properties.MODIFICATION_TIME) var modified: String = "",
    @JsonProperty(Properties.START_DATE) var startDate: String? = "",
    @JsonProperty(Properties.END_DATE) var endDate: String? = "",
    @JsonProperty(Properties.THUMBNAIL) var thumbnail: Image = Image(),
    @JsonProperty(Properties.CREATORS) var creators: CreatorList = CreatorList()
) {

    object Properties {

        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URLS = "urls"
        const val MODIFICATION_TIME = "modified"
        const val START_DATE = "start"
        const val END_DATE = "end"
        const val THUMBNAIL = "thumbnail"
        const val CREATORS = "creators"

    }

}
