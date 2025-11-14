package com.zeesofttechlibraries.core.extensions

import android.app.Dialog
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * A lifecycle-aware wrapper that automatically dismisses the dialog
 * when the Activity/Fragment lifecycle ends.
 */
internal class LoadingDialogLifecycleObserver(private val dialog: Dialog) : DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        if (dialog.isShowing) dialog.dismiss()
    }
}
