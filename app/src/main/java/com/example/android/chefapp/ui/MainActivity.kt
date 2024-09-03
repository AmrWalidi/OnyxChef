package com.example.android.chefapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.chefapp.R
import com.example.android.chefapp.databinding.ActivityMainBinding
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.data.request.RequestBody
import com.example.android.chefapp.network.data.request.user.UserRequestValue
import com.example.android.chefapp.network.data.response.user.UserResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        OnyxRmsApi.userRetrofitService.getUserDetails(RequestBody(UserRequestValue(password = "123456"))).enqueue( object:
            Callback<UserResponseData> {
            override fun onFailure(call: Call<UserResponseData>, t: Throwable) {
                 "Failure: " + t.message
            }

            override fun onResponse(call: Call<UserResponseData>, response: Response<UserResponseData>) {
                binding.user = response.body()?.userData
            }
        })
    }
}