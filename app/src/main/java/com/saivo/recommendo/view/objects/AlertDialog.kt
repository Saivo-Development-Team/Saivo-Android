package com.saivo.recommendo.view.objects

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.saivo.recommendo.R

class AlertDialog {

    lateinit var alert: View
    lateinit var title: TextView
    lateinit var image: ImageView
    lateinit var close: ImageView
    lateinit var dialog: Dialog
    lateinit var button: Button
    lateinit var message: TextView
    lateinit var alertCard: MaterialCardView

    companion object {
        @SuppressLint("InflateParams")
        fun setDialog(activity: AppCompatActivity): AlertDialog {
            return AlertDialog().apply {
                dialog = Dialog(activity)
                alert = activity.layoutInflater.inflate(R.layout.layout_alert_dialog, null)
                title = alert.findViewById(R.id.alert_dialog_title)
                image = alert.findViewById(R.id.alert_dialog_image)
                close = alert.findViewById(R.id.close_alert_dialog)
                button = alert.findViewById(R.id.alert_dialog_button)
                message = alert.findViewById(R.id.alert_dialog_message)
                alertCard = alert.findViewById(R.id.alert_dialog_card)
                dialog.setContentView(alert)
            }
        }
    }
}