package com.example.deliveryteka.fragments.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deliveryteka.R
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val listenerDelete: OnDeleteClickListener,
    private val listenerOpen: OnOpenClickListener
) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var dataList = emptyList<MedicineInfo>()

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.closeBtn.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = dataList[position]
                    listenerDelete.onDeleteItemClick(item)
                }

            }

            binding.favoriteProduct.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = dataList[position]
                    listenerOpen.onOpenItemClick(item)
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
                    .into(productCartImage)

                productCartTitle.text = medicine.medicineName

                val textDosage =
                    StringBuilder("${medicine.medicineForm} ${medicine.medicineDosage}x${medicine.medicinePack}")
                productCartPack.text = textDosage

                productCartCountry.text = medicine.medicineCountry

                val textPrice =
                    StringBuilder("${medicine.medicinePrice} бел.руб.")
                productCartPrice.text = textPrice
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(categoriesData: List<MedicineInfo>) {
        val toDoDiffUtil = FavoriteDiffUtil(dataList, categoriesData)
        val toDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = categoriesData
        toDiffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnDeleteClickListener {
        fun onDeleteItemClick(medicine: MedicineInfo)
    }

    interface OnOpenClickListener {
        fun onOpenItemClick(medicine: MedicineInfo)
    }
}