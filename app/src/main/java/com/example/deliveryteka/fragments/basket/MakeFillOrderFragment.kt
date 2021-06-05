package com.example.deliveryteka.fragments.basket

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentMakeFillOrderBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeFillOrderFragment : Fragment() {


    private var _binding: FragmentMakeFillOrderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaViewModel by viewModels()

    var userId: Int? = 0
    private var payMethodId: Int = 0
    var name = ""
    private var phone = ""
    private var address = ""
    private var comment = ""


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


                name = StringBuilder(userInfo[0].user_name).toString()
                address = StringBuilder(userInfo[0].user_address).toString()
                phone = StringBuilder(userInfo[0].user_phone).toString()
                comment = StringBuilder(binding.orderCommentInput.text.toString()).toString()


            }



            btnChangeOrder.setOnClickListener {

                it.findNavController()
                    .navigate(R.id.action_makeFillOrderFragment_to_makeOrderFragment)
            }

            makeOrder.setOnClickListener {
                showDialogPayMethods(name, address, phone, comment)
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

    private fun showDialogPayMethods(
        name: String,
        address: String,
        phone: String,
        comment: String
    ) {
        val payMethods = arrayOf("Наличными", "Картой")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Способ оплаты")
        builder.setCancelable(false)
        builder.setSingleChoiceItems(
            payMethods, 0
        ) { _, which ->
            payMethodId = which
        }

        builder.setPositiveButton("ОФОРМИТЬ ЗАКАЗ") { dialogs, which ->
            userId?.let { viewModel.addOrder(it, name, address, phone, comment, payMethodId+1) }
            dialogs.dismiss()
            binding.makeOrder.findNavController()
                .navigate(R.id.action_makeFillOrderFragment_to_medicineListFragment)
        }

        builder.setNegativeButton("ОТМЕНИТЬ") { dialogs, which ->
            dialogs.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK)


    }


}