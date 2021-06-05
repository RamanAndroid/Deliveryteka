package com.example.deliveryteka.fragments.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deliveryteka.R
import com.example.deliveryteka.data.models.BasketMedicineItem

import com.example.deliveryteka.databinding.ItemBasketBinding

class CheckOrderContentAdapter :
    RecyclerView.Adapter<CheckOrderContentAdapter.CheckOrderContenViewHolder>() {

    var dataList = emptyList<BasketMedicineItem>()


    inner class CheckOrderContenViewHolder(private val binding: ItemBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(medicine: BasketMedicineItem) {
            binding.apply {
                Glide.with(itemView).load(medicine.attributionUrl).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(
                        R.drawable.ic_error
                    )
                    .into(productCartImage)

                productCartTitle.text = medicine.medicine_name

                val textDosage =
                    StringBuilder("${medicine.medicine_form} ${medicine.medicine_dosage}x${medicine.medicine_pack}")
                productCartPack.text = textDosage

                productCartCountry.text = medicine.medicine_country

                val textPrice =
                    StringBuilder("${medicine.medicine_price} бел.руб.")
                productCartPrice.text = textPrice

                val textCount =
                    StringBuilder("${medicine.count}шт.")
                productCount.text = textCount
            }


        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckOrderContenViewHolder {

        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CheckOrderContenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckOrderContenViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(categoriesData: List<BasketMedicineItem>) {
        val toDoDiffUtil = CheckOrderContentDiffUtil(dataList, categoriesData)
        val toDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = categoriesData
        toDiffUtilResult.dispatchUpdatesTo(this)
    }

}