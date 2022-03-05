package com.rawtooth.swachta.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.rawtooth.swachta.databinding.ActivityUserDetailBinding

import com.rawtooth.swachta.roomdb.User
import com.rawtooth.swachta.roomdb.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class user_detail : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
        lateinit var allUser:List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            allUser=UserDatabase.getInstance(applicationContext).userDao().display()
            launch(Dispatchers.Main) {
                allUser.forEach{

                    binding.userTv1.text=it.name
                    binding.userTv2.text=it.phonenumber
                    binding.userTv3.text=it.email
                }
            }
        }

//        binding.userTv1.text= obj.name
//        binding.userTv2.text=obj.number
//        binding.userTv3.text=obj.email
    }
}