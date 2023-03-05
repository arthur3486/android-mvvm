package com.arthurivanets.mvvm.util

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.*
import org.junit.Test
import java.util.concurrent.TimeUnit

class CompositeMapDisposableTests {

    @Test
    fun testItemAdditionAndRetrieval() {
        val disposableMap = CompositeMapDisposable<String>()

        val subscription1 = newDummyObservable().executeOnBgThread()
        val subscription2 = newDummyObservable().executeOnBgThread()
        val subscription3 = newDummyObservable().executeOnBgThread()

        disposableMap["a"] = subscription1
        disposableMap["b"] = subscription2
        disposableMap["c"] = subscription3

        assertEquals(disposableMap.size, 3)
        assertEquals(subscription1, disposableMap["a"])
        assertEquals(subscription2, disposableMap["b"])
        assertEquals(subscription3, disposableMap["c"])

        disposableMap.clear()
    }

    @Test
    fun testItemRemoval() {
        val disposableMap = CompositeMapDisposable<String>()

        val subscription1 = newDummyObservable().executeOnBgThread()
        val subscription2 = newDummyObservable().executeOnBgThread()
        val subscription3 = newDummyObservable().executeOnBgThread()

        disposableMap["a"] = subscription1
        disposableMap["b"] = subscription2
        disposableMap["c"] = subscription3

        //
        assertEquals(disposableMap.size, 3)

        //
        val removedSubscription1 = disposableMap.remove("a")

        assertEquals(subscription1, removedSubscription1)
        assertTrue(removedSubscription1.isActuallyDisposed())

        //
        val removedSubscription2 = disposableMap.remove("b")

        assertEquals(subscription2, removedSubscription2)
        assertTrue(removedSubscription2.isActuallyDisposed())

        //
        val removedSubscription3 = disposableMap.remove("c")

        assertEquals(subscription3, removedSubscription3)
        assertTrue(removedSubscription3.isActuallyDisposed())

        //
        assertEquals(disposableMap.size, 0)
    }

    @Test
    fun testMapClearing() {
        val disposableMap = CompositeMapDisposable<String>()

        val subscription1 = newDummyObservable().executeOnBgThread()
        val subscription2 = newDummyObservable().executeOnBgThread()
        val subscription3 = newDummyObservable().executeOnBgThread()

        disposableMap["a"] = subscription1
        disposableMap["b"] = subscription2
        disposableMap["c"] = subscription3

        //
        assertEquals(disposableMap.size, 3)

        //
        disposableMap.clear()
        assertFalse(disposableMap.isDisposed)

        assertEquals(disposableMap.size, 0)

        //
        assertTrue(subscription1.isDisposed)
        assertTrue(subscription2.isDisposed)
        assertTrue(subscription3.isDisposed)
    }

    @Test
    fun testMapDisposal() {
        val disposableMap = CompositeMapDisposable<String>()

        val subscription1 = newDummyObservable().executeOnBgThread()
        val subscription2 = newDummyObservable().executeOnBgThread()
        val subscription3 = newDummyObservable().executeOnBgThread()

        disposableMap["a"] = subscription1
        disposableMap["b"] = subscription2
        disposableMap["c"] = subscription3

        //
        assertEquals(disposableMap.size, 3)

        //
        disposableMap.dispose()

        assertEquals(disposableMap.size, 0)
        assertTrue(disposableMap.isDisposed)

        //
        assertTrue(subscription1.isDisposed)
        assertTrue(subscription2.isDisposed)
        assertTrue(subscription3.isDisposed)
    }

    private fun newDummyObservable(): Observable<Long> {
        return Observable.timer(10, TimeUnit.SECONDS)
    }

    private fun Observable<*>.executeOnBgThread(): Disposable {
        return this.subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun Disposable?.isActuallyDisposed(): Boolean {
        return (this?.isDisposed ?: false)
    }

}
