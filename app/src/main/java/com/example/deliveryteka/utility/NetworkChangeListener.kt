package com.example.deliveryteka.utility

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import com.example.deliveryteka.R

class NetworkChangeListener : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        if (!Utils.isConnectedToInternet(context)) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val layoutDialog = LayoutInflater.from(context).inflate(R.layout.no_network_layout, null)
            builder.setView(layoutDialog)

            val refreshButton=layoutDialog.findViewById<Button>(R.id.refresh_btn)

            val dialog = builder.create()
            dialog.show()
            dialog.setCancelable(false)


            dialog.window?.setGravity(Gravity.CENTER)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            refreshButton.setOnClickListener {
                dialog.dismiss()
                onReceive(context,intent)
            }
        }
    }
}