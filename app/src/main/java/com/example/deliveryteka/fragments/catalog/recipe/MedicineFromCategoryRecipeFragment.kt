package com.example.deliveryteka.fragments.catalog.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentMedicineFromCategoryRecipeBinding
import com.example.deliveryteka.fragments.home.adapter.MedicineLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class MedicineFromCategoryRecipeFragment : Fragment(), CatalogRecipeAdapter.OnItemClickListener {

    private val viewModel: DeliverytekaViewModel by viewModels()
    private val adapter: CatalogRecipeAdapter by lazy { CatalogRecipeAdapter(this) }
    private var _binding: FragmentMedicineFromCategoryRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedicineFromCategoryRecipeBinding.inflate(inflater, container, false)


        binding.apply {
            recyclerViewMedicineList.setHasFixedSize(true)
            recyclerViewMedicineList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MedicineLoadStateAdapter { adapter.retry() },
                footer = MedicineLoadStateAdapter { adapter.retry() }
            )
            recyclerViewMedicineList.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerViewMedicineList.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300

            }

            btnRetry.setOnClickListener {
                adapter.retry()
            }


        }

        viewModel.category.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(medicine: MedicineInfo) {
        val action =
            MedicineFromCategoryRecipeFragmentDirections.actionMedicineFromCategoryFragmentToDetailMedicineCategoryFragment(
                medicine, medicine.medicineName
            )
        findNavController().navigate(action)
    }

}