package com.rawtooth.socialX

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import socialX.R
import socialX.databinding.FragmentFragmentinBinding
import socialX.databinding.FragmentFragmentupBinding


class Fragmentup : Fragment(), View.OnClickListener {
private var upBinding:FragmentFragmentupBinding?=null
private val binding get() = upBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        upBinding= FragmentFragmentupBinding.inflate(inflater,container,false)
        binding.registerbtn.setOnClickListener(this)
        return binding.root

    }

    override fun onClick(p0: View?) {
        val name=binding.username.text.toString()
        val email = binding.email.text.toString()
        val contact = binding.contact.text.toString()
        val pass = binding.password.text.toString()
        val check: Boolean = vali(name, email, contact, pass)
        if(check){
            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(), "Enter valid data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun vali(name: String, email: String, contact: String, pass: String): Boolean {
        if (name.isEmpty()) {
            binding.username.requestFocus()
            binding.username.setError("Field cannot be empty")
            return false
        } else if (email.isEmpty()) {
            binding.email.requestFocus()
            binding.email.setError("Field cannot be empty")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.requestFocus()
            binding.email.setError("Enter a valid email")
            return false

        } else if (contact.length < 10 || contact.length > 11 || contact == null) {
            binding.contact.requestFocus()
            binding.contact.setError("Contact number must be equal to 10")
            return false
        } else if (!Patterns.PHONE.matcher(contact).matches()) {
            binding.contact.requestFocus()
            binding.contact.setError("Enter valid contact details")
            return false
        } else if (pass.length < 5) {
            binding.password.requestFocus()
            binding.password.setError("Password must be greater than 5 characters")
            return false

        } else {
            return true
        }

    }

    }
