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

package com.arthurivanets.sample.ui.events.info

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.arthurivanets.adapster.ktx.isEmpty
import com.arthurivanets.adapster.listeners.DatasetChangeListenerAdapter
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.commons.ktx.extract
import com.arthurivanets.commons.ktx.getColorCompat
import com.arthurivanets.commons.ktx.statusBarSize
import com.arthurivanets.commons.ktx.updateLayoutParams
import com.arthurivanets.mvvm.events.ViewModelEvent
import com.arthurivanets.sample.BR
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.characters.CharacterItem
import com.arthurivanets.sample.adapters.characters.CharacterItemResources
import com.arthurivanets.sample.adapters.characters.CharacterItemViewHolder
import com.arthurivanets.sample.adapters.characters.CharacterItemsRecyclerViewAdapter
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.adapters.comics.ComicsItemResources
import com.arthurivanets.sample.adapters.comics.ComicsItemViewHolder
import com.arthurivanets.sample.adapters.comics.ComicsItemsRecyclerViewAdapter
import com.arthurivanets.sample.databinding.FragmentEventInfoBinding
import com.arthurivanets.sample.domain.entities.Character
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.domain.entities.Event
import com.arthurivanets.sample.domain.entities.Image
import com.arthurivanets.sample.imageloading.ImageLoader
import com.arthurivanets.sample.ui.base.BaseFragment
import com.arthurivanets.sample.ui.characters.info.CharacterInfoFragment
import com.arthurivanets.sample.ui.characters.info.newBundle
import com.arthurivanets.sample.ui.comics.info.ComicsInfoFragment
import com.arthurivanets.sample.ui.comics.info.newBundle
import com.arthurivanets.sample.ui.util.extensions.sharedDescriptionTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedImageTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedNameTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedTitleTransitionName
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.view_event_info_app_bar_content.*
import kotlinx.android.synthetic.main.view_event_info_content.*
import kotlinx.android.synthetic.main.view_item_empty_view.*
import kotlinx.android.synthetic.main.view_progress_bar_horizontal.*
import javax.inject.Inject

class EventInfoFragment : BaseFragment<FragmentEventInfoBinding, EventInfoViewModel>() {


    @Inject
    lateinit var localViewModel : EventInfoViewModel
    
    @Inject
    lateinit var imageLoader : ImageLoader
    
    @Inject
    lateinit var comicsItemResources : ComicsItemResources
    
    @Inject
    lateinit var characterItemResources : CharacterItemResources
    
