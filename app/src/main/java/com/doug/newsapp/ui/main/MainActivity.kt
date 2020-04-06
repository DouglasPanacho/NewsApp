package com.doug.newsapp.ui.main

import android.os.Bundle
import com.doug.newsapp.R
import com.doug.newsapp.helpers.extensions.FRAG_OPERATION
import com.doug.newsapp.helpers.extensions.executeFragOperation
import com.doug.newsapp.ui.base.BaseActivity
import com.doug.newsapp.ui.base.BaseFragment
import com.doug.newsapp.ui.news.NewsFragment
import com.doug.newsapp.ui.newscontainer.NewsFragmentContainer

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)
        if (supportFragmentManager.fragments.size == 0)
            supportFragmentManager.beginTransaction().executeFragOperation(
                FRAG_OPERATION.REPLACE,
                NewsFragmentContainer.getInstance(),
                NewsFragmentContainer.TAG
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
