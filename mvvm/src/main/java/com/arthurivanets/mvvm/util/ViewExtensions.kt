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

@file:JvmName("ViewUtils")

package com.arthurivanets.mvvm.util

import android.graphics.Paint
import android.view.View


/**
 * Sets the [View.LAYER_TYPE_NONE] as a backing layer for the specified [View].
 *
 * @param paint
 */
internal fun View.useNoLayer(paint : Paint? = null) {
    setLayerType(View.LAYER_TYPE_NONE, paint)
}


/**
 * Sets the [View.LAYER_TYPE_HARDWARE] as a backing layer for the specified [View].
 *
 * @param paint
 */
internal fun View.useHardwareLayer(paint : Paint? = null) {
    setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}


/**
 * Shows the system software keyboard.
 *
 * @param requestFocus whether to request the focus on the specified [View].
 */
internal fun View.showKeyboard(requestFocus : Boolean = true) {
    if(requestFocus) {
        requestFocus()
    }

    this.context.showKeyboard(this)
}


/**
 * Hides the system software keyboard.
 *
 * @param clearFocus whether to remove the focus from the input field
 */
internal fun View.hideKeyboard(clearFocus : Boolean = true) {
    if(clearFocus) {
        clearFocus()
    }

    this.context.hideKeyboard(this)
}


/**
 * Cancels all the currently active animations associated with the specified [View].
 */
internal fun View.cancelActiveAnimations() {
    clearAnimation()
    animate().cancel()
}