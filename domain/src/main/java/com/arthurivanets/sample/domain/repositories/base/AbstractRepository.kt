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

package com.arthurivanets.sample.domain.repositories.base

import com.arthurivanets.commons.rx.ktx.applyIOWorkSchedulers
import com.arthurivanets.commons.rx.schedulers.SchedulerProvider
import com.arthurivanets.rxbus.BusEvent
import com.arthurivanets.rxbus.RxBusFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

abstract class AbstractRepository<ET : BusEvent<*>>(
    private val schedulerProvider: SchedulerProvider
) : Repository<ET> {

    private val eventBus = RxBusFactory.create(PublishSubject.create())

    protected fun postEvent(event: ET) {
        this.eventBus.post(event)
    }

    @Suppress("UNCHECKED_CAST")
    override fun subscribe(eventConsumer: Consumer<ET>): Disposable {
        return this.eventBus.register(
            BusEvent::class.java,
            Consumer { eventConsumer.accept(it as ET) }
        )
    }

    /**
     *
     */
    protected fun <T> Flowable<T>.withAppropriateSchedulers(): Flowable<T> {
        return this.applyIOWorkSchedulers(schedulerProvider)
    }

    /**
     *
     */
    protected fun <T> Single<T>.withAppropriateSchedulers(): Single<T> {
        return this.applyIOWorkSchedulers(schedulerProvider)
    }

    /**
     *
     */
    protected fun <T> Maybe<T>.withAppropriateSchedulers(): Maybe<T> {
        return this.applyIOWorkSchedulers(schedulerProvider)
    }

    /**
     *
     */
    protected fun Completable.withAppropriateSchedulers(): Completable {
        return this.applyIOWorkSchedulers(schedulerProvider)
    }

}
