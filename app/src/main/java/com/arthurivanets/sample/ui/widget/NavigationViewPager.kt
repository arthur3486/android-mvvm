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

package com.arthurivanets.sample.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.arthurivanets.sample.ui.util.extensions.detachFromParent
import com.arthurivanets.sample.ui.util.extensions.forEachFragment

class NavigationViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    var isSwipeable = true

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        // Due to the fact that the Navigation Library (as of version 2.1.0) doesn't provide a way
        // to specify the Fragment View Handling Mode [RECREATE/RETAIN] (for the fragments put into stack),
        // certain steps need to be taken on our side in order to achieve the Fragment View retention;
        // as suggested by the library developer, we must handle it ourselves
        // on the side of the Fragments; but here's a catch, if we do that, due to the way
        // the library handles the Fragment View attachment/detachment the ViewPager widget gets
        // negatively affected; when the ViewPager gets re-attached to the window, its
        // children (i.e. Fragment Views) get re-associated with the parent container, even
        // though they've been associated with it previously, which causes an exception.
        // So, in order to prevent this exception, we must ensure that ViewPager's
        // children get detached from their parent container when the ViewPager gets detached
        // from the window. This is only applicable to the ViewPager widgets that are used
        // in conjunction with the Navigation Library.
        detachFragmentViewsIfNecessary()
    }

    private fun detachFragmentViewsIfNecessary() {
        if (adapter is FragmentPagerAdapter) {
            (adapter as FragmentPagerAdapter).forEachFragment { it.view?.detachFromParent() }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return (isSwipeable && super.onInterceptTouchEvent(ev))
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return (isSwipeable && super.onTouchEvent(ev))
    }

}
