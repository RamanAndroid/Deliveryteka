package com.example.deliveryteka.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deliveryteka.R
import com.example.deliveryteka.databinding.FragmentUserProfileBinding
import com.example.deliveryteka.utility.Utils.Companion.hideKeyboard


class UserProfileFragment : Fragment() {

    private var _binding:FragmentUserProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserProfileBinding.inflate(inflater,container,false)


        hideKeyboard(requireActivity())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}