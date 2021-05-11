package com.example.deliveryteka.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deliveryteka.R
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.databinding.ItemMedicineListBinding

class MedicineAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<MedicineInfo, MedicineAdapter.MedicineViewHolder>(
        MEDICINE_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding =
            ItemMedicineListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }


    inner class MedicineViewHolder(private val binding: ItemMedicineListBinding) :
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
                    StringBuilder("Капсулы ${medicine.medicineDosage}x${medicine.medicinePack}")
                productDosage.text = textDosage
                productCountry.text = medicine.medicineCountry
                buyMedicine.text = medicine.medicinePrice
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(medicine: MedicineInfo)
    }

    companion object {
        private val MEDICINE_COMPARATOR = object : DiffUtil.ItemCallback<MedicineInfo>() {
            override fun areItemsTheSame(oldItem: MedicineInfo, newItem: MedicineInfo) =
                oldItem.medicineId == newItem.medicineId


            override fun areContentsTheSame(oldItem: MedicineInfo, newItem: MedicineInfo) =
                oldItem == newItem

        }


    }


}