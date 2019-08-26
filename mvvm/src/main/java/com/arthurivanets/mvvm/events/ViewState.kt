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

package com.arthurivanets.mvvm.events

import com.arthurivanets.rxbus.BusEvent

/**
 * A base class to be used for the concrete implementations of the custom View States.
 *
 * (Common View States include such states as Idle, Loading, Success, Error, etc.)
 *
 * @param T the type of the state payload
 */
abstract class ViewState<T>(payload : T? = null) : BusEvent<T>(payload)