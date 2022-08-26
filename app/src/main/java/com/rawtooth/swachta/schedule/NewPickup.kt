package com.rawtooth.swachta.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rawtooth.swachta.databinding.ActivityNewPickupBinding

class NewPickup : AppCompatActivity() {
    lateinit var binding:ActivityNewPickupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewPickupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //1
        binding.type1.setOnClickListener {
            val message=binding.type1.text.toString()
            passDetails(message)
        }
        binding.type2.setOnClickListener {
            val message=binding.type2.text.toString()
            passDetails(message)
        }
        binding.type3.setOnClickListener {
            val message=binding.type3.text.toString()
            passDetails(message)
        }
        binding.type4.setOnClickListener {
            val message=binding.type4.text.toString()
            passDetails(message)
        }
        binding.otherType.setOnClickListener {
           startActivity(Intent(this@NewPickup, FetchLocation::class.java))
        }

    }

    private fun passDetails(message:String) {
        val intent = Intent(this@NewPickup, FetchLocation::class.java)
        intent.putExtra("message_key", message)
        startActivity(intent)
    }
}