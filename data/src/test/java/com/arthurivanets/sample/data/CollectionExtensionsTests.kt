package com.arthurivanets.sample.data

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CollectionExtensionsTests {

    @Test
    fun testSubListExtractingFunction() {
        val originalList = listOf(1, 2, 3, 4, 5, 6)

        val subList = originalList.subList(0, 4)
        val expectedList = listOf(1, 2, 3, 4)

        assertEquals(expectedList, subList)
    }

}
