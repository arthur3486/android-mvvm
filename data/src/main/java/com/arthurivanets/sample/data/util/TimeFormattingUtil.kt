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

package com.arthurivanets.sample.data.util

import com.arthurivanets.commons.BaseSynchronized
import java.text.SimpleDateFormat
import java.util.*

class TimeFormattingUtil : BaseSynchronized() {

    private val locale: Locale = Locale.getDefault()
    private val generalTimeFormatter = SimpleDateFormat(Formats.GENERAL, locale)
    private val timePeriodTimeFormatter = SimpleDateFormat(Formats.TIME_PERIOD, locale)

    companion object {

        @JvmStatic
        val INSTANCE = TimeFormattingUtil()

    }

    object Formats {

        /**
         * Formatted date and time would look like following: 2018-04-12T03:00:00-000Z
         */
        const val GENERAL = "yyyy-MM-dd'T'HH:mm:ssZ"

        /**
         * Formatted date and time would look like following: 2018-04-12 03:00:00
         */
        const val TIME_PERIOD = "yyyy-MM-dd HH:mm:ss"

    }

    /**
     * Formats the specified time (in milliseconds) according to [Formats.GENERAL].
     */
    fun formatGeneral(timeInMillis: Long): String = withLock {
        generalTimeFormatter.format(timeInMillis)
    }

    /**
     * Extracts the exact time in milliseconds from the time formatted according to [Formats.GENERAL].
     */
    fun parseGeneral(formattedTime: String): Long = withLock {
        generalTimeFormatter.parse(formattedTime).time
    }

    /**
     * Formats the specified time (in milliseconds) according to [Formats.TIME_PERIOD].
     */
    fun formatTimePeriod(timeInMillis: Long): String = withLock {
        timePeriodTimeFormatter.format(timeInMillis)
    }

    /**
     * Extracts the exact time in milliseconds from the time formatted according to [Formats.TIME_PERIOD].
     */
    fun parseTimePeriod(formattedTime: String): Long = withLock {
        timePeriodTimeFormatter.parse(formattedTime).time
    }

}
