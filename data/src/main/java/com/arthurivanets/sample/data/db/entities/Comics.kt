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
    tableName = Comics.Properties.TABLE_NAME,
    primaryKeys = [Comics.Properties.ID]
)
class Comics(
    @ColumnInfo(name = Properties.ID) val id: Long,
    @ColumnInfo(name = Properties.DIGITAL_ID) val digitalId: Long,
    @ColumnInfo(name = Properties.TITLE) val title: String,
    @ColumnInfo(name = Properties.ISSUE_NUMBER) val issueNumber: Double,
    @ColumnInfo(name = Properties.VARIANT_DESCRIPTION) val variantDescription: String,
    @ColumnInfo(name = Properties.DESCRIPTION) val description: String,
    @ColumnInfo(name = Properties.MODIFICATION_TIME) val modificationTimeInMillis: Long,
    @ColumnInfo(name = Properties.ISBN) val isbn: String,
    @ColumnInfo(name = Properties.UPC) val upc: String,
    @ColumnInfo(name = Properties.DIAMOND_CODE) val diamondCode: String,
    @ColumnInfo(name = Properties.EAN) val ean: String,
    @ColumnInfo(name = Properties.ISSN) val issn: String,
    @ColumnInfo(name = Properties.FORMAT) val format: String,
    @ColumnInfo(name = Properties.PAGE_COUNT) val pageCount: Int,
    @ColumnInfo(name = Properties.THUMBNAIL) val thumbnail: String,
    @ColumnInfo(name = Properties.IMAGES) val images: String,
    @ColumnInfo(name = Properties.CREATORS) val creators: String,
    @ColumnInfo(name = Properties.URLS) val urls: String
) {

    object Properties {

        const val TABLE_NAME = "comics"
        const val ID = "id"
        const val DIGITAL_ID = "digital_id"
        const val TITLE = "title"
        const val ISSUE_NUMBER = "issue_number"
        const val VARIANT_DESCRIPTION = "variant_description"
        const val DESCRIPTION = "description"
        const val MODIFICATION_TIME = "modification_time"
        const val ISBN = "isbn"
        const val UPC = "upc"
        const val DIAMOND_CODE = "diamond_code"
        const val EAN = "ean"
        const val ISSN = "issn"
        const val FORMAT = "format"
        const val PAGE_COUNT = "page_count"
        const val THUMBNAIL = "thumbnail"
        const val IMAGES = "images"
        const val CREATORS = "creators"
        const val URLS = "urls"

    }

}
