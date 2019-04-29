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

/**
 * A set of the general [ViewModelEvent]s handling of which is supported by the [com.arthurivanets.mvvm.MvvmActivity]
 * and [com.arthurivanets.mvvm.MvvmFragment] out of the box.
 */
sealed class GeneralViewModelEvents<T>(data : T? = null) : ViewModelEvent<T>(data) {

    /**
     * Requests the software keyboard to be hidden.
     *
     * @param clearFocus whether to clear the focus from the input field
     */
    class HideKeyboard(clearFocus : Boolean = true) : GeneralViewModelEvents<Boolean>(clearFocus)

    /**
     * Requests the Back Press Event to be performed.
     */
    class ConfirmBackButtonPress : GeneralViewModelEvents<Unit>()

    /**
     * Requests the currently active Activity to be finished.
     */
    class FinishActivity : GeneralViewModelEvents<Unit>()

}