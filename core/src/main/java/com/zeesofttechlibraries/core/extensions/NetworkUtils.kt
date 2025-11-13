package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {

    /**
     * Checks if the device is connected to the internet.
     *
     * @param context The context to use for accessing system services.
     * @return `true` if the device has an active internet connection, `false` otherwise.
     */
    @JvmStatic
    fun isConnectedToInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val network = cm.activeNetwork ?: return false
        val actNw = cm.getNetworkCapabilities(network) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
