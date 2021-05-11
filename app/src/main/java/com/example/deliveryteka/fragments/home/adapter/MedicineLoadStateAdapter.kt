package com.example.deliveryteka.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryteka.databinding.MedicineLoadStateFooterBinding

class MedicineLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MedicineLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = MedicineLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoadStateViewHolder(val binding: MedicineLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                textViewError.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Loading
            }
        }
    }


}