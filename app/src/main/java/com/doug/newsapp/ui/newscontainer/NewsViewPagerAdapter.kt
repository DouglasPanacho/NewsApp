package com.doug.newsapp.ui.newscontainer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doug.newsapp.ui.news.NewsFragment

class NewsViewPagerAdapter(
    private val array: Array<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return array.size
    }

    override fun createFragment(position: Int): Fragment {
        return NewsFragment.getInstance(array[position])
    }
}
