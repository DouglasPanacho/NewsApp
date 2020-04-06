package com.doug.newsapp.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.doug.newsapp.R
import com.doug.newsapp.ui.base.BaseActivity
import com.doug.newsapp.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var viewPagerAdapter: MainViewPagerAdapter
    lateinit var countries: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)
        setupViewPagerTabs()
    }

    private fun setupViewPagerTabs() {
        countries = resources.getStringArray(R.array.countries_news)
        viewPagerAdapter = MainViewPagerAdapter(countries, supportFragmentManager, lifecycle)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = countries[position]
        }.attach()
    }


    /**
    Used to control the fragment backstack
     */
    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1] is BaseFragment
        ) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
