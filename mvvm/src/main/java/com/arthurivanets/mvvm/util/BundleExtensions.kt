@file:JvmName("BundleUtils")

package com.arthurivanets.mvvm.util

import android.os.Bundle

/**
 * Adds the contents of the specified [Bundle] into
 * the current [Bundle].
 *
 * @return the current [Bundle] with appended content
 */
operator fun Bundle.plus(other: Bundle): Bundle {
    return this.also { it.putAll(other) }
}
