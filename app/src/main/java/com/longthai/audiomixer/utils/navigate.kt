package com.longthai.audiomixer.utils

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigate(resId: Int, bundle: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(resId, bundle)
}

fun Activity.navigate(view: Int, resId: Int, bundle: Bundle? = null) {
    Navigation.findNavController(this, view).navigate(resId, bundle)
}