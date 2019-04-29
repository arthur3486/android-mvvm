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

@file:JvmName("CollectionUtils")

package com.arthurivanets.sample.data.datastores.util

import com.arthurivanets.commons.ktx.clamp


/**
 * A "safer" alternative to [List.subList].
 */
internal fun <T> List<T>.take(offset : Int, limit : Int) : List<T> {
    require(offset >= 0) { "The Offset cannot be a negative number." }

    if(this.isEmpty() || (limit == 0)) {
        return emptyList()
    }

    require(offset < this.size) { "The Offset must be less than the size of the collection." }

    return this.subList(
        fromIndex = offset,
        toIndex = (if(limit > 0) (offset + limit - 1).clamp(0, this.size) else this.size)
    )
}