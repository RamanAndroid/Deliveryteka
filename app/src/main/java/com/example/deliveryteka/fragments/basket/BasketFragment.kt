package com.example.deliveryteka.fragments.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.databinding.FragmentBasketBinding
import com.example.deliveryteka.utility.Utils


class BasketFragment : Fragment() {


    private var _binding:FragmentBasketBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater,container,false)

        binding.makeOrderBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_basketFragment_to_makeOrderFragment)
        }

        Utils.hideKeyboard(requireActivity())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}