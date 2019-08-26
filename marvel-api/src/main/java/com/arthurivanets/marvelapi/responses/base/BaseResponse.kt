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

package com.arthurivanets.marvelapi.responses.base

import android.text.TextUtils
import com.arthurivanets.marvelapi.Statuses
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class BaseResponse(
    @JsonProperty(Properties.CODE) var code : Int = 0,
    @JsonProperty(Properties.STATUS) var status : String? = null,
    @JsonProperty(Properties.MESSAGE) var message : String? = null
) {


    @get:JsonIgnore
    val hasStatus : Boolean
        get() = !TextUtils.isEmpty(status)

    @get:JsonIgnore
    val isErroneous : Boolean
        get() = !Statuses.OK.equals(status, ignoreCase = true)


    object Properties {

        const val CODE = "code"
        const val STATUS = "status"
        const val MESSAGE = "message"

    }


}