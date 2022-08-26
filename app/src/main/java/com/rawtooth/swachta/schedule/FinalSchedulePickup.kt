package com.rawtooth.swachta.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rawtooth.swachta.databinding.ActivityFinalSchedulePickupBinding

class FinalSchedulePickup : AppCompatActivity() {
    lateinit var  binding:ActivityFinalSchedulePickupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityFinalSchedulePickupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}