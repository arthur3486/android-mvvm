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
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NavigationRes
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmActivity
import com.arthurivanets.mvvm.navigation.util.addExtras
import com.arthurivanets.mvvm.util.plus

/**
 * A base MVVM Activity with built-in support for Android X Navigation Concept.
 */
abstract class MvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes layoutId : Int) : MvvmActivity<VDB, VM>(layoutId) {
    
    
    /**
     * Used to obtain the exact id of the navigation graph to be used by this activity.
     *
     * @return the id of the navigation graph
     */
    @get:NavigationRes
    protected open val navigationGraphId : Int = 0
    
    /**
     * Override this property to specify a custom Start Destination.
     *
     * @return the exact id of the destination to be used as the starting one.
     */
    @get:IdRes
    protected open val navigationGraphStartDestination : Int = 0
    
    /**
     * Accesses the The NavController associated with the current activity.
     */
    protected val navController : NavController
        get() = findNavController(R.id.nav_host_fragment)
    
    /**
     * The initial input to be provided to the start destination fragment.
     */
    protected open val startDestinationInput : Bundle = Bundle()


    @CallSuper
    override fun init(savedInstanceState : Bundle?) {
        initNavigationGraph()
    }


    private fun initNavigationGraph() {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)?.navController?.apply {
            graph = navInflater.inflate(navigationGraphId).also {
                it.startDestination = (if(navigationGraphStartDestination != 0) navigationGraphStartDestination else it.startDestination)
                it.addExtras(extrasBundle + startDestinationInput)
            }
        }
    }


    /**
     * Navigates to the specified destination screen.
     *
     * @param destinationId the id of the destination screen (either the new Activity or Fragment)
     * @param extras the extra arguments to be passed to the destination screen
     */
    protected fun navigate(@IdRes destinationId : Int, extras : Bundle? = null) {
        navController.navigate(destinationId, extras)
    }
    
    
    /**
     * Navigates to the specified destination screen.
     *
     * @param directions the direction that leads to the destiantion screen.
     * @param navigationExtras
     */
    protected fun navigate(directions : NavDirections, navigationExtras : Navigator.Extras? = null) {
        navigationExtras?.let { navExtras ->
            navController.navigate(directions, navExtras)
        } ?: run {
            navController.navigate(directions)
        }
    }


    final override fun onSupportNavigateUp() : Boolean {
        return navController.navigateUp()
    }


}