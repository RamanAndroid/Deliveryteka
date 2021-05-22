package com.example.deliveryteka.fragments.favorite

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
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentFavoriteListBinding
import com.example.deliveryteka.fragments.favorite.adapter.FavoriteAdapter
import com.example.deliveryteka.fragments.home.MedicineListFragmentDirections
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class FavoriteListFragment : Fragment(),FavoriteAdapter.OnOpenClickListener,FavoriteAdapter.OnDeleteClickListener {

    private val viewModel: DeliverytekaViewModel by viewModels()
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter(this,this) }

    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!

    var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        binding.apply {
            rvMedecineCartPriceList.adapter = adapter
            rvMedecineCartPriceList.setHasFixedSize(true)
            rvMedecineCartPriceList.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvMedecineCartPriceList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }

        }

        userId?.let { viewModel.getFavorites(it) }
        viewModel.getFavorites.observe(viewLifecycleOwner) {
            if(it.result.isNotEmpty()) {
                binding.noProductsInOrderLayout.isVisible = false
                adapter.setData(it.result)
                binding.rvMedecineCartPriceList.isVisible = true
            }else{
                binding.rvMedecineCartPriceList.isVisible = false
                binding.noProductsInOrderLayout.isVisible = true
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteItemClick(medicine: MedicineInfo) {
        Toast.makeText(requireContext(),"Лекарственный препарат ${medicine.medicineName} был удален из избранных",
            Toast.LENGTH_SHORT).show()
        userId?.let {userId-> viewModel.removeFavorite(userId,medicine.medicineId) }
    }

    override fun onOpenItemClick(medicine: MedicineInfo) {
        val action =FavoriteListFragmentDirections.actionFavoriteListFragmentToFavoriteFragment(medicine,medicine.medicineName)
        findNavController().navigate(action)
    }

}