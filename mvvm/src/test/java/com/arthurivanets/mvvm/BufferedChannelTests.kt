package com.arthurivanets.mvvm

import junit.framework.TestCase.assertTrue
import org.junit.Test

internal class BufferedChannelTests {

    @Test
    fun `verify that events are not getting buffered when the consumer is attached`() {
        val consumer = AccumulatingConsumer<String>()
        val channel = BufferedChannel<String>()
        channel.consumer = consumer

        val events = listOf(
            "A",
            "B",
            "C"
        )

        assertTrue(consumer.accumulatedEvents.isEmpty())

        events.forEach { channel.post(it) }

        events.forEach {
            assertTrue(it in consumer.accumulatedEvents)
        }
    }

    @Test
    fun `verify that events are getting buffered when the consumer is not attached and flushed once it gets attached`() {
        val consumer1 = AccumulatingConsumer<String>()
        val consumer2 = AccumulatingConsumer<String>()
        val channel = BufferedChannel<String>()

        val events = listOf(
            "A",
            "B",
            "C"
        )

        // Initial State (consumed detached; events get emitted)
        assertTrue(consumer1.accumulatedEvents.isEmpty())

        events.forEach { channel.post(it) }

        assertTrue(consumer1.accumulatedEvents.isEmpty())

        // Consumption State (consumer gets attached to the channel; buffered events get flushed)
        channel.consumer = consumer1

        events.forEach {
            assertTrue(it in consumer1.accumulatedEvents)
        }

        // Empty State (old consumer gets detached, the new one - attached; no events get emitted, as the channel's buffer is empty)
        channel.consumer = null
        channel.consumer = consumer2

        assertTrue(consumer2.accumulatedEvents.isEmpty())
    }

    private class AccumulatingConsumer<T> : Consumer<T> {

        val accumulatedEvents: Set<T>
            get() = _accumulatedEvents

        private val _accumulatedEvents = LinkedHashSet<T>()

        override fun invoke(event: T) {
            _accumulatedEvents += event
        }

    }

}
