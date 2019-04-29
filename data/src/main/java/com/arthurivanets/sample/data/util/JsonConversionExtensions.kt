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

@file:JvmName("JsonConversionUtils")

package com.arthurivanets.sample.data.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper


private object Constants {

    @JvmStatic val OBJECT_MAPPER = jacksonObjectMapper()

}


fun Any.toJsonNode() : JsonNode {
    return Constants.OBJECT_MAPPER.convertValue(this, JsonNode::class.java)
}


fun Any.toJsonString() : String {
    return Constants.OBJECT_MAPPER.writeValueAsString(this)
}


fun List<*>.toJsonNode() : JsonNode {
    return Constants.OBJECT_MAPPER.convertValue(this, JsonNode::class.java)
}


fun List<*>.toJsonString() : String {
    return Constants.OBJECT_MAPPER.writeValueAsString(this)
}


fun <T> JsonNode.toObject(clazz : Class<T>) : T {
    return Constants.OBJECT_MAPPER.treeToValue(this, clazz)
}


fun <T> JsonNode.toList(clazz : Class<T>) : List<T> {
    return Constants.OBJECT_MAPPER.convertValue(this, Constants.OBJECT_MAPPER.constructListType(clazz))
}


fun <T> ObjectMapper.readValue(jsonNode : JsonNode) : T {
    val reader = Constants.OBJECT_MAPPER.readerFor(object : TypeReference<T>() {})
    return reader.readValue(jsonNode)
}


fun <T> ObjectMapper.convertValue(jsonNode : JsonNode) : T {
    return Constants.OBJECT_MAPPER.convertValue(jsonNode, object : TypeReference<T>() {})
}


fun <T> ObjectMapper.constructListType(clazz : Class<T>) : CollectionType {
    return Constants.OBJECT_MAPPER.typeFactory.constructCollectionType(ArrayList::class.java, clazz)
}


fun String.toJsonNode() : JsonNode {
    return Constants.OBJECT_MAPPER.readTree(this)
}


fun <T> String.toObject(clazz : Class<T>) : T {
    return Constants.OBJECT_MAPPER.readValue(this, clazz)
}


fun <T> String.toList(clazz : Class<T>) : List<T> {
    return Constants.OBJECT_MAPPER.readValue(this, Constants.OBJECT_MAPPER.constructListType(clazz))
}


fun String.isValidJson() : Boolean {
    return try {
        Constants.OBJECT_MAPPER.readTree(this)
        true
    } catch (e : JsonProcessingException) {
        false
    }
}