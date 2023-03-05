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

package com.arthurivanets.marvelapi.util

import com.arthurivanets.marvelapi.endpoints.EndpointPaths
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *
 */
internal class RequestAuthorizer(
    val publicKey: String,
    val privateKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().authorize())
    }

    private fun Request.authorize(): Request {
        return this.newBuilder()
            .url(this.createAuthorizedUrl())
            .build()
    }

    private fun Request.createAuthorizedUrl(): HttpUrl {
        val timestampInMillis = System.currentTimeMillis()

        return this.url
            .newBuilder()
            .addQueryParameter(EndpointPaths.Params.API_KEY, publicKey)
            .addQueryParameter(EndpointPaths.Params.HASH, timestampInMillis.createRequestHash(publicKey, privateKey))
            .addQueryParameter(EndpointPaths.Params.TIMESTAMP, timestampInMillis.toString())
            .build()
    }

}
