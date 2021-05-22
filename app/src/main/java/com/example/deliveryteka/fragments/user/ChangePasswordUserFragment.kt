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
import com.example.deliveryteka.databinding.FragmentChangePasswordUserBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePasswordUserFragment : Fragment() {

    private var _binding: FragmentChangePasswordUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeliverytekaViewModel by viewModels()
    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChangePasswordUserBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        binding.saveBtn.setOnClickListener {
            if (verifyDataFromUser(
                    binding.currentPasswordInput.text.toString(),
                    binding.newPasswordInput.text.toString(),
                    binding.newPasswordRepeatInput.text.toString()
                )
            ) {

                val currentPassword =
                    StringBuilder(binding.currentPasswordInput.text.toString()).toString()
                val newPassword = StringBuilder(binding.newPasswordInput.text.toString()).toString()

                userId?.let { userId ->
                    viewModel.updateUserPassword(
                        userId,
                        currentPassword,
                        newPassword
                    )
                }


                viewModel.login.observe(viewLifecycleOwner, { response ->
                    if (response[0].user_id.isNotEmpty()) {

                        Toast.makeText(
                            requireContext(),
                            "Пароль успешно был изменён!",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.saveBtn.findNavController()
                            .navigate(R.id.action_changePasswordFragment_to_userProfileFragment)

                        binding.saveBtn.isEnabled = false
                        Thread.sleep(1000)
                        binding.saveBtn.isEnabled = true
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Вы неправильно ввели старый пароль!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
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
        return if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordRepeat.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.not_all_fields_filled),
                Toast.LENGTH_LONG
            )
                .show()
            false
        } else if (newPassword != newPasswordRepeat) {
            Toast.makeText(
                requireContext(),
                getString(R.string.passwords_dont_match),
                Toast.LENGTH_LONG
            ).show()
            false
        } else if (currentPassword == newPassword && currentPassword == newPasswordRepeat) {
            Toast.makeText(
                requireContext(),
                "Новый пароль не может быть похож на старый!",
                Toast.LENGTH_LONG
            ).show()
            false
        } else {
            true
        }
    }

}