package com.example.deliveryteka.fragments.favorite

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.deliveryteka.R
import com.example.deliveryteka.data.viewmodel.DeliverytekaViewModel
import com.example.deliveryteka.databinding.FragmentFavoriteBinding
import com.example.deliveryteka.utility.Constants
import com.example.deliveryteka.utility.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<FavoriteFragmentArgs>()
    private val viewModel: DeliverytekaViewModel by viewModels()

    private var userId: Int? = 0
    private var medicineId = ""
    private var price = 0.00f
    private var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        binding.apply {
            fillFields(this)
        }

        return binding.root
    }

    private fun roundOffTo2DecPlaces(number: Float): String {
        return String.format("%.2f", number)
    }

    fun fillFields(binding: FragmentFavoriteBinding) {
        binding.apply {

            val medicine = args.medicine

            Glide.with(this@FavoriteFragment).load(medicine.attributionUrl)
                .error(R.drawable.ic_error).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {


                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }

                }).into(productImage)
            productDescription.text = medicine.medicineDescription

            productTitle.text = medicine.medicineName

            productCountry.text = medicine.medicineCountry
            price = medicine.medicinePrice.toDouble().toFloat()

            val textDosage =
                StringBuilder("${medicine.medicineForm} ${medicine.medicineDosage}x${medicine.medicinePack}")
            productDosage.text = textDosage

            val textCategory =
                StringBuilder("Категория препарата: ${medicine.medicineCategory}")
            productCategory.text = textCategory

            productPrice.text = medicine.medicinePrice

            medicineId = medicine.medicineId


            plusBtn.setOnClickListener {
                if (count < 99) {
                    count++
                    productPrice.text = roundOffTo2DecPlaces(price * count)
                    countText.text = count.toString()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Нельзя добавить больше лекарств",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            minusBtn.setOnClickListener {
                if (count > 1) {
                    count--
                    productPrice.text = roundOffTo2DecPlaces(price * count)
                    countText.text = count.toString()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Нельзя добавить меньше лекарств",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            openPdfBtn.isVisible = medicine.medicinePDF.isNotEmpty()

            openPdfBtn.setOnClickListener {
                Utils.openLink(requireActivity(), medicine.attributionUrlPDF)
            }


            binding.addCartBtn.setOnClickListener {

                findNavController().navigate(R.id.action_favoriteFragment_to_favoriteListFragment)
                userId?.let { id -> viewModel.addToBasket(id, medicineId, count) }
                Toast.makeText(
                    requireContext(),
                    "Товар был добавлен в корзину",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}