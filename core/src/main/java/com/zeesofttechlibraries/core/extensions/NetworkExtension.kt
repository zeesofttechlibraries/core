package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat

fun Context.isConnectedToInternet(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val actNw = cm.getNetworkCapabilities(network) ?: return false
    return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
