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

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.navigation.fragment.NavHostFragment
import com.arthurivanets.mvvm.markers.CanFetchExtras
import com.arthurivanets.mvvm.markers.CanHandleBackPressEvents
import com.arthurivanets.mvvm.markers.CanHandleNewIntent
import com.arthurivanets.mvvm.util.handleBackPressEvent
import com.arthurivanets.mvvm.util.handleExtras
import com.arthurivanets.mvvm.util.handleNewIntent

/**
 * [NavHostFragment]-based fragment which supports the handling and further propagation of the common [MvvmActivity] events.
 * <br>
 * To be used as a Host Fragment for when you rely on the [MvvmFragment]s.
 * <br>
 * When you include this fragment in your layout file you should give it the appropriate id ([R.id.nav_host_fragment])
 */
open class MvvmNavHostFragment : NavHostFragment(), CanHandleNewIntent, CanFetchExtras, CanHandleBackPressEvents {

    final override fun handleNewIntent(intent: Intent) {
        childFragmentManager.fragments.handleNewIntent(intent)
    }

    final override fun fetchExtras(extras: Bundle) {
        childFragmentManager.fragments.handleExtras(extras)
    }

    @CallSuper
    override fun onBackPressed(): Boolean {
        return childFragmentManager.fragments.handleBackPressEvent()
    }

}
