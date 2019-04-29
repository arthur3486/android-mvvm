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

@file:JvmName("DataBindingExtensions")

package com.arthurivanets.mvvm.util

import androidx.databinding.Observable
import androidx.databinding.ObservableField


/**
 * Retrieves the value held by the [ObservableField].
 *
 * @param default the default value to be returned if the field value is not present
 * @return the associated field value if it's present, or the default one otherwise.
 */
fun <T : ObservableField<out R>, R : Any> T.get(default : R) : R {
    return (get() ?: default)
}


/**
 * Registers a new [Observable.OnPropertyChangedCallback] on a specified [Observable].
 *
 * @param callback property change callback
 * @return the registered [Observable.OnPropertyChangedCallback] callback.
 */
inline fun <T : Observable> T.onPropertyChanged(crossinline callback : (T) -> Unit) : Observable.OnPropertyChangedCallback {
    return object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender : Observable?, propertyId : Int) {
            callback(sender as T)
        }
    }.also(::addOnPropertyChangedCallback)
}