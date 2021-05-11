package com.example.deliveryteka.fragments.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.deliveryteka.R
import com.example.deliveryteka.databinding.FragmentMakingOrderBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class MakeOrderFragment : Fragment() {

    private var _binding: FragmentMakingOrderBinding? = null
    private val binding get() = _binding!!


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

        binding.cardOrderBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_makeOrderFragment_to_makeBankCardFragment)
        }
        binding.cashOrderBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_makeOrderFragment_to_basketFragment)
        }

        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}