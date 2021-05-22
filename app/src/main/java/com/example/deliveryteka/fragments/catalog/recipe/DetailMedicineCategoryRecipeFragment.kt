package com.example.deliveryteka.fragments.catalog.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.isVisible
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
import com.example.deliveryteka.databinding.FragmentDetailMedicineCategoryRecipeBinding
import com.example.deliveryteka.databinding.FragmentFavoriteBinding
import com.example.deliveryteka.fragments.favorite.FavoriteFragmentArgs
import com.example.deliveryteka.utility.Constants
import com.example.deliveryteka.utility.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMedicineCategoryRecipeFragment : Fragment() {

    private var _binding: FragmentDetailMedicineCategoryRecipeBinding? = null
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

        _binding = FragmentDetailMedicineCategoryRecipeBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)

        binding.apply {
            fillFields(this)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_medicine_menu, menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_to_favorite) {
            Toast.makeText(
                requireContext(),
                "Товар был добавлен в избранное!",
                Toast.LENGTH_SHORT
            ).show()
            userId?.let { id -> viewModel.addToFavorite(id, medicineId) }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun roundOffTo2DecPlaces(number: Float): String {
        return String.format("%.2f", number)
    }

    fun fillFields(binding: FragmentDetailMedicineCategoryRecipeBinding) {
        binding.apply {

            val medicine = args.medicine

            Glide.with(this@DetailMedicineCategoryRecipeFragment).load(medicine.attributionUrl)
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
                if (count < medicine.count?.toInt() ?: 99) {
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

            if (medicine.count.isNullOrEmpty()) {
                productCountAcceptable.isVisible = false
            } else {
                val count =
                    StringBuilder("Допустимое количество: ${medicine.count}шт.")
                productCountAcceptable.text = count
            }


            binding.addCartBtn.setOnClickListener {

                findNavController().navigate(R.id.action_detailMedicineCategoryFragment_to_catalogFragment)
                userId?.let { id -> viewModel.addToBasket(id, medicineId, count) }
                Toast.makeText(
                    requireContext(),
                    "Товар был добавлен в корзину",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



}