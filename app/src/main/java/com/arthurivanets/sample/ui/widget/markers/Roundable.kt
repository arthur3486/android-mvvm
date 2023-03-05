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

package com.arthurivanets.sample.ui.widget.markers

interface Roundable {

    fun setCornerRadius(cornerRadius: Float)

    fun setTopLeftCornerRadius(topLeftCornerRadius: Float)

    fun getTopLeftCornerRadius(): Float

    fun setTopRightCornerRadius(topRightCornerRadius: Float)

    fun getTopRightCornerRadius(): Float

    fun setBottomLeftCornerRadius(bottomLeftCornerRadius: Float)

    fun getBottomLeftCornerRadius(): Float

    fun setBottomRightCornerRadius(bottomRightCornerRadius: Float)

    fun getBottomRightCornerRadius(): Float

}
