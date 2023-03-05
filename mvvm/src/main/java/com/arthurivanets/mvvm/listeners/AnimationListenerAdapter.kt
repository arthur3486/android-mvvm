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

package com.arthurivanets.mvvm.listeners

import android.view.animation.Animation

/**
 * A utility class used for the observation of the animation events.
 */
internal abstract class AnimationListenerAdapter : Animation.AnimationListener {

    override fun onAnimationStart(animation: Animation?) {
        //
    }

    override fun onAnimationRepeat(animation: Animation?) {
        //
    }

    override fun onAnimationEnd(animation: Animation?) {
        //
    }

}
