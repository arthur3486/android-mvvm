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

@file:JvmName("SharedTransitionUtils")

package com.arthurivanets.sample.ui.util.extensions

import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.entities.Event

@get:JvmName("getSharedImageTransitionName")
val Comics.sharedImageTransitionName: String
    get() = "comics_shared_image_transition_${this.id}"

@get:JvmName("getSharedTitleTransitionName")
val Comics.sharedTitleTransitionName: String
    get() = "comics_shared_title_transition_${this.id}"

@get:JvmName("getSharedImageTransitionName")
val Character.sharedImageTransitionName: String
    get() = "character_shared_image_transition_${this.id}"

@get:JvmName("getSharedNameTransitionName")
val Character.sharedNameTransitionName: String
    get() = "character_shared_name_transition_${this.id}"

@get:JvmName("getSharedImageTransitionName")
val Event.sharedImageTransitionName: String
    get() = "event_shared_image_transition_${this.id}"

@get:JvmName("getSharedTitleTransitionName")
val Event.sharedTitleTransitionName: String
    get() = "event_shared_title_transition_${this.id}"

@get:JvmName("getSharedDescriptionTransitionName")
val Event.sharedDescriptionTransitionName: String
    get() = "event_shared_description_transition_${this.id}"
