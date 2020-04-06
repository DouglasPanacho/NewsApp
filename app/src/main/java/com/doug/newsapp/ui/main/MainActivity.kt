package com.doug.newsapp.ui.main

import android.os.Bundle
import com.doug.newsapp.R
import com.doug.newsapp.helpers.extensions.FRAG_OPERATION
import com.doug.newsapp.helpers.extensions.executeFragOperation
import com.doug.newsapp.ui.base.BaseActivity
import com.doug.newsapp.ui.base.BaseFragment
import com.doug.newsapp.ui.news.NewsFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.fragments.size == 0)
            supportFragmentManager.beginTransaction().executeFragOperation(
                FRAG_OPERATION.REPLACE,
                NewsFragment.getInstance(),
                NewsFragment.TAG
            )

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
