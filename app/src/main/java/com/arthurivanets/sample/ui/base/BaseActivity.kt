package com.arthurivanets.sample.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class BaseActivity(@LayoutRes layoutId : Int) : BaseMvvmActivity<ViewDataBinding, StubViewModel>(layoutId) {
    
    
    override val bindingVariable = 0
    override val isDataBindingEnabled = false
    
    
}