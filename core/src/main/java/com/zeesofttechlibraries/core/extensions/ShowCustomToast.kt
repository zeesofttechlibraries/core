package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.zeesofttechlibraries.core.R

object ShowCustomToast {
    private var toast: Toast?=null

    fun Context.showCustomToast(
        message: String,
        icon: Int = R.drawable.default_toast_icon,
        @ColorRes textColor: Int = R.color.white,
        @ColorRes bgColor: Int = R.color.mainColor,
        @DrawableRes bgDrawable: Int = R.drawable.custom_toast_bg,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        toast?.cancel()
        val view = LayoutInflater.from(this).inflate(R.layout.custom_toast, null)
        val card = view.findViewById<LinearLayout>(R.id.card)
        val text = view.findViewById<TextView>(R.id.message)
        val icons = view.findViewById<ImageView>(R.id.icon)
        text.text = message
        icons.setImageResource(icon)
        text.setTextColor(ContextCompat.getColor(this, textColor))
        if(bgColor != R.color.mainColor){
            card.setBackgroundColor(ContextCompat.getColor(this, bgColor))
        }else{
            card.background = ContextCompat.getDrawable(this, bgDrawable)
        }
        toast = Toast(this).apply {
            this.view = view
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
            this.duration = duration
        }
        toast?.show()
    }
}