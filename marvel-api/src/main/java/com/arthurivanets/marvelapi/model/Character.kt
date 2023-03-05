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

package com.arthurivanets.marvelapi.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Character(
    @JsonProperty(Properties.ID) var id: Long = -1L,
    @JsonProperty(Properties.NAME) var name: String = "",
    @JsonProperty(Properties.DESCRIPTION) var description: String = "",
    @JsonProperty(Properties.MODIFICATION_TIME) var modificationTime: String = "",
    @JsonProperty(Properties.THUMBNAIL) var thumbnail: Image = Image(),
    @JsonProperty(Properties.URLS) var urls: List<Url> = ArrayList()
) {

    object Properties {

        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val MODIFICATION_TIME = "modified"
        const val URLS = "urls"
        const val THUMBNAIL = "thumbnail"

    }

}
