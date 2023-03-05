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

package com.arthurivanets.sample

import android.os.Looper
import android.util.Log
import com.arthurivanets.marvelapi.MarvelApi
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins

class MarvelApplication : BaseApplication() {

    override fun onInit() {
        initRxJava()
        initMarvelApi()
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler { Log.e("MarvelApplication", "Undeliverable RxError Occurred: $it") }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { AndroidSchedulers.from(Looper.getMainLooper(), true) }
    }

    private fun initMarvelApi() {
        MarvelApi.INSTANCE.init(
            publicKey = BuildConfig.API_PUBLIC_KEY,
            privateKey = BuildConfig.API_PRIVATE_KEY
        )
    }

}
