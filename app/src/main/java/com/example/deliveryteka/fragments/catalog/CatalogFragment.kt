package com.example.deliveryteka.fragments.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.data.models.CategoriesItem
import com.example.deliveryteka.data.viewmodel.MedicineListViewModel
import com.example.deliveryteka.databinding.FragmentCatalogBinding
import com.example.deliveryteka.fragments.catalog.adapter.CatalogAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private val viewModel: MedicineListViewModel by viewModels()
    private val adapter: CatalogAdapter by lazy { CatalogAdapter() }
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCatalogBinding.inflate(inflater, container, false)


        binding.apply {
            catalogRecyclerView.adapter = adapter
            catalogRecyclerView.setHasFixedSize(true)
            catalogRecyclerView.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            catalogRecyclerView.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300

            }

        }

        viewModel.getCategories()
        viewModel.getCategories.observe(viewLifecycleOwner) {
            adapter.setData(it.result)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}