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

package com.arthurivanets.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * A concrete [ViewModel] factory used to control the exact details of the creation of the appropriate [ViewModel]s.
 * <br>
 * Allows you to construct the [ViewModel] that meets your requirements (e.g. perform the dependency injection into your [ViewModel]).
 */
class ViewModelProviderFactory<V : ViewModel>(
    private val viewModelClass : Class<V>,
    private val creator : () -> V
) : ViewModelProvider.Factory {
    
    
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass : Class<T>) : T {
        if(!modelClass.isAssignableFrom(viewModelClass)) {
            throw IllegalArgumentException("Unsupported class name.")
        }
        
        return (creator() as T)
    }
    
    
}