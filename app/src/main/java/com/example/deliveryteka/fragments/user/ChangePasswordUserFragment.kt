package com.example.deliveryteka.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.databinding.FragmentChangePasswordUserBinding


class ChangePasswordUserFragment : Fragment() {

    private var _binding: FragmentChangePasswordUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChangePasswordUserBinding.inflate(inflater, container, false)

        binding.saveBtn.setOnClickListener {
            if (verifyDataFromUser(
                    binding.currentPasswordInput.text.toString(),
                    binding.newPasswordInput.text.toString(),
                    binding.newPasswordRepeatInput.text.toString()
                )
            ) {
                binding.saveBtn.findNavController().navigate(R.id.action_changePasswordFragment_to_userProfileFragment)
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifyDataFromUser(
        currentPassword: String,
        newPassword: String,
        newPasswordRepeat: String
    ): Boolean {
        return if (currentPassword.isNotEmpty() && newPassword.isNotEmpty() && newPasswordRepeat.isNotEmpty()) {
            true
        } else if (newPassword != newPasswordRepeat) {
            Toast.makeText(requireContext(), getString(R.string.passwords_dont_match), Toast.LENGTH_LONG).show()
            false
        }else {
            Toast.makeText(requireContext(), getString(R.string.not_all_fields_filled), Toast.LENGTH_LONG)
                .show()
            false
        }
    }

}