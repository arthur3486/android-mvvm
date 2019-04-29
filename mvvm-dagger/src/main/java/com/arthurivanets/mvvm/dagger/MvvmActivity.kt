package com.arthurivanets.mvvm.dagger

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.arthurivanets.dagger.androidx.AndroidXHasSupportFragmentInjector
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

/**
 * A base MVVM Activity with built-in support for the Dagger2-based dependency injection.
 */
abstract class MvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel> : MvvmActivity<VDB, VM>(), AndroidXHasSupportFragmentInjector {


    @Inject
    lateinit var fragmentDispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>


    final override fun injectDependencies() {
        AndroidInjection.inject(this)
    }


    final override fun supportFragmentInjector() : AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }


}