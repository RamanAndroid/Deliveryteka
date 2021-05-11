package com.example.deliveryteka.fragments.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.deliveryteka.R
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.data.viewmodel.MedicineListViewModel
import com.example.deliveryteka.databinding.FragmentMedicineListBinding
import com.example.deliveryteka.fragments.home.adapter.MedicineAdapter
import com.example.deliveryteka.fragments.home.adapter.MedicineLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


@AndroidEntryPoint
class MedicineListFragment : Fragment(),MedicineAdapter.OnItemClickListener {

    private val viewModel: MedicineListViewModel by viewModels()

    private var _binding: FragmentMedicineListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineListBinding.inflate(inflater, container, false)

        val adapter = MedicineAdapter(this)

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

            btnRetry.setOnClickListener{
                adapter.retry()
            }


        }

        viewModel.medicines.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        adapter.addLoadStateListener { loadState->
            binding.apply{
                progressBarMedicineList.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewMedicineList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if(loadState.source.refresh is LoadState.NotLoading&& loadState.append.endOfPaginationReached&&adapter.itemCount<1){
                    recyclerViewMedicineList.isVisible = false
                    textViewEmpty.isVisible = true
                }else{
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.list_fragment_menu, menu)

        val searchItem = menu.findItem(R.id.menu_action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerViewMedicineList.scrollToPosition(0)
                    viewModel.searchMedicines(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(medicine: MedicineInfo) {
        val action = MedicineListFragmentDirections.actionMedicineListFragmentToDetailMedicineFragment(medicine)
        findNavController().navigate(action)
    }
}