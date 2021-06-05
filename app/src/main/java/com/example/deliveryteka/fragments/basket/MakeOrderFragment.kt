package com.example.deliveryteka.fragments.basket

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentMakingOrderBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


@AndroidEntryPoint
class MakeOrderFragment : Fragment() {

    private var _binding: FragmentMakingOrderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaViewModel by viewModels()

    var userId: Int? = 0
    var payMethodId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMakingOrderBinding.inflate(inflater, container, false)

        val slots = UnderscoreDigitSlotsParser().parseSlots("+375 (__)__-__-___")
        val formatWatcher: FormatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(binding.phoneInput)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)




        binding.makeOrder.setOnClickListener {
            if (verifyDataFromUser(
                    binding.nameInput.text.toString(),
                    binding.streetInput.text.toString(),
                    binding.houseNumInput.text.toString(),
                    binding.entranceNumInput.text.toString(),
                    binding.apartmentNumInput.text.toString()
                )
            ) {

                val name = StringBuilder(binding.nameInput.text.toString()).toString()
                val address =
                    StringBuilder(
                        "ул.${binding.streetInput.text.toString()}" +
                                " дом ${binding.houseNumInput.text.toString()}" +
                                " кв. ${binding.apartmentNumInput.text.toString()}" +
                                " подъезд ${binding.entranceNumInput.text.toString()}"
                    ).toString()
                val phone = StringBuilder(binding.phoneInput.text.toString()).toString()
                val comment = StringBuilder(binding.orderCommentInput.text.toString()).toString()

                showDialogPayMethods(name, address, phone, comment)
            }


        }

        return binding.root


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
                .navigate(R.id.action_makeOrderFragment_to_medicineListFragment)
        }

        builder.setNegativeButton("ОТМЕНИТЬ") { dialogs, which ->
            dialogs.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK)


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