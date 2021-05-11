package com.example.deliveryteka.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.databinding.FragmentEditUserAddressBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class EditUserAddressFragment : Fragment() {

    private var _binding: FragmentEditUserAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditUserAddressBinding.inflate(inflater, container, false)

        binding.saveBtn.setOnClickListener {
            if (verifyDataFromUser(
                    binding.nameInput.text.toString(),
                    binding.streetInput.text.toString(),
                    binding.houseNumInput.text.toString(),
                    binding.entranceNumInput.text.toString(),
                    binding.apartmentNumInput.text.toString()
                )
            ) {
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
            Toast.makeText(requireContext(), getString(R.string.not_all_fields_filled), Toast.LENGTH_LONG)
                .show()
            false
        }
    }

}