package com.example.deliveryteka.fragments.home.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
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
import com.example.deliveryteka.databinding.FragmentDetailMedicineBinding
import com.example.deliveryteka.utility.Constants
import com.example.deliveryteka.utility.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailMedicineFragment : Fragment() {

    private var _binding: FragmentDetailMedicineBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailMedicineFragmentArgs>()
    private val viewModel: DeliverytekaViewModel by viewModels()

    private var userId: Int? = 0
    private var medicineId = ""
    private var price = 0.00f
    private var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMedicineBinding.inflate(inflater, container, false)

        val sharedPref = activity?.getSharedPreferences(Constants.USER_ID, Context.MODE_PRIVATE)
        userId = sharedPref?.getInt(Constants.USER_ID, 0)



        binding.apply {
            fillFields(this)
        }




        setHasOptionsMenu(true)
        return binding.root

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

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun roundOffTo2DecPlaces(number: Float): String {
        return String.format("%.2f", number)
    }

    fun fillFields(binding: FragmentDetailMedicineBinding) {
        binding.apply {
            val medicine = args.medicine

            Glide.with(this@DetailMedicineFragment).load(medicine.attributionUrl)
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
                Utils.openLink(requireActivity(),medicine.attributionUrlPDF)
            }


            binding.addCartBtn.setOnClickListener {

                findNavController().navigate(R.id.action_detailMedicineFragment_to_medicineListFragment)
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