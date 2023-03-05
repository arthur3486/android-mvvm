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
data class Data<T>(
    @JsonProperty(Properties.OFFSET) var offset: Int = 0,
    @JsonProperty(Properties.LIMIT) var limit: Int = 0,
    @JsonProperty(Properties.TOTAL) var total: Int = 0,
    @JsonProperty(Properties.COUNT) var count: Int = 0,
    @JsonProperty(Properties.RESULTS) var results: List<T> = ArrayList()
) {

    object Properties {

        const val OFFSET = "offset"
        const val LIMIT = "limit"
        const val TOTAL = "total"
        const val COUNT = "count"
        const val RESULTS = "results"

    }

}
