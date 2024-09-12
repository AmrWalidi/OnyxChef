package com.example.android.chefapp.bindingUtils

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.android.chefapp.R

@BindingAdapter("setStatus")
fun ImageView.setStatus(status: String) {
    setImageResource(
        when (status) {
            "New" -> R.drawable.ic_new
            "Change" -> R.drawable.ic_changed
            else -> R.drawable.ic_delayed
        }
    )
}

@BindingAdapter("setStatus")
fun ConstraintLayout.setStatus(status: String) {
    setBackgroundColor(
        when (status) {
            "New" -> R.color.new_background_color
            "Change" -> R.color.changed_color
            else -> R.color.delayed_background_color
        }
    )
}

@BindingAdapter("setStatus")
fun TextView.setStatus(status: String) {
    setTextColor(
        when (status) {
            "New" -> R.color.new_color
            "Change" -> R.color.primaryColor
            else -> R.color.delayed_color
        }
    )
}