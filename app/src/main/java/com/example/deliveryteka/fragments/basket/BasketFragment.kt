package com.example.deliveryteka.fragments.basket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.R
import com.example.deliveryteka.data.models.BasketMedicineItem
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentBasketBinding
import com.example.deliveryteka.fragments.basket.adapter.BasketAdapter
import com.example.deliveryteka.utility.Constants
import com.example.deliveryteka.utility.Utils
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class BasketFragment : Fragment(),BasketAdapter.OnItemClickListener{

    private val viewModel: DeliverytekaViewModel by viewModels()
    private val adapter: BasketAdapter by lazy { BasketAdapter(this) }


    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        binding.apply {
            rvMedecineCartPriceList.adapter = adapter
            rvMedecineCartPriceList.setHasFixedSize(true)
            rvMedecineCartPriceList.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvMedecineCartPriceList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300

            }

        }

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        userId?.let { viewModel.getBasket(it) }
        viewModel.getBasket.observe(viewLifecycleOwner) {
            if(it.result.isNotEmpty()){
                binding.noProductsInBasketLayout.isVisible = false
                adapter.setData(it.result)
                binding.rvMedecineCartPriceList.isVisible = true
                binding.productsInCartLayout.isVisible = true
                val textPrice =
                    StringBuilder("${it.total} бел.руб.")
                binding.textViewTotalAmount.text = textPrice
            }else{
                binding.rvMedecineCartPriceList.isVisible = false
                binding.productsInCartLayout.isVisible = false
                binding.noProductsInBasketLayout.isVisible = true
            }
        }

        userId?.let { viewModel.getUserInfo(it) }

        binding.makeOrderBtn.setOnClickListener {
            viewModel.getUserInfo.observe(viewLifecycleOwner) {

                val userInfo = it.result

                if(userInfo[0].user_name.isNullOrBlank()||userInfo[0].user_address.isNullOrBlank()||userInfo[0].user_phone.isEmpty()){
                    binding.makeOrderBtn.findNavController().navigate(R.id.action_basketFragment_to_makeOrderFragment)

                }else{
                    binding.makeOrderBtn.findNavController()
                        .navigate(R.id.action_basketFragment_to_makeFillOrderFragment)
                }

            }

        }

        Utils.hideKeyboard(requireActivity())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(medicine: BasketMedicineItem) {
        Toast.makeText(requireContext(),"Лекарственный препарат ${medicine.medicine_name} был удален из корзины",Toast.LENGTH_SHORT).show()
        userId?.let {userId-> viewModel.removeBasket(userId,medicine.medicine_id) }
    }

}