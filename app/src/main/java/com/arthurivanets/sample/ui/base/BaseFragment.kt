package com.arthurivanets.sample.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class BaseFragment(@LayoutRes layoutId: Int) : BaseMvvmFragment<ViewDataBinding, StubViewModel>(layoutId) {

    override val bindingVariable = 0
    override val isDataBindingEnabled = false

}
