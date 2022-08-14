package com.codingwithme.currentlocation

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codingwithme.currentlocation.databinding.FragmentAddressDetailBinding
import com.codingwithme.currentlocation.roomDb.AddressDetail
import com.codingwithme.currentlocation.roomDb.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddressDetailFrag : Fragment() {
private var _binding:FragmentAddressDetailBinding?=null
    val binding get() = _binding!!
    lateinit var appDb:AppDataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    _binding=FragmentAddressDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addressSaveBtn.setOnClickListener{
            saveData()
            findNavController().navigate(R.id.action_addressDetail_to_schedulePickup)
        }
    }

    private fun saveData() {
        val firstname=binding.addressFirstname.text.toString()
        val lastname=binding.addressLastname.text.toString()
        val contactnumber=binding.addressContact.text.toString()
        val housenumber=binding.addressHouseno.text.toString()
        val streetname=binding.addressStreet.text.toString()
        val city=binding.addressCity.text.toString()
        val state=binding.addressState.text.toString()
        val pincode=binding.addressPincode.text.toString()
        if(firstname.isNotEmpty()&&lastname.isNotEmpty()&&contactnumber.isNotEmpty()&&housenumber.isNotEmpty()&&streetname.isNotEmpty()&&city.isNotEmpty()&&
            state.isNotEmpty()&&pincode.isNotEmpty()){
            val address=AddressDetail(
                null,
                firstname,
                lastname,
                housenumber,
                contactnumber,
                streetname,
                city,
                state,
                pincode
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDb.addressDao().insert(address)
            }
        }
        else{
            Toast.makeText(requireContext(),"PLease enter all details",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}
