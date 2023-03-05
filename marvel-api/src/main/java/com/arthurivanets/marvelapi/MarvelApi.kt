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

package com.arthurivanets.marvelapi

import com.arthurivanets.marvelapi.endpoints.characters.CharactersEndpoint
import com.arthurivanets.marvelapi.endpoints.characters.CharactersEndpointImpl
import com.arthurivanets.marvelapi.endpoints.comics.ComicsEndpoint
import com.arthurivanets.marvelapi.endpoints.comics.ComicsEndpointImpl
import com.arthurivanets.marvelapi.endpoints.events.EventsEndpoint
import com.arthurivanets.marvelapi.endpoints.events.EventsEndpointImpl
import com.arthurivanets.marvelapi.services.CharactersService
import com.arthurivanets.marvelapi.services.ComicsService
import com.arthurivanets.marvelapi.services.EventsService
import com.arthurivanets.marvelapi.util.RequestAuthorizer
import com.arthurivanets.marvelapi.util.Timeouts
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
    private lateinit var retrofit: Retrofit

    // Services
    private lateinit var charactersService: CharactersService
    private lateinit var eventsService: EventsService
    private lateinit var comicsService: ComicsService

    // Endpoints
    private lateinit var charactersEndpoint: CharactersEndpoint
    private lateinit var eventsEndpoint: EventsEndpoint
    private lateinit var comicsEndpoint: ComicsEndpoint

    // Auth
    private var publicKey = ""
    private var privateKey = ""
    private var requestAuthorizer: Interceptor? = null

    //
    override val characters: CharactersEndpoint
        get() = charactersEndpoint

    override val events: EventsEndpoint
        get() = eventsEndpoint

    override val comics: ComicsEndpoint
        get() = comicsEndpoint

    companion object {

        @JvmStatic
        val INSTANCE by lazy { MarvelApi() }

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

    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Timeouts.CONNECT, TimeUnit.SECONDS)
            .readTimeout(Timeouts.READ, TimeUnit.SECONDS)
            .writeTimeout(Timeouts.WRITE, TimeUnit.SECONDS)
            .also(::addExtraClientConfig)
            .build()
    }

    private fun addExtraClientConfig(builder: OkHttpClient.Builder) {
        if (BuildConfig.API_DEBUG_MODE) {
            builder.addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY })
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

    override fun init(publicKey: String, privateKey: String) {
        if (isInitializationRequired(publicKey = publicKey, privateKey = privateKey)) {
            initAuthorizer(publicKey = publicKey, privateKey = privateKey)
            init()
        }
    }

    private fun initAuthorizer(publicKey: String, privateKey: String) {
        this.publicKey = publicKey
        this.privateKey = privateKey

        requestAuthorizer = RequestAuthorizer(
            publicKey = publicKey,
            privateKey = privateKey
        )
    }

    private fun isInitializationRequired(publicKey: String, privateKey: String): Boolean {
        return ((this.publicKey != publicKey) || (this.privateKey != privateKey))
    }

}
