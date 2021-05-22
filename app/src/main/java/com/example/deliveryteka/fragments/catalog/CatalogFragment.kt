package com.example.deliveryteka.fragments.catalog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.data.models.CategoriesItem
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentCatalogBinding
import com.example.deliveryteka.fragments.catalog.adapter.CatalogAdapter
import com.example.deliveryteka.utility.Constants
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class CatalogFragment : Fragment(), CatalogAdapter.OnItemClickListener {

    private val viewModel: DeliverytekaViewModel by viewModels()
    private val adapter: CatalogAdapter by lazy { CatalogAdapter(this) }
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

    override fun onItemClick(categoriesItem: CategoriesItem) {
        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        val userId = sharedPref?.getInt(Constants.USER_ID, 0)

        userId?.let {
            Constants.TITLE = categoriesItem.category
            Constants.ID = it.toString()
           val action =
                CatalogFragmentDirections.actionCatalogFragmentToMedicineFromCategoryFragment(
                    categoriesItem.category
                )
            findNavController().navigate(action)

        }
    }

}