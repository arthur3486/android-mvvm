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

package com.arthurivanets.sample.ui.base

import com.arthurivanets.mvvm.markers.ViewState

sealed class GeneralViewStates<T>(val payload: T? = null) : ViewState {

    class Idle<T>(payload: T? = null) : GeneralViewStates<T>(payload)

    class Loading<T>(payload: T? = null) : GeneralViewStates<T>(payload)

    class Success<T>(payload: T? = null) : GeneralViewStates<T>(payload)

    class Error<T>(payload: T? = null) : GeneralViewStates<T>(payload)

}
