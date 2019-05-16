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

@file:JvmName("ViewModelProviderUtils")

package com.arthurivanets.mvvm.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.arthurivanets.mvvm.ViewModelProviderFactory


/**
 * Provides the [ViewModel] of the specified class using the [ViewModelProviders] and a custom [ViewModelProviderFactory],
 * thus allowing you to construct a [ViewModel] according to your requirements (e.g. inject the dependencies into it, etc.).
 * The creation of a new [ViewModel] instance using the [creator] will happen only if the [ViewModelProviders] doesn't have
 * a retained version of the corresponding [ViewModel] instance, otherwise the existing (retained) [ViewModel] will be reused.
 */
fun <T : ViewModel> FragmentActivity.provideViewModel(viewModelClass : Class<T>, creator : () -> T) : T {
    val viewModelFactory = ViewModelProviderFactory(viewModelClass, creator)
    return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
}


/**
 * Provides the [ViewModel] of the specified class using the [ViewModelProviders] and a custom [ViewModelProviderFactory],
 * thus allowing you to construct a [ViewModel] according to your requirements (e.g. inject the dependencies into it, etc.).
 * The creation of a new [ViewModel] instance using the [creator] will happen only if the [ViewModelProviders] doesn't have
 * a retained version of the corresponding [ViewModel] instance, otherwise the existing (retained) [ViewModel] will be reused.
 */
fun <T : ViewModel> Fragment.provideViewModel(viewModelClass : Class<T>, creator : () -> T) : T {
    val viewModelFactory = ViewModelProviderFactory(viewModelClass, creator)
    return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
}