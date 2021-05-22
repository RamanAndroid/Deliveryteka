package com.example.deliveryteka.fragments.basket

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentBasketBinding
import com.example.deliveryteka.databinding.FragmentMakeFillOrderBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeFillOrderFragment : Fragment() {


    private var _binding: FragmentMakeFillOrderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaViewModel by viewModels()

    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeFillOrderBinding.inflate(inflater, container, false)

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

            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val layoutDialog =
                LayoutInflater.from(context).inflate(R.layout.confirm_order_layout, null)
            builder.setView(layoutDialog)

            val yesBtn = layoutDialog.findViewById<Button>(R.id.yes_btn)
            val noBtn = layoutDialog.findViewById<Button>(R.id.no_btn)

            val dialog = builder.create()

            btnChangeOrder.setOnClickListener {


                it.findNavController()
                    .navigate(R.id.action_makeFillOrderFragment_to_makeOrderFragment)
            }

            cardOrderBtn.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_makeFillOrderFragment_to_makeBankCardFragment)
            }

            cashOrderBtn.setOnClickListener {
                dialog.show()
                dialog.setCancelable(false)

                dialog.window?.setGravity(Gravity.CENTER)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                yesBtn.setOnClickListener {
                    dialog.dismiss()
                    binding.cardOrderBtn.findNavController()
                        .navigate(R.id.action_makeFillOrderFragment_to_medicineListFragment)
                }
                noBtn.setOnClickListener {
                    dialog.dismiss()
                }

            }
        }


        return binding.root
    }

    private fun checkData(string: String?, text: String?): String {
        return if (string.isNullOrBlank()) {
            text.toString()
        } else string
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}