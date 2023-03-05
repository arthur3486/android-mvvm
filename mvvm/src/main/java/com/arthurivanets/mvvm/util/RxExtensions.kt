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

@file:JvmName("RxExtensions")

package com.arthurivanets.mvvm.util

import io.reactivex.functions.Consumer

/**
 * Converts the specified [Consumer] of the items of type [T] into a
 * [Consumer] of the items of type [R], provided that the type [R] inherits the type [T].
 *
 * @return the new [Consumer]
 */
internal fun <T, R : T> Consumer<T>.adapt(): Consumer<R> {
    return Consumer(::accept)
}
