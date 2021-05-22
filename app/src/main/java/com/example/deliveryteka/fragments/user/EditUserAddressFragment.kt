package com.example.deliveryteka.fragments.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentEditUserAddressBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserAddressFragment : Fragment() {

    private var _binding: FragmentEditUserAddressBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeliverytekaViewModel by viewModels()
    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditUserAddressBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        binding.saveBtn.setOnClickListener {
            if (verifyDataFromUser(
                    binding.nameInput.text.toString(),
                    binding.streetInput.text.toString(),
                    binding.houseNumInput.text.toString(),
                    binding.entranceNumInput.text.toString(),
                    binding.apartmentNumInput.text.toString()
                )
            ) {
                val address =
                    StringBuilder(
                        "ул.${binding.streetInput.text.toString()}" +
                                " дом ${binding.houseNumInput.text.toString()}" +
                                " кв. ${binding.apartmentNumInput.text.toString()}" +
                                " подъезд ${binding.entranceNumInput.text.toString()}"
                    )
                val name = StringBuilder(binding.nameInput.text.toString())
                userId?.let { userId ->
                    viewModel.updateUserInfo(
                        userId,
                        name.toString(),
                        address.toString()
                    )
                }

                binding.saveBtn.findNavController()
                    .navigate(R.id.action_editProfileFragment_to_userProfileFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifyDataFromUser(
        name: String,
        street: String,
        house: String,
        entrance: String,
        apartment: String
    ): Boolean {
        return if (name.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && entrance.isNotEmpty() && apartment.isNotEmpty()) {
            true
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.not_all_fields_filled),
                Toast.LENGTH_LONG
            )
                .show()
            false
        }
    }

}