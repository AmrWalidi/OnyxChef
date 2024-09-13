package com.example.android.chefapp.bindingUtils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.android.chefapp.R

@BindingAdapter("setStatus")
fun View.setStatus(status: String) {
    background = when (status) {
        "New" -> ContextCompat.getDrawable(this.context, R.color.new_color)
        "Change" -> ContextCompat.getDrawable(this.context, R.color.primaryColor)
        else -> ContextCompat.getDrawable(this.context, R.color.delayed_color)
    }
}