    private lateinit var comicsItemsAdapter : ComicsItemsRecyclerViewAdapter
    private lateinit var characterItemsAdapter : CharacterItemsRecyclerViewAdapter
    
    
    companion object {}
    
    
    override fun fetchExtras(extras : Bundle) {
        super.fetchExtras(extras)
    
        extras.extract(extrasExtractor).also {
            localViewModel.setEvent(it.event)
        }
    }
    
    
    override fun init(savedInstanceState : Bundle?) {
        val event = localViewModel.getEvent()
    
        initAppBar(event)
        initComicsRecyclerView()
        initCharactersRecyclerView()
        initTransitionViews(event)
        updateComicsContainer()
        updateCharactersContainer()
    }
    
    
    private fun initAppBar(event : Event) {
        initCollapsingToolbar()
        initToolbar()
        loadHeaderImage(event.thumbnail)
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
    
    
    private fun initComicsRecyclerView() {
        with(comicsRecyclerView) {
            layoutManager = initComicsLayoutManager()
            adapter = initComicsAdapter()
        }
    }
    
    
    private fun initComicsLayoutManager() : RecyclerView.LayoutManager {
        return LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
    }
    
    
    private fun initComicsAdapter() : ComicsItemsRecyclerViewAdapter {
        comicsItemsAdapter = ComicsItemsRecyclerViewAdapter(
            context = context!!,
            items = localViewModel.comicsItems,
            resources = comicsItemResources
        )
        comicsItemsAdapter.addOnDatasetChangeListener(onComicsDataSetChangeListener)
        comicsItemsAdapter.onItemClickListener = OnItemClickListener { _, item, _ -> localViewModel.onComicsClicked(item) }
        
        return comicsItemsAdapter
    }
    
    
    private fun initCharactersRecyclerView() {
        with(charactersRecyclerView) {
            layoutManager = initCharactersLayoutManager()
            adapter = initCharactersAdapter()
        }
    }
    
    
    private fun initCharactersLayoutManager() : RecyclerView.LayoutManager {
        return LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
    }
    
    
    private fun initCharactersAdapter() : CharacterItemsRecyclerViewAdapter {
        characterItemsAdapter = CharacterItemsRecyclerViewAdapter(
            context = context!!,
            items = localViewModel.characterItems,
            resources = characterItemResources
        )
        characterItemsAdapter.addOnDatasetChangeListener(onCharactersDataSetChangeListener)
        characterItemsAdapter.onItemClickListener = OnItemClickListener { _, item, _ -> localViewModel.onCharacterClicked(item) }
        
        return characterItemsAdapter
    }
    
    
    private fun initTransitionViews(event : Event) {
        imageContainer.transitionName = event.sharedImageTransitionName
        titleTv.transitionName = event.sharedTitleTransitionName
        descriptionTv.transitionName = event.sharedDescriptionTransitionName
    }
    
    
    private fun updateComicsContainer() {
        val isComicsContainerVisible = !comicsItemsAdapter.isEmpty()
        
        comicsLabelTv.isVisible = isComicsContainerVisible
        comicsRecyclerView.isVisible = isComicsContainerVisible
    }
    
    
    private fun updateCharactersContainer() {
        val isCharactersContainerVisible = !characterItemsAdapter.isEmpty()
        
        charactersLabelTv.isVisible = isCharactersContainerVisible
        charactersRecyclerView.isVisible = isCharactersContainerVisible
    }
    
    
    override fun postInit() {
        super.postInit()
        
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        
        onLoadingStateChanged(localViewModel.isLoading)
    }
    
    
    private fun loadHeaderImage(image : Image) {
        emptyViewTv.isVisible = !image.hasImage
        
        if(image.hasImage) {
            imageLoader.load(imageIv, image.imageUrl)
        } else {
            imageLoader.load(imageIv, R.drawable.marvel_comics_placeholder)
        }
    }
    
    
    override fun onRegisterObservables() {
        localViewModel.loadingStateHolder.register(::onLoadingStateChanged)
    }
    
    
    private fun onLoadingStateChanged(isLoading : Boolean) {
        progress_bar.isVisible = isLoading
    }


    override fun onViewModelEvent(event : ViewModelEvent<*>) {
        super.onViewModelEvent(event)
    
        when(event) {
            is EventInfoViewModelEvents.OpenComicsInfoScreen -> event.data?.let(::onOpenComicsInfoScreen)
            is EventInfoViewModelEvents.OpenCharacterInfoScreen -> event.data?.let(::onOpenCharacterInfoScreen)
        }
    }
    
    
    private fun onOpenComicsInfoScreen(comics : Comics) {
        val viewHolder = (getComicsItemViewHolder(comics) ?: return)
        
        navigate(
            R.id.comicsInfoFragmentAction,
            ComicsInfoFragment.newBundle(comics),
            FragmentNavigatorExtras(
                viewHolder.imageIv to comics.sharedImageTransitionName,
                viewHolder.titleTv to comics.sharedTitleTransitionName
            )
        )
    }
    
    
    private fun onOpenCharacterInfoScreen(character : Character) {
        val viewHolder = (getCharacterItemViewHolder(character) ?: return)
        
        navigate(
            R.id.characterInfoFragmentAction,
            CharacterInfoFragment.newBundle(character),
            FragmentNavigatorExtras(
                viewHolder.imageIv to character.sharedImageTransitionName,
                viewHolder.nameTv to character.sharedNameTransitionName
            )
        )
    }
    
    
    private fun getComicsItemViewHolder(comics : Comics) : ComicsItemViewHolder? {
        val index = comicsItemsAdapter.indexOf(ComicsItem(comics))
        
        return if(index != -1) {
            (comicsRecyclerView.findViewHolderForAdapterPosition(index) as ComicsItemViewHolder)
        } else {
            null
        }
    }
    
    
    private fun getCharacterItemViewHolder(character : Character) : CharacterItemViewHolder? {
        val index = characterItemsAdapter.indexOf(CharacterItem(character))
        
        return if(index != -1) {
            (charactersRecyclerView.findViewHolderForAdapterPosition(index) as CharacterItemViewHolder)
        } else {
            null
        }
    }


    override fun getLayoutId() : Int {
        return R.layout.fragment_event_info
    }


    override fun getBindingVariable() : Int {
        return BR.viewModel
    }


    override fun getViewModel() : EventInfoViewModel {
        return localViewModel
    }
    
    
    private val onComicsDataSetChangeListener = object : DatasetChangeListenerAdapter<MutableList<ComicsItem>, ComicsItem>() {
        
        override fun onDatasetSizeChanged(oldSize : Int, newSize : Int) {
            updateComicsContainer()
        }
        
    }
    
    
    private val onCharactersDataSetChangeListener = object : DatasetChangeListenerAdapter<MutableList<CharacterItem>, CharacterItem>() {
        
        override fun onDatasetSizeChanged(oldSize : Int, newSize : Int) {
            updateCharactersContainer()
        }
        
    }


}