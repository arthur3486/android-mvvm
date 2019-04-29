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

package com.arthurivanets.sample.ui.dashboard

import android.os.Bundle
import android.view.MenuItem
import com.arthurivanets.commons.ktx.getColorCompat
import com.arthurivanets.commons.ktx.statusBarSize
import com.arthurivanets.commons.ktx.updatePadding
import com.arthurivanets.mvvm.BR
import com.arthurivanets.mvvm.navigation.dagger.MvvmFragment
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.dashboard.DashboardViewPagerAdapter
import com.arthurivanets.sample.databinding.FragmentDashboardBinding
import com.arthurivanets.sample.ui.characters.list.CharactersFragment
import com.arthurivanets.sample.ui.comics.list.ComicsFragment
import com.arthurivanets.sample.ui.events.list.EventsFragment
import com.arthurivanets.sample.ui.util.extensions.attemptScrollToTop
import com.arthurivanets.sample.ui.util.extensions.isEmpty
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class DashboardFragment : MvvmFragment<FragmentDashboardBinding, DashboardViewModel>() {


    @Inject
    lateinit var localViewModel : DashboardViewModel

    private lateinit var adapter : DashboardViewPagerAdapter


    override fun init(savedInstanceState : Bundle?) {
        super.init(savedInstanceState)
        
        initToolbar()
        initAdapter()
        initViewPager()
        initBottomBar()
    }


    private fun initToolbar() {
        toolbar.updatePadding(paddingTop = context!!.statusBarSize)
        toolbar_title.setText(R.string.toolbar_title_dashboard)
    }


    private fun initAdapter() {
        adapter = DashboardViewPagerAdapter(childFragmentManager).apply {
            addFragment(ComicsFragment())
            addFragment(EventsFragment())
            addFragment(CharactersFragment())
        }
    }


    private fun initViewPager() {
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        viewPager.isSwipeable = false
    }


    private fun initBottomBar() {
        with(bottomBar) {
            inflateMenu(com.arthurivanets.sample.R.menu.dashboard_navigation_menu)
            itemIconTintList = com.arthurivanets.commons.ktx.selectedAndReleased(
                getColorCompat(com.arthurivanets.sample.R.color.navigation_item_color_selected_state),
                getColorCompat(com.arthurivanets.sample.R.color.navigation_item_color_released_state)
            )
            itemTextColor = com.arthurivanets.commons.ktx.selectedAndReleased(
                getColorCompat(com.arthurivanets.sample.R.color.navigation_item_color_selected_state),
                getColorCompat(com.arthurivanets.sample.R.color.navigation_item_color_released_state)
            )
            setOnNavigationItemSelectedListener(::onNavigationItemSelected)
            setOnNavigationItemReselectedListener(::onNavigationItemReselected)
        }
    }


    private fun onNavigationItemSelected(item : MenuItem) : Boolean {
        viewPager.setCurrentItem(getPageIndexForMenuItem(item), false)
        return true
    }


    private fun onNavigationItemReselected(item : MenuItem) {
        if(!adapter.isEmpty) {
            adapter.getItem(getPageIndexForMenuItem(item)).attemptScrollToTop(false)
        }
    }


    private fun getPageIndexForMenuItem(item : MenuItem) : Int {
        return when(item.itemId) {
            R.id.navigation_item_events -> 1
            R.id.navigation_item_characters -> 2
            else -> 0
        }
    }


    override fun getLayoutId() : Int {
        return R.layout.fragment_dashboard
    }


    override fun getBindingVariable() : Int {
        return BR.viewModel
    }


    override fun getViewModel() : DashboardViewModel {
        return localViewModel
    }


}