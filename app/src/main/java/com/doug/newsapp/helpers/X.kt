package com.doug.newsapp.helpers

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.doug.newsapp.R
import com.doug.newsapp.helpers.datehelper.DateHelper
import com.doug.newsapp.ui.base.BaseFragment

fun Context.showToast() {
    Toast.makeText(this, "teste", Toast.LENGTH_LONG).show()
}


enum class FRAG_OPERATION {
    ADD, REMOVE, REPLACE
}

fun FragmentTransaction.executeFragOperation(
    operation: FRAG_OPERATION,
    fragment: BaseFragment,
    tag: String = ""
) {
    when (operation) {
        FRAG_OPERATION.ADD -> setCustomAnimations(
            R.anim.slide_in_right, android.R.anim.fade_in, android.R.anim.fade_out,
            R.anim.slide_down_fade_out
        )
            .addToBackStack(tag)
            .add(R.id.container, fragment, fragment.tag)
        FRAG_OPERATION.REMOVE -> setCustomAnimations(
            android.R.anim.fade_out,
            R.anim.slide_down_fade_out

        ).remove(fragment)
        else -> replace(R.id.container, fragment)
    }
    commit()
}


fun String.toDate(): String = DateHelper.getPassedTime(this)


