package com.example.deliveryteka.utility

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.inputmethod.InputMethodManager


class Utils {

    companion object {
        private const val VK_APP_PACKAGE_ID = "com.vkontakte.android"

        fun hideKeyboard(activity: Activity) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val currentFocusedView = activity.currentFocus
            currentFocusedView?.let {
                inputMethodManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }


        fun isConnectedToInternet(context: Context?): Boolean {
            var result = false
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }
            return result
        }

        fun openLink(activity: Activity, url: String?) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                val resInfo = activity.packageManager.queryIntentActivities(intent, 0)
                if (resInfo.isEmpty()) return
                for (info in resInfo) {
                    if (info.activityInfo == null) continue
                    if (VK_APP_PACKAGE_ID == info.activityInfo.packageName) {
                        intent.setPackage(info.activityInfo.packageName)
                        break
                    }
                }
                activity.startActivity(intent)
            }catch (ex: ActivityNotFoundException){

            }

        }

    }


}