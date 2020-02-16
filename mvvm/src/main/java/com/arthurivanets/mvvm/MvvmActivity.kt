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
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import com.arthurivanets.mvvm.events.Command
import com.arthurivanets.mvvm.events.Route
import com.arthurivanets.mvvm.events.ViewState
import com.arthurivanets.mvvm.markers.CanFetchExtras
import com.arthurivanets.mvvm.markers.CanHandleNewIntent
import com.arthurivanets.mvvm.util.currentFragment
import com.arthurivanets.mvvm.util.handleBackPressEvent
import com.arthurivanets.mvvm.util.handleNewIntent
import com.arthurivanets.mvvm.util.onPropertyChanged
import com.arthurivanets.rxbus.register
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * A base MVVM Activity to be used in conjunction with the concrete implementations of the [BaseViewModel].
 */
abstract class MvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes
    private val layoutId : Int
) : AppCompatActivity(), CanFetchExtras, CanHandleNewIntent {
    
    
    /**
     * Retrieves the id of the Data Binding variable.
     * (This id should correspond to the id of the ViewModel
     * variable defined in your xml layout file)
     *
     * @return the binding variable id
     */
    abstract val bindingVariable : Int
    
    var extrasBundle = Bundle()
        private set

    private var viewDataBinding : VDB? = null
    private var viewModel : VM? = null
    
    private val viewStateSubscriptionDisposables = CompositeDisposable()
    private val subscriptionDisposables = CompositeDisposable()
    private val registeredObservables = HashSet<Pair<Observable.OnPropertyChangedCallback, Observable>>()


    final override fun onCreate(savedInstanceState : Bundle?) {
        injectDependencies()
        intent?.extras?.let(::fetchExtras)
        preInit()
        super.onCreate(savedInstanceState)
        initDataBinding()
        init(savedInstanceState)
        postInit()
        performDataBinding()
        subscribeViewStateObservers()
    }


    final override fun onNewIntent(intent : Intent?) {
        super.onNewIntent(intent)

        intent?.let(::handleNewIntent)
    }


    /**
     * Gets called when it's the right time for you to inject the dependencies.
     */
    open fun injectDependencies() {
        //
    }


    /**
     * Gets called right before the pre-initialization stage ([preInit] method call),
     * if the received [Bundle] is not null.
     *
     * @param extras the bundle of arguments
     */
    @CallSuper
    override fun fetchExtras(extras : Bundle) {
        extrasBundle = extras
    }


    /**
     * Gets called whenever the current [AppCompatActivity] receives the new [Intent]
     * through the [AppCompatActivity.onNewIntent] method.
     *
     * @param intent the new message [Intent] (see: [android.app.Activity.onNewIntent])
     */
    @CallSuper
    override fun handleNewIntent(intent : Intent) {
        supportFragmentManager.currentFragment?.handleNewIntent(intent)
    }


    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit() {
        //
    }


    /**
     * Get's called when it's the right time for you to initialize the UI elements.
     *
     * @param savedInstanceState state bundle brought from the [android.app.Activity.onCreate]
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


    private fun initDataBinding() {
        viewDataBinding = (viewDataBinding ?: DataBindingUtil.setContentView(this, layoutId))
        viewModel = (viewModel ?: createViewModel())

        viewDataBinding?.setVariable(bindingVariable, viewModel)
        viewDataBinding?.lifecycleOwner = this
    }
    
    
    /**
     * Creates the concrete version of the [BaseViewModel].
     *
     * @return the created concrete version of the [BaseViewModel]
     */
    protected abstract fun createViewModel() : VM


    /**
     * Executes the pending Data Binding operations.
     */
    @CallSuper
    protected open fun performDataBinding() {
        viewDataBinding?.executePendingBindings()
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

        disposeSubscriptions()
        unregisterFields()
    }


    @CallSuper
    override fun onBackPressed() {
        val isConsumedByViewModel = (viewModel?.onBackPressed() ?: false)

        if(!handleBackPressEvent() && !isConsumedByViewModel) {
            super.onBackPressed()
        }
    }


    private fun handleBackPressEvent() : Boolean {
        return supportFragmentManager.fragments.handleBackPressEvent()
    }


    final override fun onRestoreInstanceState(savedInstanceState : Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let(::onRestoreStateInternal)
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
        super.onSaveInstanceState(outState)

        outState.let(::onSaveStateInternal)
    }


    private fun onSaveStateInternal(stateBundle : Bundle) {
        viewModel?.onSaveState(stateBundle)
        onSaveState(stateBundle)
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
        disposeViewStateSubscriptions()
        onRecycle()
        super.onDestroy()
    }


    /**
     * Gets called right before the destruction of the [android.app.Activity] (see: [android.app.Activity.onDestroy]).
     */
    protected open fun onRecycle() {
        // to be overridden
    }
    
    
    /**
     * Gets called whenever the new [Command] arrives from the [BaseViewModel].
     *
     * Override this method to provide the handling of the ViewModel-specific commands.
     *
     * @param command the newly arrived [Command]
     */
    @CallSuper
    protected open fun onHandleCommand(command : Command<*>) {
        // to be overridden
    }
    
    
    /**
     * Gets called whenever the [ViewState] change event arrives from the [BaseViewModel].
     *
     * Override this method to provide the handling of the UI state changes
     * based on the emitted [ViewState] change events.
     *
     * @param state the new [ViewState]
     */
    protected open fun onViewStateChanged(state : ViewState<*>) {
        // to be overridden
    }
    
    
    /**
     * Gets called whenever the new [Route] event arrives from the [BaseViewModel].
     *
     * Override this method to provide the handling of the navigation between the application screens
     * based on the emitted [Route] events.
     *
     * @param route the newly arrived [Route]
     */
    protected open fun onRoute(route : Route<*>) {
        // to be overridden
    }
    
    
    private fun subscribeViewStateObservers() {
        viewModel?.viewStateBus
            ?.register(Consumer(::onViewStateChanged))
            ?.manageViewStateSubscription()
    }
    
    
    private fun disposeViewStateSubscriptions() {
        viewStateSubscriptionDisposables.clear()
    }
    
    
    private fun subscribeEventConsumers() {
        subscribeToViewModelCommandBus()
        subscribeToViewModelRouteBus()
    }
    
    
    private fun subscribeToViewModelCommandBus() {
        viewModel?.commandBus
            ?.register(Consumer(::onHandleCommand))
            ?.manageLifecycle()
    }
    
    
    private fun subscribeToViewModelRouteBus() {
        viewModel?.routeBus
            ?.register(Consumer(::onRoute))
            ?.manageLifecycle()
    }
    
    
    private fun disposeSubscriptions() {
        subscriptionDisposables.clear()
    }


    private fun unregisterFields() {
        registeredObservables.forEach { (callback, field) -> field.removeOnPropertyChangedCallback(callback) }
        registeredObservables.clear()
    }


    private fun Disposable.manageViewStateSubscription() {
        viewStateSubscriptionDisposables.add(this)
    }
    
    
    private fun Disposable.manageLifecycle() {
        subscriptionDisposables.add(this)
    }


    /**
     * Adds the specified [Observable.OnPropertyChangedCallback] to the registry of Lifecycle-aware Callbacks.
     * <br>
     * [Observable.OnPropertyChangedCallback]s are automatically disposed whenever the
     * [android.app.Activity.onPause] method is called.
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