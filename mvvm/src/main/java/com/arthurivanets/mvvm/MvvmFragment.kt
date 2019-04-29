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

package com.arthurivanets.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.arthurivanets.mvvm.events.GeneralViewModelEvents
import com.arthurivanets.mvvm.events.ViewModelEvent
import com.arthurivanets.mvvm.listeners.AnimationListenerAdapter
import com.arthurivanets.mvvm.markers.CanFetchExtras
import com.arthurivanets.mvvm.markers.CanHandleBackPressEvents
import com.arthurivanets.mvvm.markers.CanHandleNewIntent
import com.arthurivanets.mvvm.util.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * A base MVVM Fragment to be used in conjunction with the concrete implementations of the [BaseViewModel].
 */
abstract class MvvmFragment<VDB : ViewDataBinding, VM : BaseViewModel> : Fragment(), CanFetchExtras, CanHandleNewIntent, CanHandleBackPressEvents {
    
    
    /**
     * The content [View] of the current [MvvmFragment].
     * (might be null if the current [MvvmFragment] hasn't been initialized yet.)
     */
    var rootView : View? = null
        private set

    private var viewDataBinding : VDB? = null
    private var viewModel : VM? = null

    private val eventConsumerDisposables = CompositeDisposable()
    private val registeredObservables = HashSet<Pair<Observable.OnPropertyChangedCallback, Observable>>()
    
    /**
     * Indicates whether the current [MvvmFragment]'s content view is initialized or not.
     */
    var isViewCreated = false
        private set
    
    /**
     * Indicates whether the current [MvvmFragment] is being animated or not.
     */
    var isViewAnimating = false
        private set
    
    /**
     * Hint provided by the app that this fragment is currently visible to the user, as well as "active".
     * (This is usually set manually (e.g. when using the [androidx.viewpager.widget.ViewPager]) to indicate that the "Page" is active
     * and ready to load data or do something useful)
     */
    var isActive = true
        private set


    final override fun onCreate(savedInstanceState : Bundle?) {
        // dependencies will be injected only once (based on the state of the content view)
        if(!isViewCreated) {
            injectDependencies()
        }
        
        super.onCreate(savedInstanceState)
        
        // the overall initialization, extras fetching and post initialization will be performed only once, too
        if(!isViewCreated) {
            initViewModel()
            arguments?.let(::fetchExtras)
            preInit()
        }
    }


    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        if(!isViewCreated) {
            viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            rootView = viewDataBinding?.root
        }
        
        viewDataBinding?.setVariable(getBindingVariable(), viewModel)
        viewDataBinding?.lifecycleOwner = this

