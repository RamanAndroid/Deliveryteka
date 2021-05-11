package com.example.deliveryteka.fragments.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.deliveryteka.R
import com.example.deliveryteka.activities.MainActivity
import com.example.deliveryteka.data.models.RequestAccess
import com.example.deliveryteka.data.viewmodel.MedicineListViewModel
import com.example.deliveryteka.databinding.FragmentRegisterBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: MedicineListViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val slots = UnderscoreDigitSlotsParser().parseSlots("+375 (__)__-__-___")
        val formatWatcher: FormatWatcher = MaskFormatWatcher( // форматировать текст будет вот он
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(binding.phoneInput)

        binding.registerBtn.setOnClickListener {

            binding.progressBar.isVisible = true
            if (verifyDataFromUser(
                    binding.phoneInput.text.toString(),
                    binding.passwordInput.text.toString(),
                    binding.passwordRepeatInput.text.toString()
                )
            ) {
                viewModel.sighUp(
                    RequestAccess(
                        binding.phoneInput.text.toString(),
                        binding.passwordInput.text.toString()
                    )

                )
                viewModel.sighUp.observe(viewLifecycleOwner, { response ->
                    if (response[0].user_id.isNotEmpty()) {


                        val sharedPref = requireActivity().getSharedPreferences(
                            Constants.USER_ID,
                            Context.MODE_PRIVATE)
                        sharedPref?.let {
                            with(sharedPref.edit()) {
                                putInt(Constants.USER_ID, response[0].user_id.toInt()).apply()
                            }
                        }

                        binding.progressBar.isVisible = false
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            "Такой номер телефона уже занят!",
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
        phone: String,
        password: String,
        repeatPassword: String
    ): Boolean {
        binding.progressBar.isVisible = false
        return if (phone.length != 18) {
            Toast.makeText(
                requireContext(),
                getString(R.string.enter_number_phone_correctly),
                Toast.LENGTH_LONG
            ).show()
            false
        } else if (password != repeatPassword) {
            Toast.makeText(
                requireContext(),
                getString(R.string.passwords_dont_match),
                Toast.LENGTH_LONG
            ).show()
            false
        } else if (phone.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
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