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
import com.arthurivanets.commons.ktx.selectedAndReleased
import com.arthurivanets.commons.ktx.statusBarSize
import com.arthurivanets.commons.ktx.updatePadding
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.dashboard.DashboardViewPagerAdapter
import com.arthurivanets.sample.ui.base.BaseFragment
import com.arthurivanets.sample.ui.characters.list.CharactersFragment
import com.arthurivanets.sample.ui.comics.list.ComicsFragment
import com.arthurivanets.sample.ui.events.list.EventsFragment
import com.arthurivanets.sample.ui.util.extensions.attemptScrollToTop
import com.arthurivanets.sample.ui.util.extensions.isEmpty
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.view_toolbar.*

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {


    private lateinit var pagerAdapter : DashboardViewPagerAdapter


    override fun init(savedInstanceState : Bundle?) {
        super.init(savedInstanceState)
        
        initToolbar()
        initViewPager()
        initBottomBar()
    }


    private fun initToolbar() {
        toolbar.updatePadding(paddingTop = context!!.statusBarSize)
        toolbar_title.setText(R.string.toolbar_title_dashboard)
    }


    private fun initViewPager() {
        with(viewPager) {
            adapter = initPagerAdapter()
            offscreenPageLimit = pagerAdapter.count
            isSwipeable = false
        }
    }
    
    
    private fun initPagerAdapter() : DashboardViewPagerAdapter {
        return DashboardViewPagerAdapter(childFragmentManager).apply {
            addFragment(ComicsFragment())
            addFragment(EventsFragment())
            addFragment(CharactersFragment())
        }.also { pagerAdapter = it }
    }


    private fun initBottomBar() {
        with(bottomBar) {
            inflateMenu(R.menu.dashboard_navigation_menu)
            itemIconTintList = selectedAndReleased(
                getColorCompat(R.color.navigation_item_color_selected_state),
                getColorCompat(R.color.navigation_item_color_released_state)
            )
            itemTextColor = selectedAndReleased(
                getColorCompat(R.color.navigation_item_color_selected_state),
                getColorCompat(R.color.navigation_item_color_released_state)
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
        if(!pagerAdapter.isEmpty) {
            pagerAdapter.getFragment(getPageIndexForMenuItem(item))?.attemptScrollToTop(false)
        }
    }


    private fun getPageIndexForMenuItem(item : MenuItem) : Int {
        return when(item.itemId) {
            R.id.navigation_item_events -> 1
            R.id.navigation_item_characters -> 2
            else -> 0
        }
    }


}