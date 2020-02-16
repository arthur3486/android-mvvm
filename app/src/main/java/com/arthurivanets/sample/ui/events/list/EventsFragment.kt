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

package com.arthurivanets.sample.ui.events.list

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.mvvm.events.Route
import com.arthurivanets.mvvm.events.ViewState
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.events.EventItem
import com.arthurivanets.sample.adapters.events.EventItemResources
import com.arthurivanets.sample.adapters.events.EventItemViewHolder
import com.arthurivanets.sample.adapters.events.EventItemsRecyclerViewAdapter
import com.arthurivanets.sample.databinding.FragmentEventsBinding
import com.arthurivanets.sample.domain.entities.Event
import com.arthurivanets.sample.ui.base.BaseMvvmFragment
import com.arthurivanets.sample.ui.base.GeneralViewStates
import com.arthurivanets.sample.ui.base.MarvelRoutes
import com.arthurivanets.sample.ui.dashboard.DashboardFragmentDirections
import com.arthurivanets.sample.ui.util.extensions.onItemClick
import com.arthurivanets.sample.ui.util.extensions.sharedDescriptionTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedImageTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedTitleTransitionName
import com.arthurivanets.sample.ui.util.markers.CanScrollToTop
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.view_progress_bar_circular.*
import javax.inject.Inject

class EventsFragment : BaseMvvmFragment<FragmentEventsBinding, EventsViewModel>(R.layout.fragment_events), CanScrollToTop {
    

    @Inject
    lateinit var itemResources : EventItemResources

    private lateinit var adapter : EventItemsRecyclerViewAdapter


    override fun init(savedInstanceState : Bundle?) {
        initRecyclerView()
    }


    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = initLayoutManager()
            adapter = initAdapter()
        }
    }


    private fun initLayoutManager() : RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }


    private fun initAdapter() : EventItemsRecyclerViewAdapter {
        return EventItemsRecyclerViewAdapter(
            context = context!!,
            items = viewModel.items,
            resources = itemResources
        ).apply {
            onItemClickListener = onItemClick { viewModel.onEventClicked(it.itemModel) }
        }.also { adapter = it }
    }
    
    
    override fun scrollToTop(animate : Boolean) {
        if(animate) {
            recyclerView?.smoothScrollToPosition(0)
        } else {
            recyclerView?.scrollToPosition(0)
        }
    }
    
    
    override fun onViewStateChanged(state : ViewState<*>) {
        when(state) {
            is GeneralViewStates.Idle -> onIdleState()
            is GeneralViewStates.Loading -> onLoadingState()
            is GeneralViewStates.Success -> onSuccessState()
            is GeneralViewStates.Error -> onErrorState()
        }
    }
    
    
    private fun onIdleState() {
        progress_bar.isVisible = false
    }
    
    
    private fun onLoadingState() {
        progress_bar.isVisible = true
    }
    
    
    private fun onSuccessState() {
        progress_bar.isVisible = false
    }
    
    
    private fun onErrorState() {
        progress_bar.isVisible = false
    }
    
    
    override fun onRoute(route : Route<*>) {
        when(route) {
            is MarvelRoutes.EventInfoScreen -> route.payload?.let(::onOpenEventInfoScreen)
        }
    }
    
    
    private fun onOpenEventInfoScreen(event : Event) {
        val viewHolder = (getItemViewHolder(event) ?: return)
        
        navigate(
            directions = DashboardFragmentDirections.eventInfoFragmentAction(event),
            navigationExtras = FragmentNavigatorExtras(
                viewHolder.imageIv to event.sharedImageTransitionName,
                viewHolder.titleTv to event.sharedTitleTransitionName,
                viewHolder.descriptionTv to event.sharedDescriptionTransitionName
            )
        )
    }
    
    
    private fun getItemViewHolder(event : Event) : EventItemViewHolder? {
        val index = adapter.indexOf(EventItem(event))
        
        return if(index != -1) {
            (recyclerView.findViewHolderForAdapterPosition(index) as EventItemViewHolder)
        } else {
            null
        }
    }


}