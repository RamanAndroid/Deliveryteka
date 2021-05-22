package com.example.deliveryteka.fragments.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.deliveryteka.data.models.MedicineInfo

class FavoriteDiffUtil (
    private val oldList: List<MedicineInfo>,
    private val newList: List<MedicineInfo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].medicineId == newList[newItemPosition].medicineId
                && oldList[oldItemPosition].medicineName == newList[newItemPosition].medicineName
    }
}