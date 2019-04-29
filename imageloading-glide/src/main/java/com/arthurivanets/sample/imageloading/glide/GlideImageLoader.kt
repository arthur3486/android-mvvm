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

package com.arthurivanets.sample.imageloading.glide

import android.widget.ImageView
import com.arthurivanets.sample.imageloading.Config
import com.arthurivanets.sample.imageloading.ImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : ImageLoader {


    override fun load(target : ImageView,
                      drawableId : Int,
                      config : Config) {
        Glide.with(target)
            .load(drawableId)
            .apply(getRequestOptions(config))
            .apply {
                if(config.animate) {
                    transition(DrawableTransitionOptions.withCrossFade())
                }
            }
            .into(target)
    }


    override fun load(target : ImageView,
                      imageUrl : String,
                      config : Config) {
        Glide.with(target)
            .load(imageUrl)
            .apply(getRequestOptions(config))
            .apply {
                if(config.animate) {
                    transition(DrawableTransitionOptions.withCrossFade())
                }
            }
            .into(target)
    }


    private fun getRequestOptions(config : Config) : RequestOptions {
        return RequestOptions().apply {
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            if(config.isSizeSet) {
                override(config.width, config.height)
            }

            if(config.centerCrop) {
                centerCrop()
            }
        }
    }


}