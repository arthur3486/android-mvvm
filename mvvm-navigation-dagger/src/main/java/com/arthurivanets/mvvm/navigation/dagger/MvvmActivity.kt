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

package com.arthurivanets.mvvm.navigation.dagger

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.navigation.MvvmActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * A base MVVM Activity with built-in support for the Dagger2-based dependency injection and
 * Android X Navigation Concept.
 */
abstract class MvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes layoutId : Int) : MvvmActivity<VDB, VM>(layoutId) {
    
    
    @Inject
    lateinit var viewModel : VM


    final override fun injectDependencies() {
        AndroidInjection.inject(this)
    }
    
    
    final override fun createViewModel() : VM {
        return viewModel
    }
    
    
}