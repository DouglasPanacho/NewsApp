package com.doug.newsapp.ui.newscontainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.doug.newsapp.R
import com.doug.newsapp.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_news_container.*

class NewsFragmentContainer : BaseFragment() {

    private lateinit var viewPagerAdapter: NewsViewPagerAdapter
    private lateinit var countries: Array<String>

    companion object{
        const val TAG = "NewsContainer"

        fun getInstance(): NewsFragmentContainer {
            return NewsFragmentContainer()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_news_container,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPagerTabs()

    }

    private fun setupViewPagerTabs() {
        countries = resources.getStringArray(R.array.countries_news)
        viewPagerAdapter = NewsViewPagerAdapter(countries, fragmentManager!!, lifecycle)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = countries[position]
        }.attach()
    }

}
