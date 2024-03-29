/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.work@gmail.com
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

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arthurivanets.mvvm.markers.Command
import com.arthurivanets.mvvm.markers.Route
import com.arthurivanets.mvvm.markers.ViewState
import com.arthurivanets.mvvm.util.CompositeMapDisposable
import com.arthurivanets.mvvm.util.adapt
import com.arthurivanets.rxbus.BusEvent
import com.arthurivanets.rxbus.EventSource
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * An abstract implementation of the [BaseViewModel].
 * <br>
 * (Extend your concrete [ViewModel] implementations from this class)
 *
 * @param defaultViewState the default view state (will be delivered through the corresponding bus)
 */
abstract class AbstractViewModel(
    defaultViewState: ViewState? = null
) : ViewModel(), BaseViewModel {

    protected var viewState: ViewState?
        set(value) {
            _viewStateHolder.value = value
        }
        get() = _viewStateHolder.value

    final override val commandChannel: Channel<Command>
        get() = _commandChannel

    final override val routeChannel: Channel<Route>
        get() = _routeChannel

    final override val viewStateHolder: LiveData<ViewState>
        get() = _viewStateHolder

    private val _commandChannel = BufferedChannel<Command>().asMainThreadChannel()
    private val _routeChannel = BufferedChannel<Route>().asMainThreadChannel()
    private val _viewStateHolder = (defaultViewState?.let { MutableLiveData(it) } ?: MutableLiveData())

    private val shortLivingDisposables = CompositeMapDisposable<String>()
    private val longLivingDisposables = CompositeMapDisposable<String>()

    private val eventHandler = Consumer(::onEvent)

    @CallSuper
    override fun onStart() {
        //
    }

    @CallSuper
    override fun onStop() {
        clearShortLivingDisposables()
    }

    @CallSuper
    override fun onCleared() {
        shortLivingDisposables.dispose()
        longLivingDisposables.dispose()
        super.onCleared()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    @CallSuper
    override fun onRestoreState(bundle: Bundle) {
        //
    }

    @CallSuper
    override fun onSaveState(bundle: Bundle) {
        //
    }

    /**
     * Used for the delivery of the Events generated by....
     */
    @CallSuper
    protected open fun onEvent(event: BusEvent<*>) {
        //
    }

    /**
     * Registers the specified [EventSource] for further consumption of the corresponding
     * emitted events in the [onEvent] method.
     * <br>
     * The exact management of the lifecycle of the subscription is taken care of internally.
     */
    protected fun <T : BusEvent<*>> registerEventEmitter(eventEmitter: EventSource<T>) {
        eventEmitter.subscribe(eventHandler.adapt()).manageLongLivingDisposable()
    }

    /**
     * Dispatches the [Command] to its final destination (the owning View).
     * <br>
     * (The [Command] will be delivered if and only if the owning View has an active subscription to the current ViewModel)
     */
    protected fun dispatchCommand(command: Command) {
        _commandChannel.post(command)
    }

    /**
     * Changes the current [ViewState] to the specified one, and dispatches the [ViewState] change event
     * to its final destination (the owning View).
     * <br>
     * (The [ViewState] change event will be delivered if and only if the owning View has an active subscription to the current ViewModel)
     */
    protected fun postViewState(newState: ViewState) {
        _viewStateHolder.postValue(newState)
    }

    /**
     * Dispatches the [Route] event to its final destination (the owning View).
     * <br>
     * (The [Route] event will be delivered if and only if the owning View has an active subscription to the current ViewModel)
     */
    protected fun route(destinationRoute: Route) {
        _routeChannel.post(destinationRoute)
    }

    /**
     * Adds the specified disposable to the registry of Short-living [Disposable]s.
     * <br>
     * (If there's an active short-living [Disposable] associated with the same key, it will be disposed)
     * <br>
     * <br>
     * Short Living [Disposable]s are automatically disposed whenever the [BaseViewModel]'s
     * [BaseViewModel.onStop] method is called.
     *
     * @param key the key to associate with this disposable
     */
    protected fun Disposable.manageShortLivingDisposable(key: String = this.hashCode().toString()) {
        disposeShortLivingDisposable(key)
        shortLivingDisposables[key] = this
    }

    /**
     * Disposes the active Short-living [Disposable] that's associated with the specified key (if there's any).
     */
    protected fun disposeShortLivingDisposable(key: String) {
        shortLivingDisposables.remove(key)?.dispose()
    }

    /**
     * Disposes all the Short-living [Disposable]s that are present within
     * the Short-living [Disposable]s Registry.
     */
    protected fun clearShortLivingDisposables() {
        shortLivingDisposables.clear()
    }

    /**
     * Adds the specified disposable to the registry of Long-living [Disposable]s.
     * <br>
     * (If there's an active long-living [Disposable] associated with the same key, it will be disposed)
     * <br>
     * <br>
     * Long Living [Disposable]s are automatically disposed whenever the [ViewModel]'s
     * [ViewModel.onCleared] method is called.
     *
     * @param key the key to associate with this disposable
     */
    protected fun Disposable.manageLongLivingDisposable(key: String = this.hashCode().toString()) {
        disposeLongLivingDisposable(key)
        longLivingDisposables[key] = this
    }

    /**
     * Disposes the active Long-living [Disposable] that's associated with the specified key (if there's any).
     */
    protected fun disposeLongLivingDisposable(key: String) {
        longLivingDisposables.remove(key)?.dispose()
    }

    /**
     * Disposes all the Long-living [Disposable]s that are present within
     * the Long-living [Disposable]s Registry.
     */
    protected fun clearLongLivingDisposables() {
        longLivingDisposables.clear()
    }

}
