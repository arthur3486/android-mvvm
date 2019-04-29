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

package com.arthurivanets.sample.data.api

import com.arthurivanets.sample.data.BuildConfig
import com.arthurivanets.sample.data.api.endpoints.characters.CharactersEndpoint
import com.arthurivanets.sample.data.api.endpoints.characters.CharactersEndpointImpl
import com.arthurivanets.sample.data.api.endpoints.comics.ComicsEndpoint
import com.arthurivanets.sample.data.api.endpoints.comics.ComicsEndpointImpl
import com.arthurivanets.sample.data.api.endpoints.events.EventsEndpoint
import com.arthurivanets.sample.data.api.endpoints.events.EventsEndpointImpl
import com.arthurivanets.sample.data.api.services.CharactersService
import com.arthurivanets.sample.data.api.services.ComicsService
import com.arthurivanets.sample.data.api.services.EventsService
import com.arthurivanets.sample.data.util.Timeouts
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 */
class MarvelApi : Api {


    // Retrofit
    private lateinit var retrofit : Retrofit

    // Services
    private lateinit var charactersService : CharactersService
    private lateinit var eventsService : EventsService
    private lateinit var comicsService : ComicsService

    // Endpoints
    private lateinit var charactersEndpoint : CharactersEndpoint
    private lateinit var eventsEndpoint : EventsEndpoint
    private lateinit var comicsEndpoint : ComicsEndpoint

    private var requestAuthorizer : Interceptor? = null

    private var isDebuggingEnabled = false

    //
    override val characters : CharactersEndpoint
        get() = charactersEndpoint

    override val events : EventsEndpoint
        get() = eventsEndpoint

    override val comics : ComicsEndpoint
        get() = comicsEndpoint


    companion object {

        @JvmStatic val INSTANCE by lazy { MarvelApi() }

    }


    init {
        init()
    }


    private fun init() {
        initRetrofit()
        initServices()
        initEndpoints()
    }


    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(initOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }


    private fun initOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Timeouts.CONNECT, TimeUnit.SECONDS)
            .readTimeout(Timeouts.READ, TimeUnit.SECONDS)
            .writeTimeout(Timeouts.WRITE, TimeUnit.SECONDS)
            .also(::addExtraClientConfig)
            .build()
    }


    private fun addExtraClientConfig(builder : OkHttpClient.Builder) {
        if(isDebuggingEnabled) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        requestAuthorizer?.let { builder.addInterceptor(it) }
    }


    private fun initServices() {
        charactersService = retrofit.create(CharactersService::class.java)
        eventsService = retrofit.create(EventsService::class.java)
        comicsService = retrofit.create(ComicsService::class.java)
    }


    private fun initEndpoints() {
        charactersEndpoint = CharactersEndpointImpl(charactersService)
        eventsEndpoint = EventsEndpointImpl(eventsService)
        comicsEndpoint = ComicsEndpointImpl(comicsService)
    }


    override fun enableDebugging() : Api {
        return setDebuggingEnabled(true)
    }


    override fun disableDebugging() : Api {
        return setDebuggingEnabled(false)
    }


    override fun setDebuggingEnabled(isEnabled : Boolean) : Api = apply {
        if(isDebuggingEnabled != isEnabled) {
            isDebuggingEnabled = isEnabled

            init()
        }
    }


    override fun setAuthorizer(authorizer : Interceptor) : Api = apply {
        if(requestAuthorizer != authorizer) {
            requestAuthorizer = authorizer

            init()
        }
    }


}