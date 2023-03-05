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

package com.arthurivanets.marvelapi.util;

/**
 *
 */
class TypeConversionUtils {

    /**
     * @param bytes
     * @return
     */
    static String toHexString(byte[] bytes) {
        final StringBuilder hexStringBuilder = new StringBuilder();

        int adjustedByte;

        for (byte aByte : bytes) {
            adjustedByte = (0xFF & aByte);

            if (adjustedByte < 0x10) {
                hexStringBuilder.append("0").append(Integer.toHexString(adjustedByte));
            } else {
                hexStringBuilder.append(Integer.toHexString(adjustedByte));
            }
        }

        return hexStringBuilder.toString();
    }

}
