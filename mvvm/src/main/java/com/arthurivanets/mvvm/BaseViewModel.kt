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

import com.arthurivanets.mvvm.events.ViewModelEvent
import com.arthurivanets.mvvm.markers.CanHandleBackPressEvents
import com.arthurivanets.mvvm.markers.CanManageState
import com.arthurivanets.mvvm.markers.ViewModelLifecycle
import com.arthurivanets.rxbus.EventSource

/**
 * A base contract used to implement the concrete version of the View Model.
 */
interface BaseViewModel : ViewModelLifecycle, CanManageState, CanHandleBackPressEvents, EventSource<ViewModelEvent<*>>