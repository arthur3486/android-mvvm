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

package com.arthurivanets.sample.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = Character.Properties.TABLE_NAME,
    primaryKeys = [Character.Properties.ID]
)
class Character(
    @ColumnInfo(name = Properties.ID) val id : Long,
    @ColumnInfo(name = Properties.NAME) val name : String,
    @ColumnInfo(name = Properties.DESCRIPTION) val description : String,
    @ColumnInfo(name = Properties.MODIFICATION_TIME) val modificationTimeInMillis : Long,
    @ColumnInfo(name = Properties.THUMBNAIL) val thumbnail : String,
    @ColumnInfo(name = Properties.URLS) val urls : String
) {


    object Properties {

        const val TABLE_NAME = "characters"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val MODIFICATION_TIME = "modification_time"
        const val THUMBNAIL = "thumbnail"
        const val URLS = "urls"

    }


}