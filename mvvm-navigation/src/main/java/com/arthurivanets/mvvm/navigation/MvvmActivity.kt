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
import androidx.annotation.NavigationRes
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmActivity
import com.arthurivanets.mvvm.navigation.util.addExtras

/**
 * A base MVVM Activity with built-in support for Android X Navigation Concept.
 */
abstract class MvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel> : MvvmActivity<VDB, VM>() {


    @CallSuper
    override fun init(savedInstanceState : Bundle?) {
        initNavigationGraph()
    }


    private fun initNavigationGraph() {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)?.navController?.apply {
            graph = navInflater.inflate(getNavigationGraphId()).also {
                it.startDestination = (if(getNavigationGraphStartDestination() != 0) getNavigationGraphStartDestination() else it.startDestination)
                it.addExtras(extrasBundle)
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
        findNavController(R.id.nav_host_fragment).navigate(destinationId, extras)
    }


    final override fun onSupportNavigateUp() : Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }


    /**
     * Used to obtain the exact id of the navigation graph to be used by this activity.
     *
     * @return the id of the navigation graph
     */
    @NavigationRes
    protected abstract fun getNavigationGraphId() : Int


    /**
     * Override this method to specify a custom Start Destination.
     *
     * @return the exact id of the destination to be used as the starting one.
     */
    @IdRes
    protected open fun getNavigationGraphStartDestination() : Int {
        return 0
    }


}