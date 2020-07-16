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

import androidx.lifecycle.LiveData
import com.arthurivanets.mvvm.markers.*

/**
 * A base contract used to implement the concrete version of the View Model.
 */
interface BaseViewModel : ViewModelLifecycle, CanManageState, CanHandleBackPressEvents {
    
    /**
     * An [Channel] that the ViewModel emits the [Command]s through.
     * (The actual handling of the emitted [Command]s is completely up to the subscribing side (i.e. Activity, Fragment, View))
     */
    val commandChannel : Channel<Command>
    
    /**
     * An [Channel] that the ViewModel emits the [Route]s through.
     * (The actual handling of the emitted [Route]s is completely up to the subscribing side (i.e. Activity, Fragment, View))
     */
    val routeChannel : Channel<Route>
    
    /**
     * An [LiveData] that the ViewModel notifies about [ViewState] changes through.
     * (The actual handling of the [ViewState] changes is completely up to the subscribing side (i.e. Activity, Fragment, View))
     */
    val viewStateHolder : LiveData<ViewState>
    
}