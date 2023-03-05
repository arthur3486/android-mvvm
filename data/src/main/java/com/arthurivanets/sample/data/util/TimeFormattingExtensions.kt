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

@file:JvmName("TimeFormattingUtils")

package com.arthurivanets.sample.data.util

/**
 *
 */
fun Long.toGeneralTime(): String {
    return TimeFormattingUtil.INSTANCE.formatGeneral(this)
}

/**
 *
 */
fun String.toMillisFromGeneralTime(): Long {
    return (if (!this.isEmpty()) TimeFormattingUtil.INSTANCE.parseGeneral(this) else 0L)
}

/**
 *
 */
fun Long.toTimePeriodTime(): String {
    return TimeFormattingUtil.INSTANCE.formatTimePeriod(this)
}

/**
 *
 */
fun String.toMillisFromTimePeriodTime(): Long {
    return (if (!this.isEmpty()) TimeFormattingUtil.INSTANCE.parseTimePeriod(this) else 0L)
}
