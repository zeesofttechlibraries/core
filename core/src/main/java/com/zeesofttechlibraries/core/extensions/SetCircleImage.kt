package com.zeesofttechlibraries.core.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.widget.ImageView

object SetCircleImage {
    fun ImageView.setCircularImage(bitmap: Bitmap) {
        val size = Math.min(bitmap.width, bitmap.height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        paint.isAntiAlias = true

        val rect = RectF(0f, 0f, size.toFloat(), size.toFloat())
        canvas.drawOval(rect, paint)

        paint.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, ((size - bitmap.width) / 2).toFloat(), ((size - bitmap.height) / 2).toFloat(), paint)

        this.setImageBitmap(output)
    }

}