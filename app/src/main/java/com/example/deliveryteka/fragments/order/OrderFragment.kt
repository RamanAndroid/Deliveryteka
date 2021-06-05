package com.example.deliveryteka.fragments.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.data.models.OrdersItem
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentOrderBinding
import com.example.deliveryteka.fragments.home.MedicineListFragmentDirections
import com.example.deliveryteka.fragments.order.adapter.OrderAdapter
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class OrderFragment : Fragment(), OrderAdapter.OnOpenClickListener {

    private val viewModel: DeliverytekaViewModel by viewModels()
    private val adapter: OrderAdapter by lazy { OrderAdapter(this) }

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        binding.apply {
            rvOrdersList.adapter = adapter
            rvOrdersList.setHasFixedSize(true)
            rvOrdersList.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvOrdersList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }

        }

        userId?.let { viewModel.getOrders(it) }
        viewModel.getOrders.observe(viewLifecycleOwner) {
            if (it.result.isNotEmpty()) {
                binding.noProductsInOrderLayout.isVisible = false
                adapter.setData(it.result)
                binding.rvOrdersList.isVisible = true
            } else {
                binding.rvOrdersList.isVisible = false
                binding.noProductsInOrderLayout.isVisible = true
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenItemClick(order: OrdersItem) {
        val action =
            OrderFragmentDirections.actionOrderFragmentToCheckOrderContentFragment(
                order.order_id
            )
        findNavController().navigate(action)
    }


}