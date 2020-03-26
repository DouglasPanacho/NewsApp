package com.doug.newsapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment


abstract class BaseFragment :Fragment(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
    }


    /**
     * Set the necessary information for the current fragment toolbar
     */
    abstract fun setupToolbar()

}
