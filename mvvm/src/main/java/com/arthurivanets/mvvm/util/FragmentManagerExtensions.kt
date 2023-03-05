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

@file:JvmName("FragmentManagerUtils")

package com.arthurivanets.mvvm.util

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arthurivanets.mvvm.markers.CanFetchExtras
import com.arthurivanets.mvvm.markers.CanHandleBackPressEvents
import com.arthurivanets.mvvm.markers.CanHandleNewIntent

/**
 * Retrieves the currently visible [Fragment] contained
 * by the specified [FragmentManager] (if there's any).
 */
val FragmentManager.currentFragment: Fragment?
    get() {
        for (fragment in this.fragments) {
            if (fragment?.isVisible == true) {
                return fragment
            }
        }

        return null
    }

/**
 * Propagates the extras for further handling to the specified [Fragment]
 * only if it (the specified [Fragment]) can handle the specified [Bundle] of arguments
 * (if it (the specified [Fragment]) implements the [CanFetchExtras] interface)
 *
 * @param extras the [Bundle] of arguments
 */
fun Fragment.handleExtras(extras: Bundle) {
    if (this is CanFetchExtras) {
        this.fetchExtras(extras)
    }
}

/**
 * Propagates the new [Intent] for further handling to the specified [Fragment]
 * only if it (the specified [Fragment]) can handle the specified [Intent]
 * (if it (the specified [Fragment]) implements the [CanHandleNewIntent] interface)
 *
 * @param intent the newly arrived [Intent]
 */
fun Fragment.handleNewIntent(intent: Intent) {
    if (this is CanHandleNewIntent) {
        this.handleNewIntent(intent)
    }
}

/**
 * Propagates the Back Press Event for further handling to the specified [Fragment]
 * only if it (the specified [Fragment]) can handle the Back Press Event
 * (if it (the specified [Fragment]) implements the [CanHandleBackPressEvents] interface)
 *
 * @return whether the Back Press Event has been consumed or not (see: [CanHandleBackPressEvents.onBackPressed])
 */
fun Fragment.handleBackPressEvent(): Boolean {
    return ((this is CanHandleBackPressEvents) && this.onBackPressed())
}

/**
 * Applies the [handleExtras] method to each of the [Fragment]s
 * present in the specified [Collection].
 *
 * @param extras the [Bundle] of arguments
 */
fun Collection<Fragment>.handleExtras(extras: Bundle) {
    this.forEach { it.handleExtras(extras) }
}

/**
 * Applies the [handleNewIntent] method to each of the [Fragment]s
 * present in the specified [Collection].
 *
 * @param intent the newly arrived [Intent]
 */
fun Collection<Fragment>.handleNewIntent(intent: Intent) {
    this.forEach { it.handleNewIntent(intent) }
}

/**
 * Applies the [handleBackPressEvent] method to each of the [Fragment]s
 * present in the specified [Collection].
 *
 * @return whether the Back Press Event has been consumed or not (see: [CanHandleBackPressEvents.onBackPressed])
 */
fun Collection<Fragment>.handleBackPressEvent(): Boolean {
    for (fragment in this) {
        if (fragment.handleBackPressEvent()) {
            return true
        }
    }

    return false
}
