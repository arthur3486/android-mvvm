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

package com.arthurivanets.sample.adapters.characters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.sample.R
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Image
import com.arthurivanets.sample.ui.util.extensions.sharedImageTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedNameTransitionName

class CharacterItemViewHolder(
    itemView: View,
    private val resources: CharacterItemResources?
) : BaseItem.ViewHolder<Character>(itemView) {

    val overlayView = itemView.findViewById<View>(R.id.overlay)
    val imageIv = itemView.findViewById<ImageView>(R.id.imageIv)
    val emptyViewTv = itemView.findViewById<TextView>(R.id.emptyViewTv)
    val nameTv = itemView.findViewById<TextView>(R.id.nameTv)

    override fun bindData(data: Character?) {
        super.bindData(data)

        data?.let { handleData(it) }
    }

    private fun handleData(data: Character) {
        handleImage(data)
        handleName(data)
    }

    private fun handleImage(data: Character) {
        loadImage(data.thumbnail)

        imageIv.transitionName = data.sharedImageTransitionName
    }

    private fun handleName(data: Character) {
        nameTv.text = data.name
        nameTv.transitionName = data.sharedNameTransitionName
    }

    private fun loadImage(image: Image) {
        emptyViewTv.isVisible = !image.hasImage

        if (image.hasImage) {
            loadImage(image.imageUrl)
        } else {
            loadImage(R.drawable.marvel_character_placeholder)
        }
    }

    private fun loadImage(@DrawableRes drawableId: Int) {
        resources?.imageLoader?.load(
            target = imageIv,
            drawableId = drawableId
        )
    }

    private fun loadImage(imageUrl: String) {
        resources?.imageLoader?.load(
            target = imageIv,
            imageUrl = imageUrl
        )
    }

}
