package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import jp.wasabeef.blurry.Blurry

object BaseBlurry {
    fun addBlur(
        activityRoot: ViewGroup,
        activity: Activity,
        blurOverlay: ImageView?,
    ) {
        Blurry.with(activity)
            .radius(15)
            .sampling(2)
            .async()
            .capture(activityRoot)
            .getAsync {
                blurOverlay?.setImageBitmap(it)
                blurOverlay?.animate()?.alpha(1f)?.setDuration(250)?.start()
            }
    }

    fun removeBlur(activityRoot: ViewGroup, blurOverlay: ImageView?, setBlurOverlayNull:()-> Unit){
        blurOverlay?.animate()?.alpha(0f)?.setDuration(350)?.withEndAction { activityRoot.removeView(blurOverlay) }
        setBlurOverlayNull.invoke()
    }
}