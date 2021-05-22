package com.example.deliveryteka.fragments.catalog.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deliveryteka.R
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.databinding.ItemMedicineFromCategoryRecipeBinding

class CatalogRecipeAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<MedicineInfo, CatalogRecipeAdapter.CatalogRecipeViewHolder>(
        MEDICINE_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogRecipeViewHolder {
        val binding =
            ItemMedicineFromCategoryRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return CatalogRecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogRecipeViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }


    inner class CatalogRecipeViewHolder(private val binding: ItemMedicineFromCategoryRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            binding.buyMedicine.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }

            }
        }

        fun bind(medicine: MedicineInfo) {
            binding.apply {

                Glide.with(itemView).load(medicine.attributionUrl).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(
                        R.drawable.ic_error
                    )
                    .into(ivCard)

                productTitle.text = medicine.medicineName
                val textDosage =
                    StringBuilder("${medicine.medicineForm} ${medicine.medicineDosage}x${medicine.medicinePack}")
                productDosage.text = textDosage
                productCountry.text = medicine.medicineCountry

                val textPrice =
                    StringBuilder("${medicine.medicinePrice} бел.руб.")
                buyMedicine.text = textPrice

                if (medicine.count.isNullOrEmpty()) {
                    productCountAcceptable.isVisible = false
                } else {
                    val count =
                        StringBuilder("Допустимо: ${medicine.count}шт.")
                    productCountAcceptable.text = count
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(medicine: MedicineInfo)
    }

    companion object {
        private val MEDICINE_COMPARATOR = object : DiffUtil.ItemCallback<MedicineInfo>() {
            override fun areItemsTheSame(oldInfo: MedicineInfo, newInfo: MedicineInfo) =
                oldInfo.medicineId == newInfo.medicineId


            override fun areContentsTheSame(oldInfo: MedicineInfo, newInfo: MedicineInfo) =
                oldInfo == newInfo

        }


    }


}