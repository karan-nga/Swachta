package com.rawtooth.socialX

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import socialX.R
import socialX.databinding.FragmentFragmentinBinding


class Fragmentin : Fragment() {
private var inBinding:FragmentFragmentinBinding?=null
    private val binding get() = inBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        inBinding= FragmentFragmentinBinding.inflate(inflater,container,false)
        binding.google.setOnClickListener{
            Toast.makeText(requireContext(),"Google",Toast.LENGTH_SHORT).show()
        }
        binding.fb.setOnClickListener{
            Toast.makeText(requireContext(),"Facebook",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}

