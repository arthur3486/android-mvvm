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

package com.arthurivanets.sample.ui.comics.info

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.arthurivanets.adapster.ktx.isEmpty
import com.arthurivanets.adapster.listeners.DatasetChangeListenerAdapter
import com.arthurivanets.commons.ktx.getColorCompat
import com.arthurivanets.commons.ktx.statusBarSize
import com.arthurivanets.mvvm.markers.Route
import com.arthurivanets.mvvm.markers.ViewState
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.characters.CharacterItem
import com.arthurivanets.sample.adapters.characters.CharacterItemResources
import com.arthurivanets.sample.adapters.characters.CharacterItemViewHolder
import com.arthurivanets.sample.adapters.characters.CharacterItemsRecyclerViewAdapter
import com.arthurivanets.sample.databinding.FragmentComicsInfoBinding
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.entities.Image
import com.arthurivanets.sample.imageloading.ImageLoader
import com.arthurivanets.sample.ui.base.BaseMvvmFragment
import com.arthurivanets.sample.ui.base.GeneralViewStates
import com.arthurivanets.sample.ui.base.MarvelRoutes
import com.arthurivanets.sample.ui.util.extensions.onItemClick
import com.arthurivanets.sample.ui.util.extensions.sharedImageTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedNameTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedTitleTransitionName
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.view_comics_info_app_bar_content.*
import kotlinx.android.synthetic.main.view_comics_info_content.*
import kotlinx.android.synthetic.main.view_item_empty_view.*
import kotlinx.android.synthetic.main.view_progress_bar_horizontal.*
import javax.inject.Inject

class ComicsInfoFragment : BaseMvvmFragment<FragmentComicsInfoBinding, ComicsInfoViewModel>(R.layout.fragment_comics_info) {
    
    
    private val args by navArgs<ComicsInfoFragmentArgs>()
    
    @Inject
    lateinit var imageLoader : ImageLoader
    
    @Inject
    lateinit var characterItemResources : CharacterItemResources
    
    private lateinit var characterItemsAdapter : CharacterItemsRecyclerViewAdapter
    
    
    override fun init(savedInstanceState : Bundle?) {
        val comics = args.comics
        viewModel.comics = comics
        
        initAppBar(comics)
        initCharactersRecyclerView()
        initTransitionViews(comics)
        updateCharactersContainer()
    }
    
    
    private fun initAppBar(comics : Comics) {
        initCollapsingToolbar()
        initToolbar()
        loadHeaderImage(comics.thumbnail)
    }
    
    
    private fun initCollapsingToolbar() {
        with(collapsingToolbar) {
            setContentScrimColor(getColorCompat(R.color.colorPrimary))
            setCollapsedTitleTextColor(getColorCompat(R.color.toolbar_title_color))
            setExpandedTitleColor(getColorCompat(R.color.toolbar_title_color))
        }
    }
    
    
    private fun initToolbar() {
        toolbar.updateLayoutParams<CollapsingToolbarLayout.LayoutParams> {
            topMargin = context!!.statusBarSize
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }
    
    
    private fun initCharactersRecyclerView() {
        with(charactersRecyclerView) {
            layoutManager = initLayoutManager()
            adapter = initAdapter()
        }
    }
    
    
    private fun initLayoutManager() : RecyclerView.LayoutManager {
        return LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
    }
    
    
    private fun initAdapter() : CharacterItemsRecyclerViewAdapter {
        return CharacterItemsRecyclerViewAdapter(
            context = context!!,
            items = viewModel.characterItems,
            resources = characterItemResources
        ).apply {
            addOnDatasetChangeListener(onDataSetChangeListener)
            onItemClickListener = onItemClick { viewModel.onCharacterClicked(it.itemModel) }
        }.also { characterItemsAdapter = it }
    }
    
    
    private fun initTransitionViews(comics : Comics) {
        imageContainer.transitionName = comics.sharedImageTransitionName
        titleTv.transitionName = comics.sharedTitleTransitionName
    }
    
    
    private fun updateCharactersContainer() {
        val isCharactersContainerVisible = !characterItemsAdapter.isEmpty()
        
        charactersLabelTv.isVisible = isCharactersContainerVisible
        charactersRecyclerView.isVisible = isCharactersContainerVisible
    }
    
    
    override fun postInit() {
        super.postInit()
    
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    
    
    private fun loadHeaderImage(image : Image) {
        emptyViewTv.isVisible = !image.hasImage
        
        if(image.hasImage) {
            imageLoader.load(imageIv, image.imageUrl)
        } else {
            imageLoader.load(imageIv, R.drawable.marvel_comics_placeholder)
        }
    }
    
    
    override fun onViewStateChanged(state : ViewState) {
        when(state) {
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
    
    
    override fun onRoute(route : Route) {
        when(route) {
            is MarvelRoutes.CharacterInfoScreen -> onOpenCharacterInfoScreen(route.character)
        }
    }
    
    
    private fun onOpenCharacterInfoScreen(character : Character) {
        val viewHolder = (getItemViewHolder(character) ?: return)
        
        navigate(
            directions = ComicsInfoFragmentDirections.characterInfoFragmentAction(character),
            navigationExtras = FragmentNavigatorExtras(
                viewHolder.imageIv to character.sharedImageTransitionName,
                viewHolder.nameTv to character.sharedNameTransitionName
            )
        )
    }
    
    
    private fun getItemViewHolder(character : Character) : CharacterItemViewHolder? {
        val index = characterItemsAdapter.indexOf(CharacterItem(character))
        
        return if(index != -1) {
            (charactersRecyclerView.findViewHolderForAdapterPosition(index) as CharacterItemViewHolder)
        } else {
            null
        }
    }
    
    
    private val onDataSetChangeListener = object : DatasetChangeListenerAdapter<MutableList<CharacterItem>, CharacterItem>() {
    
        override fun onDatasetSizeChanged(oldSize : Int, newSize : Int) {
            updateCharactersContainer()
        }
        
    }


}