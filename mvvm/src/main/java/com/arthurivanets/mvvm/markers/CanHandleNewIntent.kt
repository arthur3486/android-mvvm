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

package com.arthurivanets.mvvm.markers

import android.content.Intent

/**
 * A marker interface used to indicate the support for the handling of the new message [Intent]s passed to a particular [Activity].
 */
interface CanHandleNewIntent {

    /**
     * Processes the specified message [Intent].
     *
     * @param intent the message intent
     */
    fun handleNewIntent(intent: Intent)

}
