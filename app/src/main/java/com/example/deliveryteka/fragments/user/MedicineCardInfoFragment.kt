package com.example.deliveryteka.fragments.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentMedicineCardInfoBinding
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


@AndroidEntryPoint
class MedicineCardInfoFragment : Fragment() {

    private var _binding: FragmentMedicineCardInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeliverytekaViewModel by viewModels()
    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedicineCardInfoBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        val slots = UnderscoreDigitSlotsParser().parseSlots("____-____-____-____")
        val formatWatcher: FormatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(binding.cardCodeInput)

        binding.saveBtn.setOnClickListener {
            if (verifyDataFromUser(binding.cardCodeInput.text.toString())) {

                val medCardNumber = StringBuilder(binding.cardCodeInput.text.toString()).toString()

                userId?.let { userId -> viewModel.updateUserMedCard(userId, medCardNumber) }


                findNavController().navigate(R.id.action_medicineCardInfoFragment_to_userProfileFragment)
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifyDataFromUser(
        medCardNumber: String
    ): Boolean {
        return if (medCardNumber.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.not_all_fields_filled),
                Toast.LENGTH_LONG
            )
                .show()
            false
        } else if (medCardNumber.length < 19) {
            Toast.makeText(
                requireContext(),
                "Номер медецинской карты должен быть не меньше 16 символов!",
                Toast.LENGTH_LONG
            ).show()
            false
        } else {
            true
        }
    }

}