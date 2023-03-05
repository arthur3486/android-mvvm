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

package com.arthurivanets.marvelapi.services

import com.arthurivanets.marvelapi.endpoints.EndpointPaths
import com.arthurivanets.marvelapi.responses.CharactersResponse
import com.arthurivanets.marvelapi.responses.ComicsResponse
import com.arthurivanets.marvelapi.responses.EventsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 */
internal interface EventsService {

    /**
     *
     */
    @GET(EndpointPaths.Events.LIST_SINGLE)
    fun getEventAsync(@Path(EndpointPaths.Params.ID) id: Long): Single<EventsResponse>

    /**
     *
     */
    @GET(EndpointPaths.Events.BASE)
    fun getEventsAsync(
        @Query(EndpointPaths.Params.OFFSET) offset: Int? = null,
        @Query(EndpointPaths.Params.LIMIT) limit: Int? = null
    ): Single<EventsResponse>

    /**
     *
     */
    @GET(EndpointPaths.Events.LIST_CHARACTERS)
    fun getEventCharactersAsync(
        @Path(EndpointPaths.Params.ID) eventId: Long,
        @Query(EndpointPaths.Params.OFFSET) offset: Int? = null,
        @Query(EndpointPaths.Params.LIMIT) limit: Int? = null
    ): Single<CharactersResponse>

    /**
     *
     */
    @GET(EndpointPaths.Events.LIST_COMICS)
    fun getEventComicsAsync(
        @Path(EndpointPaths.Params.ID) eventId: Long,
        @Query(EndpointPaths.Params.OFFSET) offset: Int? = null,
        @Query(EndpointPaths.Params.LIMIT) limit: Int? = null
    ): Single<ComicsResponse>

}
