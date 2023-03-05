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

package com.arthurivanets.mvvm.markers

/**
 * A marker interface used to implement the [androidx.lifecycle.ViewModel] lifecycle.
 */
interface ViewModelLifecycle {

    /**
     * Gets called to notify about the fact that the View that owns the [androidx.lifecycle.ViewModel]
     * became visible and active.
     * <br>
     * (In most cases it's the right place to initiate the loading of the data).
     */
    fun onStart()

    /**
     * Gets called to notify about the fact that the View that owns the [androidx.lifecycle.ViewModel]
     * became invisible and inactive.
     */
    fun onStop()

}
