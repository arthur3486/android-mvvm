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

@file:JvmName("GeneralMappingUtils")

package com.arthurivanets.sample.data.datastores.util

import com.arthurivanets.sample.data.util.*

internal fun Collection<ApiCreator>.toDataCreators(): List<DataCreator> {
    return this.map { it.toDataCreator() }
}

internal fun ApiCreator.toDataCreator(): DataCreator {
    return DataCreator(
        name = this.name,
        role = this.role
    )
}

internal fun Collection<ApiImage>.toDataImages(): List<DataImage> {
    return this.map { it.toDataImage() }
}

internal fun ApiImage.toDataImage(): DataImage {
    return DataImage(
        imageUrl = (if (!this.isDefaultImage) this.imageUrl else "")
    )
}

internal fun Collection<ApiUrl>.toDataUrls(): List<DataUrl> {
    return this.map { it.toDataUrl() }
}

internal fun ApiUrl.toDataUrl(): DataUrl {
    return DataUrl(
        type = this.type,
        url = this.url
    )
}
