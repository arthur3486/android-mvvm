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
import com.arthurivanets.sample.data.db.entities.Comics

@Dao
interface ComicsTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(comics: Comics)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg comics: Comics)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(comics: List<Comics>)

    @Update
    fun update(comics: Comics)

    @Update
    fun update(vararg comics: Comics)

    @Update
    fun update(comics: List<Comics>)

    @Delete
    fun delete(comics: Comics)

    @Delete
    fun delete(vararg comics: Comics)

    @Delete
    fun delete(comics: List<Comics>)

    @Query("SELECT * FROM ${Comics.Properties.TABLE_NAME} WHERE ${Comics.Properties.ID} = :id")
    fun getById(id: Long): Comics?

    @Query("SELECT * FROM ${Comics.Properties.TABLE_NAME} ORDER BY ${Comics.Properties.ID} LIMIT :offset, :limit")
    fun get(offset: Int = 0, limit: Int = -1): List<Comics>

}
