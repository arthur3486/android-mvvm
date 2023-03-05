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

package com.arthurivanets.marvelapi.endpoints.events

import com.arthurivanets.marvelapi.endpoints.AbstractEndpoint
import com.arthurivanets.marvelapi.responses.CharactersResponse
import com.arthurivanets.marvelapi.responses.ComicsResponse
import com.arthurivanets.marvelapi.responses.EventsResponse
import com.arthurivanets.marvelapi.services.EventsService
import io.reactivex.Single

internal class EventsEndpointImpl(eventsService: EventsService) : AbstractEndpoint<EventsService>(eventsService),
    EventsEndpoint {

    override fun getEvent(id: Long): Single<EventsResponse> {
        return service.getEventAsync(id)
    }

    override fun getEvents(offset: Int, limit: Int): Single<EventsResponse> {
        return service.getEventsAsync(
            offset = offset,
            limit = limit
        )
    }

    override fun getEventCharacters(
        eventId: Long,
        offset: Int,
        limit: Int
    ): Single<CharactersResponse> {
        return service.getEventCharactersAsync(
            eventId = eventId,
            offset = offset,
            limit = limit
        )
    }

    override fun getEventComics(
        eventId: Long,
        offset: Int,
        limit: Int
    ): Single<ComicsResponse> {
        return service.getEventComicsAsync(
            eventId = eventId,
            offset = offset,
            limit = limit
        )
    }

}
