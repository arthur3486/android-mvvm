package com.arthurivanets.mvvm.dagger

import androidx.databinding.ViewDataBinding
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * A base MVVM Activity with built-in support for the Dagger2-based dependency injection.
 */
abstract class MvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel> : MvvmActivity<VDB, VM>(), HasAndroidInjector {
    
    
    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>


    final override fun injectDependencies() {
        AndroidInjection.inject(this)
    }
    
    
    final override fun androidInjector() : AndroidInjector<Any> {
        return androidInjector
    }


}