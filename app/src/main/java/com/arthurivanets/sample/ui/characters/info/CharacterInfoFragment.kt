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

package com.arthurivanets.sample.ui.characters.info

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
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.adapters.comics.ComicsItemResources
import com.arthurivanets.sample.adapters.comics.ComicsItemViewHolder
import com.arthurivanets.sample.adapters.comics.ComicsItemsRecyclerViewAdapter
import com.arthurivanets.sample.databinding.FragmentCharacterInfoBinding
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
import kotlinx.android.synthetic.main.view_character_info_app_bar_content.*
import kotlinx.android.synthetic.main.view_character_info_content.*
import kotlinx.android.synthetic.main.view_item_empty_view.*
import kotlinx.android.synthetic.main.view_progress_bar_horizontal.*
import javax.inject.Inject

class CharacterInfoFragment : BaseMvvmFragment<FragmentCharacterInfoBinding, CharacterInfoViewModel>(R.layout.fragment_character_info) {

    private val args by navArgs<CharacterInfoFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var comicsItemResources: ComicsItemResources

    private lateinit var comicsItemsAdapter: ComicsItemsRecyclerViewAdapter

    override fun init(savedInstanceState: Bundle?) {
        val character = args.character
        viewModel.character = character

        initAppBar(character)
        initComicsRecyclerView()
        initTransitionViews(character)
        updateComicsContainer()
    }

    private fun initAppBar(character: Character) {
        initCollapsingToolbar()
        initToolbar()
        loadHeaderImage(character.thumbnail)
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
            layoutManager = initLayoutManager()
            adapter = initAdapter()
        }
    }

    private fun initLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
    }

    private fun initAdapter(): ComicsItemsRecyclerViewAdapter {
        return ComicsItemsRecyclerViewAdapter(
            context = context!!,
            items = viewModel.comicsItems,
            resources = comicsItemResources
        ).apply {
            addOnDatasetChangeListener(onDataSetChangeListener)
            onItemClickListener = onItemClick { viewModel.onComicsClicked(it.itemModel) }
        }.also { comicsItemsAdapter = it }
    }

    private fun initTransitionViews(character: Character) {
        imageContainer.transitionName = character.sharedImageTransitionName
        nameTv.transitionName = character.sharedNameTransitionName
    }

    private fun updateComicsContainer() {
        val isComicsContainerVisible = !comicsItemsAdapter.isEmpty()

        comicsLabelTv.isVisible = isComicsContainerVisible
        comicsRecyclerView.isVisible = isComicsContainerVisible
    }

    override fun postInit() {
        super.postInit()

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun loadHeaderImage(image: Image) {
        emptyViewTv.isVisible = !image.hasImage

        if (image.hasImage) {
            imageLoader.load(imageIv, image.imageUrl)
        } else {
            imageLoader.load(imageIv, R.drawable.marvel_character_placeholder)
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
            is MarvelRoutes.ComicsInfoScreen -> onOpenComicsInfoScreen(route.comics)
        }
    }

    private fun onOpenComicsInfoScreen(comics: Comics) {
        val viewHolder = (getItemViewHolder(comics) ?: return)

        navigate(
            directions = CharacterInfoFragmentDirections.comicsInfoFragmentAction(comics),
            navigationExtras = FragmentNavigatorExtras(
                viewHolder.imageIv to comics.sharedImageTransitionName,
                viewHolder.titleTv to comics.sharedTitleTransitionName
            )
        )
    }

    private fun getItemViewHolder(comics: Comics): ComicsItemViewHolder? {
        val index = comicsItemsAdapter.indexOf(ComicsItem(comics))

        return if (index != -1) {
            (comicsRecyclerView.findViewHolderForAdapterPosition(index) as ComicsItemViewHolder)
        } else {
            null
        }
    }

    private val onDataSetChangeListener = object : DatasetChangeListenerAdapter<MutableList<ComicsItem>, ComicsItem>() {

        override fun onDatasetSizeChanged(oldSize: Int, newSize: Int) {
            updateComicsContainer()
        }

    }

}
