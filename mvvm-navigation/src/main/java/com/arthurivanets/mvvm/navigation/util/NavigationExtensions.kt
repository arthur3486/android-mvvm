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

@file:JvmName("NavigationUtils")

package com.arthurivanets.mvvm.navigation.util

import android.os.Bundle
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraph


/**
 * Determines if the current Fragment Page is the start destination
 * (according to the associated navigation graph).
 */
val NavController.isOnStartDestination : Boolean
    get() = (this.currentDestination?.id == this.graph.startDestination)


/**
 * Extracts all the arguments present in the specified [Bundle]
 * and adds them to the specified [NavGraph].
 *
 * @param extras a bundle of arguments
 */
internal fun NavGraph.addExtras(extras : Bundle) {
    for(key in extras.keySet()) {
        this.addArgument(key, newNavArgument(extras.get(key)))
    }
}


private fun newNavArgument(value : Any?) : NavArgument {
    return NavArgument.Builder()
        .setDefaultValue(value)
        .setIsNullable(value == null)
        .build()
}