package com.example.deliveryteka.fragments.basket

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
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

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val layoutDialog =
            LayoutInflater.from(context).inflate(R.layout.confirm_order_layout, null)
        builder.setView(layoutDialog)

        val yesBtn = layoutDialog.findViewById<Button>(R.id.yes_btn)
        val noBtn = layoutDialog.findViewById<Button>(R.id.no_btn)

        val dialog = builder.create()


        binding.cardOrderBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_makeOrderFragment_to_makeBankCardFragment)
        }
        binding.cashOrderBtn.setOnClickListener {
            dialog.show()
            dialog.setCancelable(false)

            dialog.window?.setGravity(Gravity.CENTER)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            yesBtn.setOnClickListener {
                dialog.dismiss()
                binding.cashOrderBtn.findNavController()
                    .navigate(R.id.action_makeOrderFragment_to_medicineListFragment)
            }
            noBtn.setOnClickListener {
                dialog.dismiss()
            }
        }

        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}