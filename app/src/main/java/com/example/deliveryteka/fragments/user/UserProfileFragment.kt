package com.example.deliveryteka.fragments.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.deliveryteka.R
import com.example.deliveryteka.SplashCentered
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentUserProfileBinding
import com.example.deliveryteka.utility.Constants
import com.example.deliveryteka.utility.Utils.Companion.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaViewModel by viewModels()

    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        userId?.let { viewModel.getUserInfo(it) }

        binding.apply {
            viewModel.getUserInfo.observe(viewLifecycleOwner) {

                val userInfo = it.result

                profileName.text = checkData(userInfo[0].user_name, getString(R.string.your_name))
                profileAddress.text =
                    checkData(userInfo[0].user_address, getString(R.string.your_address))
                profileNumberPhone.text =
                    checkData(userInfo[0].user_phone, getString(R.string.your_number_phone))
                profileMedicineCart.text = checkData(
                    userInfo[0].med_card_number,
                    getString(R.string.not_specified_medicine_card)
                )
            }

            signOutBtn.setOnClickListener {
                sharedPref?.let {
                    with(sharedPref.edit()) {
                        putString(Constants.USER_PASSWORD, "").apply()
                    }
                }
                val intent = Intent(requireContext(), SplashCentered::class.java)
                startActivity(intent)
            }
        }

        hideKeyboard(requireActivity())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkData(string: String?, text: String?): String {
        return if (string.isNullOrBlank()) {
            text.toString()
        } else string
    }

}