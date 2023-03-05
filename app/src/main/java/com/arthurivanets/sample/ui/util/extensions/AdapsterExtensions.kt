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

@file:JvmName("AdapsterUtils")

package com.arthurivanets.sample.ui.util.extensions

import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.listeners.OnItemLongClickListener

inline fun <T> onItemClick(crossinline action: (T) -> Unit): OnItemClickListener<T> {
    return OnItemClickListener { _, item, _ -> action(item) }
}

inline fun <T> onItemLongClick(crossinline action: (T) -> Boolean): OnItemLongClickListener<T> {
    return OnItemLongClickListener { _, item, _ -> action(item) }
}
