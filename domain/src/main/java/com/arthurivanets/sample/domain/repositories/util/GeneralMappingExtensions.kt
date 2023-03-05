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

@file:JvmName("GeneralMappingUtils")

package com.arthurivanets.sample.domain.repositories.util

import com.arthurivanets.sample.domain.util.*

internal fun Collection<DataCreator>.toDomainCreators(): List<DomainCreator> {
    return this.map { it.toDomainCreator() }
}

internal fun DataCreator.toDomainCreator(): DomainCreator {
    return DomainCreator(
        name = this.name,
        role = this.role
    )
}

internal fun Collection<DataImage>.toDomainImages(): List<DomainImage> {
    return this.map { it.toDomainImage() }
}

internal fun DataImage.toDomainImage(): DomainImage {
    return DomainImage(
        imageUrl = this.imageUrl
    )
}

internal fun Collection<DataUrl>.toDomainUrls(): List<DomainUrl> {
    return this.map { it.toDomainUrl() }
}

internal fun DataUrl.toDomainUrl(): DomainUrl {
    return DomainUrl(
        type = this.type,
        url = this.url
    )
}
