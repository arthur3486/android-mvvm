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

package com.arthurivanets.sample.ui.characters.list

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.mvvm.markers.Route
import com.arthurivanets.mvvm.markers.ViewState
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.characters.CharacterItem
import com.arthurivanets.sample.adapters.characters.CharacterItemResources
import com.arthurivanets.sample.adapters.characters.CharacterItemViewHolder
import com.arthurivanets.sample.adapters.characters.CharacterItemsRecyclerViewAdapter
import com.arthurivanets.sample.databinding.FragmentCharactersBinding
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.ui.base.BaseMvvmFragment
import com.arthurivanets.sample.ui.base.GeneralViewStates
import com.arthurivanets.sample.ui.base.MarvelRoutes
import com.arthurivanets.sample.ui.characters.CHARACTERS_COLUMN_COUNT
import com.arthurivanets.sample.ui.dashboard.DashboardFragmentDirections
import com.arthurivanets.sample.ui.util.extensions.onItemClick
import com.arthurivanets.sample.ui.util.extensions.sharedImageTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedNameTransitionName
import com.arthurivanets.sample.ui.util.markers.CanScrollToTop
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.android.synthetic.main.view_progress_bar_circular.*
import javax.inject.Inject

class CharactersFragment : BaseMvvmFragment<FragmentCharactersBinding, CharactersViewModel>(R.layout.fragment_characters), CanScrollToTop {

    @Inject
    lateinit var itemResources: CharacterItemResources

    private lateinit var adapter: CharacterItemsRecyclerViewAdapter

    override fun init(savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = initLayoutManager()
            adapter = initAdapter()
        }
    }

    private fun initLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, CHARACTERS_COLUMN_COUNT)
    }

    private fun initAdapter(): CharacterItemsRecyclerViewAdapter {
        return CharacterItemsRecyclerViewAdapter(
            context = context!!,
            items = viewModel.items,
            resources = itemResources
        ).apply {
            onItemClickListener = onItemClick { viewModel.onCharacterClicked(it.itemModel) }
        }.also { adapter = it }
    }

    override fun scrollToTop(animate: Boolean) {
        if (animate) {
            recyclerView?.smoothScrollToPosition(0)
        } else {
            recyclerView?.scrollToPosition(0)
        }
    }

    override fun onViewStateChanged(state: ViewState) {
        when (state) {
            is GeneralViewStates.Idle<*> -> onIdleState()
            is GeneralViewStates.Loading<*> -> onLoadingState()
            is GeneralViewStates.Success<*> -> onSuccessState()
            is GeneralViewStates.Error<*> -> onErrorState()
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

    override fun onRoute(route: Route) {
        when (route) {
            is MarvelRoutes.CharacterInfoScreen -> onOpenCharacterInfoScreen(route.character)
        }
    }

    private fun onOpenCharacterInfoScreen(character: Character) {
        val viewHolder = (getItemViewHolder(character) ?: return)

        navigate(
            directions = DashboardFragmentDirections.characterInfoFragmentAction(character),
            navigationExtras = FragmentNavigatorExtras(
                viewHolder.imageIv to character.sharedImageTransitionName,
                viewHolder.nameTv to character.sharedNameTransitionName
            )
        )
    }

    private fun getItemViewHolder(character: Character): CharacterItemViewHolder? {
        val index = adapter.indexOf(CharacterItem(character))

        return if (index != -1) {
            (recyclerView.findViewHolderForAdapterPosition(index) as CharacterItemViewHolder)
        } else {
            null
        }
    }

}
