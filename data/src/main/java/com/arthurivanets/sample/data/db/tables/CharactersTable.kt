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

package com.arthurivanets.sample.data.db.tables

import androidx.room.*
import com.arthurivanets.sample.data.db.entities.Character

@Dao
interface CharactersTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg characters: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(characters: List<Character>)

    @Update
    fun update(character: Character)

    @Update
    fun update(vararg characters: Character)

    @Update
    fun update(characters: List<Character>)

    @Delete
    fun delete(character: Character)

    @Delete
    fun delete(vararg characters: Character)

    @Delete
    fun delete(characters: List<Character>)

    @Query("SELECT * FROM ${Character.Properties.TABLE_NAME} WHERE ${Character.Properties.ID} = :id")
    fun getById(id: Long): Character?

    @Query("SELECT * FROM ${Character.Properties.TABLE_NAME} ORDER BY ${Character.Properties.ID} LIMIT :offset, :limit")
    fun get(offset: Int = 0, limit: Int = -1): List<Character>

}
