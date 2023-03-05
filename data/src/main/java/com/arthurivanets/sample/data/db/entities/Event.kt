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

package com.arthurivanets.sample.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = Event.Properties.TABLE_NAME,
    primaryKeys = [Event.Properties.ID]
)
class Event(
    @ColumnInfo(name = Properties.ID) val id: Long,
    @ColumnInfo(name = Properties.TITLE) val title: String,
    @ColumnInfo(name = Properties.DESCRIPTION) val description: String,
    @ColumnInfo(name = Properties.MODIFICATION_TIME) val modificationTimeInMillis: Long,
    @ColumnInfo(name = Properties.START_DATE) val startDateInMillis: Long,
    @ColumnInfo(name = Properties.END_DATE) val endDateInMillis: Long,
    @ColumnInfo(name = Properties.THUMBNAIL) val thumbnail: String,
    @ColumnInfo(name = Properties.URLS) val urls: String,
    @ColumnInfo(name = Properties.CREATORS) val creators: String
) {

    object Properties {

        const val TABLE_NAME = "events"
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val MODIFICATION_TIME = "modification_time"
        const val START_DATE = "start_date"
        const val END_DATE = "end_date"
        const val THUMBNAIL = "thumbnail"
        const val URLS = "urls"
        const val CREATORS = "creators"

    }

}
