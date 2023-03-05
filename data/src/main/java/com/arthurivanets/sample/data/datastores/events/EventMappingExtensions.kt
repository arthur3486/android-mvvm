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

@file:JvmName("EventMappingUtils")

package com.arthurivanets.sample.data.datastores.events

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.datastores.util.*
import com.arthurivanets.sample.data.util.*

internal fun com.arthurivanets.marvelapi.responses.EventsResponse.toSingleItemResponse(): Response<DataEvent, Throwable> {
    return this.toSingleItemResponse { it.toDataEvent() }
}

internal fun com.arthurivanets.marvelapi.responses.EventsResponse.toResponse(): Response<List<DataEvent>, Throwable> {
    return this.toResponse { it.toDataEvents() }
}

internal fun Collection<ApiEvent>.toDataEvents(): List<DataEvent> {
    return this.map { it.toDataEvent() }
}

internal fun Collection<DatabaseEvent>.fromDatabaseToDataEvents(): List<DataEvent> {
    return this.map { it.toDataEvent() }
}

internal fun Collection<DataEvent>.toDatabaseEvents(): List<DatabaseEvent> {
    return this.map { it.toDatabaseEvent() }
}

internal fun ApiEvent.toDataEvent(): DataEvent {
    return DataEvent(
        id = this.id,
        title = this.title,
        description = this.description,
        modificationTimeInMillis = this.modified.toMillisFromGeneralTime(),
        startDateInMillis = (this.startDate?.toMillisFromTimePeriodTime() ?: 0L),
        endDateInMillis = (this.endDate?.toMillisFromTimePeriodTime() ?: 0L),
        thumbnail = this.thumbnail.toDataImage(),
        urls = this.urls.toDataUrls(),
        creators = this.creators.items.toDataCreators()
    )
}

internal fun DatabaseEvent.toDataEvent(): DataEvent {
    return DataEvent(
        id = this.id,
        title = this.title,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        startDateInMillis = this.startDateInMillis,
        endDateInMillis = this.endDateInMillis,
        thumbnail = this.thumbnail.toObject(DataImage::class.java),
        urls = this.urls.toList(DataUrl::class.java),
        creators = this.creators.toList(DataCreator::class.java)
    )
}

internal fun DataEvent.toDatabaseEvent(): DatabaseEvent {
    return DatabaseEvent(
        id = this.id,
        title = this.title,
        description = this.description,
        modificationTimeInMillis = this.modificationTimeInMillis,
        startDateInMillis = this.startDateInMillis,
        endDateInMillis = this.endDateInMillis,
        thumbnail = this.thumbnail.toJsonString(),
        urls = this.urls.toJsonString(),
        creators = this.creators.toJsonString()
    )
}
