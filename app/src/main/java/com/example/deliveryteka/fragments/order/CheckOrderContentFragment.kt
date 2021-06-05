package com.example.deliveryteka.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentCheckOrderContentBinding
import com.example.deliveryteka.fragments.order.adapter.CheckOrderContentAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class CheckOrderContentFragment : Fragment() {

    private var _binding: FragmentCheckOrderContentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeliverytekaViewModel by viewModels()
    private val adapter: CheckOrderContentAdapter by lazy { CheckOrderContentAdapter() }
    private val args by navArgs<CheckOrderContentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCheckOrderContentBinding.inflate(inflater, container, false)

        binding.apply {
            rvMedecineCartPriceList.adapter = adapter
            rvMedecineCartPriceList.setHasFixedSize(true)
            rvMedecineCartPriceList.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvMedecineCartPriceList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300

            }

            viewModel.getOrderContent(args.id.toInt())

            viewModel.getOrderContent.observe(viewLifecycleOwner) {
                adapter.setData(it.result)
                val textPrice =
                    StringBuilder("${it.total} бел.руб.")
                binding.textViewTotalAmount.text = textPrice
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

