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

package com.arthurivanets.sample.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arthurivanets.commons.SynchronizedSingletonHolder
import com.arthurivanets.sample.data.db.entities.Character
import com.arthurivanets.sample.data.db.entities.Comics
import com.arthurivanets.sample.data.db.entities.Event
import com.arthurivanets.sample.data.db.tables.CharactersTable
import com.arthurivanets.sample.data.db.tables.ComicsTable
import com.arthurivanets.sample.data.db.tables.EventsTable

@androidx.room.Database(
    entities = [
        Character::class,
        Comics::class,
        Event::class
    ],
    version = Constants.DATABASE_VERSION
)
abstract class Database : RoomDatabase() {

    companion object : SynchronizedSingletonHolder<Database, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            Database::class.java,
            Constants.DATABASE_NAME
        )
            .addMigrations(*Constants.MIGRATIONS)
            .build()
    })

    abstract fun charactersTable(): CharactersTable

    abstract fun comicsTable(): ComicsTable

    abstract fun eventsTable(): EventsTable

}
