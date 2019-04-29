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

package com.arthurivanets.sample.data.db.tables

import androidx.room.*
import com.arthurivanets.sample.data.db.entities.Event

@Dao
interface EventsTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(event : Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg events : Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(events : List<Event>)

    @Update
    fun update(event : Event)

    @Update
    fun update(vararg event : Event)

    @Update
    fun update(events : List<Event>)

    @Delete
    fun delete(event : Event)

    @Delete
    fun delete(vararg events : Event)

    @Delete
    fun delete(events : List<Event>)

    @Query("SELECT * FROM ${Event.Properties.TABLE_NAME} WHERE ${Event.Properties.ID} = :id")
    fun getById(id : Long) : Event?

    @Query("SELECT * FROM ${Event.Properties.TABLE_NAME} ORDER BY ${Event.Properties.ID} LIMIT :offset, :limit")
    fun get(offset : Int = 0, limit : Int = -1) : List<Event>

}