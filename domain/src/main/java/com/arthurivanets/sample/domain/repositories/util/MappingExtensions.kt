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

@file:JvmName("MappingUtils")

package com.arthurivanets.sample.domain.repositories.util

import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.commons.rx.ktx.asFlowable
import com.arthurivanets.commons.rx.ktx.asSingle
import io.reactivex.Flowable
import io.reactivex.Single


internal inline fun <T, R> Response<T, Throwable>.toResponse(resultMapper : (T) -> R) : Response<R, Throwable> {
    return Response(
        result = this.result?.let { resultMapper(it) },
        error = this.error
    )
}


internal inline fun <T, R> Single<Response<T, Throwable>>.convertResponse(crossinline resultMapper : (Response<T, Throwable>) -> Response<R, Throwable>) : Single<Response<R, Throwable>> {
    return this.flatMap { resultMapper(it).asSingle() }
}


internal inline fun <T, R> Flowable<Response<T, Throwable>>.convertResponse(crossinline resultMapper : (Response<T, Throwable>) -> Response<R, Throwable>) : Flowable<Response<R, Throwable>> {
    return this.flatMap { resultMapper(it).asFlowable() }
}