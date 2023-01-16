package com.benyfrabasa.app.presentation.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.benyfrabasa.app.presentation.dashboard.news.NewsFragment
import com.benyfrabasa.app.presentation.dashboard.photo.PhotoFragment
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    val fragments: List<String> = listOf(NEWS_FRAGMENT, PHOTO_FRAGMENT)

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (fragments[position]) {
            NEWS_FRAGMENT -> NewsFragment()
            PHOTO_FRAGMENT -> PhotoFragment()
            else -> NewsFragment()
        }
    }

    companion object {
        private const val NEWS_FRAGMENT = "News"
        private const val PHOTO_FRAGMENT = "Photo"
    }
}
