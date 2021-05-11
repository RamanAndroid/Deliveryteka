package com.example.deliveryteka

import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToRegisterFragment")
        @JvmStatic
        fun navigateToRegisterFragment(view: Button, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }
        }

        @BindingAdapter("android:navigateToLoginFragment")
        @JvmStatic
        fun navigateToLoginFragment(view: Button, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }


        @BindingAdapter("android:navigateFromBasketFragmentToMedicineListFragment")
        @JvmStatic
        fun navigateFromBasketFragmentToMedicineListFragment(view: Button, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController()
                        .navigate(R.id.action_basketFragment_to_medicineListFragment)
                }
            }
        }

        @BindingAdapter("android:navigateFromOrderFragmentToMedicineListFragment")
        @JvmStatic
        fun navigateFromOrderFragmentToMedicineListFragment(view: Button, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController()
                        .navigate(R.id.action_orderFragment_to_medicineListFragment2)
                }
            }
        }

        @BindingAdapter("android:navigateFromFavoriteFragmentToMedicineListFragment")
        @JvmStatic
        fun navigateFromFavoriteFragmentToMedicineListFragment(view: Button, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController()
                        .navigate(R.id.action_favoriteListFragment_to_medicineListFragment)
                }
            }
        }

        @BindingAdapter("android:navigateToEditUserAddressFragment")
        @JvmStatic
        fun navigateToEditUserAddressFragment(view: ImageView, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController()
                        .navigate(R.id.action_userProfileFragment_to_editProfileFragment)
                }
            }
        }

        @BindingAdapter("android:navigateToChangePasswordFragment")
        @JvmStatic
        fun navigateToChangePasswordFragment(view: CardView, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController()
                        .navigate(R.id.action_userProfileFragment_to_changePasswordFragment)
                }
            }
        }

        @BindingAdapter("android:navigateToMedicineCardInfoFragment")
        @JvmStatic
        fun navigateToMedicineCardInfoFragment(view: CardView, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController()
                        .navigate(R.id.action_userProfileFragment_to_medicineCardInfoFragment)
                }
            }
        }

    }

}