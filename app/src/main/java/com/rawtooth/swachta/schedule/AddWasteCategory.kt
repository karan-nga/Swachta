package com.rawtooth.swachta.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rawtooth.swachta.databinding.ActivityAddWasteCategoryBinding

class AddWasteCategory : AppCompatActivity() {
    lateinit var binding:ActivityAddWasteCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityAddWasteCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}