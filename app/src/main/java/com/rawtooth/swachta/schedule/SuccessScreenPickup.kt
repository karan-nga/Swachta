package com.rawtooth.swachta.schedule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rawtooth.swachta.MainActivity
import com.rawtooth.swachta.databinding.ActivitySuccessScreenPickupBinding

class SuccessScreenPickup : AppCompatActivity() {
    lateinit var binding: ActivitySuccessScreenPickupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySuccessScreenPickupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lastpickupscreen.setOnClickListener{
            val i = Intent(this@SuccessScreenPickup,MainActivity::class.java)

            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            finish()
            startActivity(i)
        }

    }
}