        return rootView
    }


    final override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wasViewCreated = isViewCreated
        isViewCreated = true

        // performing the initialization only in cases when the view was created for the first time
        if(!wasViewCreated) {
            init(savedInstanceState)
            postInit()
        }
        
        performDataBinding()
        
        // performing the state restoring only in cases when the view was created for the first time
        // (otherwise there's no need to restore the state, as the current view already holds the most recent state)
        if(!wasViewCreated) {
            savedInstanceState?.let(::onRestoreStateInternal)
        }
    }


    /**
     * Gets called when it's the right time for you to inject the dependencies.
     */
    open fun injectDependencies() {
        //
    }


    /**
     * Gets called right before the pre-initialization stage ([preInit] method call),
     * if the [Bundle] received from the [onViewCreated] is not null.
     *
     * @param extras the bundle of arguments
     */
    @CallSuper
    override fun fetchExtras(extras : Bundle) {
        //
    }


    @CallSuper
    override fun handleNewIntent(intent : Intent) {
        //
    }


    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit() {
        //
    }


    private fun initViewModel() {
        viewModel = getViewModel()
    }


    /**
     * Get's called when it's the right time for you to initialize the UI elements.
     *
     * @param savedInstanceState the state bundle brought from the [Fragment.onViewCreated]
     */
    protected open fun init(savedInstanceState : Bundle?) {
        //
    }


    /**
     * Gets called right after the UI initialization.
     */
    protected open fun postInit() {
        //
    }


    /**
     * Executes the pending Data Binding operations.
     */
    @CallSuper
    protected open fun performDataBinding() {
        viewDataBinding?.executePendingBindings()
    }
    
    
    /**
     * Looks up the [View] for the specified viewId within the current view hierarchy.
     *
     * @throws IllegalStateException if the [MvvmFragment]'s root [View] hasn't been created yet.
     */
    protected fun <T : View> findViewById(@IdRes viewId : Int) : T {
        return (rootView?.findViewById(viewId) ?: throw IllegalStateException("The Fragment View hasn't been created yet."))
    }
    
    
    /**
     * Shows the system software keyboard.
     *
     * @param requestFocus whether the target view should request the focus or not
     */
    protected fun showKeyboard(requestFocus : Boolean = true) {
        rootView?.let { showKeyboard(it, requestFocus) }
    }


    /**
     * Shows the system software keyboard.
     *
     * @param targetView the view that's requesting the keyboard to be shown
     * @param requestFocus whether the target view should request the focus or not
     */
    protected fun showKeyboard(targetView : View, requestFocus : Boolean = true) {
        targetView.showKeyboard(requestFocus)
    }
    
    
    /**
     * Hides the system software keyboard.
     *
     * @param clearFocus whether the focus should be cleared from the target view or not
     */
    protected fun hideKeyboard(clearFocus : Boolean = true) {
        rootView?.let { hideKeyboard(it, clearFocus) }
    }


    /**
     * Hides the system software keyboard.
     *
     * @param targetView the view that's requesting the keyboard to be hidden
     * @param clearFocus whether the focus should be cleared from the target view or not
     */
    protected fun hideKeyboard(targetView : View, clearFocus : Boolean = true) {
        targetView.hideKeyboard(clearFocus)
    }


    /**
     * Performs the Back Press Action (see: [android.app.Activity.onBackPressed]).
     */
    protected fun performBackPress() {
        activity?.onBackPressed()
    }


    /**
     * Finishes the host [android.app.Activity] (see: [android.app.Activity.finish]).
     */
    protected fun finishActivity() {
        activity?.finish()
    }


    /**
     * Finishes the host [android.app.Activity] affinity (see: [android.app.Activity.finishAffinity]).
     */
    protected fun finishActivityAffinity() {
        activity?.finishAffinity()
    }


    override fun onCreateAnimation(transit : Int, enter : Boolean, nextAnim : Int) : Animation {
        if(nextAnim == 0) {
            return AnimationUtils.loadAnimation(context!!, R.anim.no_animation)
        }

        // enabling the hardware acceleration for the time of the animation (to smoothen the things up)
        view?.useHardwareLayer()
        view?.cancelActiveAnimations()

        return AnimationUtils.loadAnimation(context!!, nextAnim).apply {
            setAnimationListener(object : AnimationListenerAdapter() {

                override fun onAnimationStart(animation : Animation?) {
                    onAnimationStarted()
                }

                override fun onAnimationEnd(animation : Animation?) {
                    view?.useNoLayer()
                    onAnimationEnded()
                }

            })
        }
    }


    /**
     * Gets called whenever the [Fragment]-related transition animation starts.
     */
    @CallSuper
    protected open fun onAnimationStarted() {
        isViewAnimating = true
    }


    /**
     * Gets called whenever the [Fragment]-related transition animation ends.
     */
    @CallSuper
    protected open fun onAnimationEnded() {
        isViewAnimating = false
    }


    @CallSuper
    override fun onResume() {
        super.onResume()

        subscribeEventConsumers()
        onRegisterObservables()

        viewModel?.onStart()
    }


    /**
     * Gets called when it's the right time to register the [ObservableField]s of your [androidx.lifecycle.ViewModel].
     */
    protected open fun onRegisterObservables() {
        //
    }


    @CallSuper
    override fun onPause() {
        super.onPause()

        viewModel?.onStop()

        unsubscribeEventConsumers()
        unregisterFields()
    }


    @CallSuper
    override fun onBackPressed() : Boolean {
        hideKeyboard()

        val isConsumedByViewModel = (viewModel?.onBackPressed() ?: false)

        return (handleBackPressEvent() || isConsumedByViewModel)
    }


    private fun handleBackPressEvent() : Boolean {
        return childFragmentManager.fragments.handleBackPressEvent()
    }


    private fun onRestoreStateInternal(stateBundle : Bundle) {
        viewModel?.onRestoreState(stateBundle)
        onRestoreState(stateBundle)
    }


    /**
     * Gets called whenever it's the right time to restore the previously stored state.
     *
     * @param stateBundle the previously store state
     */
    open fun onRestoreState(stateBundle : Bundle) {
        //
    }


    final override fun onSaveInstanceState(outState : Bundle) {
        viewModel?.onSaveState(outState)
        onSaveState(outState)

        super.onSaveInstanceState(outState)
    }


    /**
     * Gets called whenever it's the right time to save the state.
     *
     * @param stateBundle the bundle the state is to be saved into
     */
    open fun onSaveState(stateBundle : Bundle) {
        //
    }


    final override fun onDestroy() {
        onRecycle()
        recycleInternal()
        super.onDestroy()
    }
    
    
    private fun recycleInternal() {
        rootView = null
        viewDataBinding = null
        viewModel = null
        
        isViewAnimating = false
        isViewCreated = false
    }


    /**
     * Gets called right before the destruction of the [Fragment] (see: [Fragment.onDestroy]).
     */
    protected open fun onRecycle() {
        //
    }


    /**
     * Gets called whenever the new [BaseViewModel] event arrives.
     *
     * @param event the newly arrived [BaseViewModel] event
     */
    @CallSuper
    protected open fun onViewModelEvent(event : ViewModelEvent<*>) {
        when(event) {
            is GeneralViewModelEvents.HideKeyboard -> event.data?.let { hideKeyboard(clearFocus = it) }
            is GeneralViewModelEvents.ConfirmBackButtonPress -> performBackPress()
            is GeneralViewModelEvents.FinishActivity -> activity?.finish()
        }
    }


    /**
     * Gets called whenever the [MvvmFragment] becomes "active".
     * (see: [MvvmFragment.setActive])
     */
    protected open fun onBecameActive() {
        //
    }


    /**
     * Gets called whenever the [MvvmFragment] becomes "inactive".
     * (see: [MvvmFragment.setActive])
     */
    protected open fun onBecameInactive() {
        //
    }


    private fun subscribeEventConsumers() {
        viewModel?.subscribe(Consumer(::onViewModelEvent))
            ?.manageLifecycle()
    }


    private fun unsubscribeEventConsumers() {
        eventConsumerDisposables.clear()
    }


    private fun unregisterFields() {
        registeredObservables.forEach { (callback, field) -> field.removeOnPropertyChangedCallback(callback) }
        registeredObservables.clear()
    }
    
    
    /**
     * Set a hint about whether this fragment is currently "active".
     * (This hint defaults to true)
     * (It's mostly used in conjunction with the [androidx.viewpager.widget.ViewPager])
     *
     * (See: [isActive])
     *
     * @param isActive true if this fragment is currently "active" (default).
     */
    fun setActive(isActive : Boolean) {
        val wasChanged = (this.isActive != isActive)
        this.isActive = isActive

        if(isActive) {
            if(wasChanged) {
                onBecameActive()
            }
        } else {
            if(wasChanged) {
                onBecameInactive()
            }
        }
    }


    /**
     * Retrieves the resource id of the layout which will be used
     * as a content view of the [Fragment].
     *
     * @return a valid layout resource id
     */
    @LayoutRes
    protected abstract fun getLayoutId() : Int


    /**
     * Retrieves the id of the Data Binding variable.
     * (This id should correspond to the id of the ViewModel
     * variable defined in your xml layout file)
     *
     * @return the binding variable id
     */
    protected abstract fun getBindingVariable() : Int


    /**
     * Used to retrieve the concrete version of the
     * initialized [BaseViewModel].
     *
     * @return the initialized [BaseViewModel]
     */
    protected abstract fun getViewModel() : VM


    private fun Disposable.manageLifecycle() {
        eventConsumerDisposables.add(this)
    }


    /**
     * Adds the specified [Observable.OnPropertyChangedCallback] to the registry of Lifecycle-aware Callbacks.
     * <br>
     * [Observable.OnPropertyChangedCallback]s are automatically disposed whenever the
     * [Fragment.onPause] method is called.
     *
     * @param observable the [Observable] the [Observable.OnPropertyChangedCallback] is registered to
     */
    protected fun Observable.OnPropertyChangedCallback.manageLifecycle(observable : Observable) {
        registeredObservables.add(Pair(this, observable))
    }


    /**
     * Registers the value change callback to the specified [ObservableField].
     * <br>
     * The lifecycle of the registered callbacks is managed internally (see: [manageLifecycle]),
     * so you don't have to do any manual unregistering yourself.
     *
     * @param callback value change callback
     */
    protected inline fun <T : ObservableField<R>, R : Any> T.register(crossinline callback : (R) -> Unit) {
        this.onPropertyChanged { it.get()?.let(callback) }.manageLifecycle(this)
    }


}