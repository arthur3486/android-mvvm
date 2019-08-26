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

package com.arthurivanets.marvelapi.endpoints

/**
 *
 */
object EndpointPaths {


    object Characters {

        const val BASE = "characters"
        const val LIST_SINGLE = "$BASE/{${EndpointPaths.Params.ID}}"
        const val LIST_COMICS = "$BASE/{${EndpointPaths.Params.ID}}/comics"

    }


    object Events {

        const val BASE = "events"
        const val LIST_SINGLE = "$BASE/{${EndpointPaths.Params.ID}}"
        const val LIST_CHARACTERS = "$BASE/{${EndpointPaths.Params.ID}}/characters"
        const val LIST_COMICS = "$BASE/{${EndpointPaths.Params.ID}}/comics"

    }


    object Comics {

        const val BASE = "comics"
        const val LIST_SINGLE = "$BASE/{${EndpointPaths.Params.ID}}"
        const val LIST_CHARACTERS = "$BASE/{${EndpointPaths.Params.ID}}/characters"
        const val LIST_EVENTS = "$BASE/{${EndpointPaths.Params.ID}}/events"

    }


    object Params {

        const val API_KEY = "apikey"
        const val HASH = "hash"
        const val TIMESTAMP = "ts"
        const val ID = "id"
        const val OFFSET = "offset"
        const val LIMIT = "limit"

    }


}