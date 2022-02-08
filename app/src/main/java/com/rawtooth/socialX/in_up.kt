package com.rawtooth.socialX

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import socialX.R
import socialX.databinding.ActivityInUpBinding


class in_up : AppCompatActivity() {
    lateinit var inupBinding: ActivityInUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inupBinding= ActivityInUpBinding.inflate(layoutInflater)
        setContentView(inupBinding.root)
        inupBinding.signin.setOnClickListener{
            changeFragment(Fragmentin())
        }
        inupBinding.signup.setOnClickListener{
            changeFragment(Fragmentup())
        }
    }


    private fun changeFragment(fragment:Fragment) {
        val fagManger=supportFragmentManager
        val fagTraction=fagManger.beginTransaction()
        fagTraction.replace(R.id.fragments,fragment)
        fagTraction.commit()
    }
}