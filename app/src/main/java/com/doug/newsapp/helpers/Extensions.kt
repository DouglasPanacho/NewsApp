package com.doug.newsapp.helpers

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.doug.newsapp.R
import com.doug.newsapp.helpers.datehelper.DateHelper
import com.doug.newsapp.ui.base.BaseFragment

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


sealed class FragOperation {
    class AddOperation : FragOperation() {
        val enterAnim: Int = R.anim.slide_in_right
        val exitAnim: Int = android.R.anim.fade_in
        val popEnterAnim: Int = android.R.anim.fade_out
        val popExitAnim: Int = R.anim.slide_down_fade_out
    }

    class RemoveOperation : FragOperation() {
        val enterAnim: Int = android.R.anim.fade_out
        val exitAnim: Int = R.anim.slide_down_fade_out
    }

    object ReplaceOperation : FragOperation()
}

fun FragmentTransaction.executeFragOperation(
    operation: FragOperation,
    fragment: BaseFragment,
    tag: String
) {
    when (operation) {
        is FragOperation.AddOperation -> setCustomAnimations(
            operation.enterAnim, operation.exitAnim, operation.popEnterAnim, operation.popExitAnim
        )
            .addToBackStack(tag)
            .add(R.id.container, fragment, tag)
        is FragOperation.RemoveOperation -> setCustomAnimations(operation.enterAnim,operation.exitAnim).remove(fragment)
        else -> replace(R.id.container, fragment)
    }
    commit()
}


fun String.toDate(): String = DateHelper.getPassedTime(this)


