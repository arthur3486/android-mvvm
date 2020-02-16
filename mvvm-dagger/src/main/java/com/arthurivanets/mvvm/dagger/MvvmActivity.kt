package com.arthurivanets.mvvm.dagger

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * A base MVVM Activity with built-in support for the Dagger2-based dependency injection.
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