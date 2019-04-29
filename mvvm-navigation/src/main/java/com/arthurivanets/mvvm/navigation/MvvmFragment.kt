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

package com.arthurivanets.mvvm.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmFragment

/**
 * A base MVVM Fragment with built-in support for Android X Navigation Concept.
 */
abstract class MvvmFragment<VDB : ViewDataBinding, VM : BaseViewModel> : MvvmFragment<VDB, VM>() {


    /**
     * Navigates to the specified destination screen.
     *
     * @param destinationId the id of the destination screen (either the new Activity or Fragment)
     * @param extras the extra arguments to be passed to the destination screen
     * @param navigationExtras
     */
    protected fun navigate(@IdRes destinationId : Int,
                           extras : Bundle? = null,
                           navigationExtras : Navigator.Extras? = null) {
        findNavController().navigate(
            destinationId,
            extras,
            null,
            navigationExtras
        )
    }
    
    
    /**
     * Navigates back (pops the back stack) to the previous [MvvmFragment] on the stack.
     */
    protected fun navigateBack() {
        findNavController().popBackStack()
    }


}