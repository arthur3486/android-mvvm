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

package com.arthurivanets.sample.domain.repositories.events

import com.arthurivanets.commons.data.repository.Repository
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.domain.util.DomainCharacter
import com.arthurivanets.sample.domain.util.DomainComics
import com.arthurivanets.sample.domain.util.DomainEvent
import io.reactivex.Flowable
import io.reactivex.Single

interface EventsRepository : Repository {

    fun getEvent(id : Long) : Single<Response<DomainEvent, Throwable>>

    fun getEvents(offset : Int, limit : Int) : Single<Response<List<DomainEvent>, Throwable>>

    fun getAllEvents(offset : Int, limit : Int) : Flowable<Response<List<DomainEvent>, Throwable>>

    fun getEventCharacters(event : DomainEvent,
                           offset : Int,
                           limit : Int) : Single<Response<List<DomainCharacter>, Throwable>>
    
    fun getEventComics(event : DomainEvent,
                       offset : Int,
                       limit : Int) : Single<Response<List<DomainComics>, Throwable>>

}