package com.doug.newsapp.ui.main

import android.os.Bundle
import com.doug.newsapp.R
import com.doug.newsapp.helpers.FragOperation
import com.doug.newsapp.helpers.executeFragOperation
import com.doug.newsapp.ui.base.BaseActivity
import com.doug.newsapp.ui.base.BaseFragment
import com.doug.newsapp.ui.news.NewsFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.fragments.size == 0)
            supportFragmentManager.beginTransaction().executeFragOperation(
                FragOperation.ReplaceOperation,
                NewsFragment.getInstance(),
                NewsFragment.TAG
            )

    }

}
