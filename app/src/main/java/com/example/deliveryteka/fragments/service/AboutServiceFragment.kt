package com.example.deliveryteka.fragments.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deliveryteka.R
import com.example.deliveryteka.databinding.FragmentAboutServiceBinding
import com.example.deliveryteka.utility.Utils


class AboutServiceFragment : Fragment() {

    private var _binding: FragmentAboutServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutServiceBinding.inflate(inflater, container, false)

        binding.btnOpenOfficialWebsite.setOnClickListener {
            Utils.openLink(requireActivity(),"https://komaroff-site.000webhostapp.com/index.html")
        }

        binding.btnOpenVkGleb.setOnClickListener {
            Utils.openLink(requireActivity(),"https://vk.com/komaroffdes")
        }
        binding.btnOpenVkRoman.setOnClickListener {
            Utils.openLink(requireActivity(),"https://vk.com/romlyschyk")
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}