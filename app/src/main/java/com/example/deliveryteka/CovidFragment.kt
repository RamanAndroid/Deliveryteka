package com.example.deliveryteka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentCovidBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZoneId
import java.time.ZonedDateTime

@AndroidEntryPoint
class CovidFragment : Fragment() {

    private var _binding: FragmentCovidBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCovidBinding.inflate(inflater, container, false)

        viewModel.covidInfo()

        viewModel.covidInfo.observe(viewLifecycleOwner, {
            binding.apply {

                activeTextNumber.text = it.cases.toString()
                recoveredTextNumber.text = it.recovered.toString()
                fatalTextNumber.text = it.deaths.toString()
            }
        })


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}