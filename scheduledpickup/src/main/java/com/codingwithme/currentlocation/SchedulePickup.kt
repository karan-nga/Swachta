package com.codingwithme.currentlocation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithme.currentlocation.databinding.FragmentAddressDetailBinding
import com.codingwithme.currentlocation.databinding.FragmentSchedulePickupBinding
import com.codingwithme.currentlocation.roomDb.ViewModelDb


class SchedulePickup : Fragment() {
private var _binding:FragmentSchedulePickupBinding?=null
    val binding get() = _binding!!
lateinit var recyclerViewAdapter: Adapter
lateinit var viewModel:ViewModelDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSchedulePickupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter= Adapter()
            adapter=recyclerViewAdapter
        }
        viewModel= ViewModelProviders.of(SchedulePickup())[ViewModelDb::class.java]
        viewModel.getAllUserOnservers().observe(viewLifecycleOwner, Observer {
                recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })
    }


}