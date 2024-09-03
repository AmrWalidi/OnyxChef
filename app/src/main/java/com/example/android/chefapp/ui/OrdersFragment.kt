package com.example.android.chefapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentOrdersBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_orders, container, false)
        return binding.root
    }
}