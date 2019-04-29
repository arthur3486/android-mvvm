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

package com.arthurivanets.sample.util.glide

import android.content.Context
import com.arthurivanets.sample.imageloading.getDefaultCacheDir
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

@GlideModule
class GlideConfigurationModule : AppGlideModule() {


    override fun registerComponents(context : Context, glide : Glide, registry : Registry) {
        super.registerComponents(context, glide, registry)

        // OkHttp Client initialization
        val client = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .cache(Cache(getDefaultCacheDir(context), Int.MAX_VALUE.toLong()))
            .build()

        // glide url-handling method replacement
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }


    override fun isManifestParsingEnabled() : Boolean {
        return false
    }


}