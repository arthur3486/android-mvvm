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

package com.arthurivanets.sample.adapters.events

import android.content.Context
import com.arthurivanets.adapster.databinding.ObservableTrackableRecyclerViewAdapter
import com.arthurivanets.adapster.databinding.TrackableList
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.markers.ItemResources

class EventItemsRecyclerViewAdapter(
    context: Context,
    items: TrackableList<Long, EventItem>,
    private val resources: EventItemResources
) : ObservableTrackableRecyclerViewAdapter<Long, EventItem, EventItemViewHolder>(context, items) {

    var onItemClickListener: OnItemClickListener<EventItem>? = null

    override fun assignListeners(
        holder: EventItemViewHolder,
        position: Int,
        item: EventItem
    ) {
        super.assignListeners(holder, position, item)

        onItemClickListener?.let { item.setOnItemClickListener(holder, it) }
    }

    override fun getResources(): ItemResources? {
        return resources
    }

}
