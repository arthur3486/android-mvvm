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

package com.arthurivanets.sample.data.entities

import com.arthurivanets.sample.data.util.DataCreator
import com.arthurivanets.sample.data.util.DataImage
import com.arthurivanets.sample.data.util.DataUrl

data class Comics(
    val id : Long = -1L,
    val digitalId : Long = -1L,
    val title : String = "",
    val issueNumber : Double = 0.0,
    val variantDescription : String = "",
    val description : String = "",
    val modificationTimeInMillis : Long = 0L,
    val isbn : String = "",
    val upc : String = "",
    val diamondCode : String = "",
    val ean : String = "",
    val issn : String = "",
    val format : String = "",
    val pageCount : Int = 0,
    val thumbnail : DataImage = DataImage(),
    val images : List<DataImage> = emptyList(),
    val creators : List<DataCreator> = emptyList(),
    val urls : List<DataUrl> = emptyList()
)