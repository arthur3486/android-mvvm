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

@file:JvmName("EventMappingUtils")

package com.arthurivanets.sample.domain.repositories.events

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.domain.repositories.util.toDomainCreators
import com.arthurivanets.sample.domain.repositories.util.toDomainImage
import com.arthurivanets.sample.domain.repositories.util.toDomainUrls
import com.arthurivanets.sample.domain.repositories.util.toResponse
import com.arthurivanets.sample.domain.util.DataEvent
import com.arthurivanets.sample.domain.util.DomainEvent

internal fun Response<DataEvent, Throwable>.toDomainEventResponse(): Response<DomainEvent, Throwable> {
    return this.toResponse { it.toDomainEvent() }
}

internal fun Response<List<DataEvent>, Throwable>.toDomainEventsResponse(sortItems: Boolean = false): Response<List<DomainEvent>, Throwable> {
    return this.toResponse { it.toDomainEvents(sortItems) }
}

internal fun Collection<DataEvent>.toDomainEvents(sort: Boolean = false): List<DomainEvent> {
    return this.map { it.toDomainEvent() }
        .let { events -> (if (sort) events.sortedBy { it.id } else events) }
}

internal fun DataEvent.toDomainEvent(): DomainEvent {
    return DomainEvent(
        id = this.id,
        title = this.title,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        startDateInMillis = this.startDateInMillis,
        endDateInMillis = this.endDateInMillis,
        thumbnail = this.thumbnail.toDomainImage(),
        urls = this.urls.toDomainUrls(),
        creators = this.creators.toDomainCreators()
    )
}
