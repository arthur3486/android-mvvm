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

package com.arthurivanets.mvvm.util

import io.reactivex.disposables.Disposable

/**
 * A class used for the management of multiple [Disposable]s based on their keys.
 */
internal class CompositeMapDisposable<K : Any> : Disposable {


    private val disposablesMap = HashMap<K, Disposable>()

    val size : Int
        get() = synchronized(this) { disposablesMap.size }

    @Volatile
    private var isDisposed = false


    operator fun set(key : K, disposable : Disposable) = synchronized(this) {
        if(!isDisposed) {
            disposablesMap[key] = disposable
        }
    }


    operator fun get(key : K) : Disposable? = synchronized(this) {
        return disposablesMap[key]
    }


    fun remove(key : K) : Disposable? = synchronized(this) {
        return disposablesMap.remove(key)
            ?.also(Disposable::dispose)
    }


    fun clear() = synchronized(this) {
        if(!isDisposed) {
            disposablesMap.values.forEach(Disposable::dispose)
            disposablesMap.clear()
        }
    }


    override fun dispose() = synchronized(this) {
        if(!isDisposed) {
            clear()

            isDisposed = true
        }
    }


    override fun isDisposed() : Boolean {
        return isDisposed
    }


}