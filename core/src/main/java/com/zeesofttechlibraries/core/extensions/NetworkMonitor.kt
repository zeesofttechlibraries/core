package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.zeesofttechlibraries.core.extensions.CustomAlertDialog.dismissAlertDialog
import com.zeesofttechlibraries.core.extensions.CustomAlertDialog.showCustomAlertDialog
import com.zeesofttechlibraries.core.extensions.CustomLoading.isDialogShowing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class NetworkMonitor(val context: Context, val lifecycleOwner: LifecycleOwner) {

    fun startMonitoring() {
        checkInternet()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    checkInternet()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    checkInternet()
                }
            })
        }

    }

    private fun checkInternet() {
        lifecycleOwner.lifecycleScope.launch {
            if (isInternetAvailable()) {
                dismissWarningDialog()
            } else {
                showWarningDialog()

            }
        }
    }

    private fun showWarningDialog() {
        if (context is Activity) {
            context.runOnUiThread {
                context.showCustomAlertDialog(
                    lifeCycleOwner = lifecycleOwner,
                    "No Internet",
                    "Please connect to internet to continue",
                    positiveButtonText = "Retry",
                    positiveButtonTextColor = com.zeesofttechlibraries.core.R.color.warning,
                    positiveButtonBg = com.zeesofttechlibraries.core.R.drawable.warning_bg,
                    topBgColor = com.zeesofttechlibraries.core.R.color.warning,
                    lottieAnimation = com.zeesofttechlibraries.core.R.raw.warning_animation,
                    positiveButtonAction = {
                        lifecycleOwner.lifecycleScope.launch {
                            if (!isInternetAvailable()) {
                                startMonitoring()
                            } else {
                                dismissWarningDialog()
                            }
                        }
                    },
                    isBlurred = true,
                )
            }
        } else {
            // fallback if context is not an Activity
            Handler(Looper.getMainLooper()).post {
                context.showCustomAlertDialog(
                    lifeCycleOwner = lifecycleOwner,
                    "No Internet",
                    "Please connect to internet to continue",
                    positiveButtonText = "Retry",
                    positiveButtonTextColor = com.zeesofttechlibraries.core.R.color.warning,
                    positiveButtonBg = com.zeesofttechlibraries.core.R.drawable.warning_bg,
                    topBgColor = com.zeesofttechlibraries.core.R.color.warning,
                    lottieAnimation = com.zeesofttechlibraries.core.R.raw.warning_animation,
                    positiveButtonAction = {
                        lifecycleOwner.lifecycleScope.launch {
                            if (!isInternetAvailable()) {
                                startMonitoring()
                            } else {
                                dismissWarningDialog()
                            }
                        }
                    },
                    isBlurred = true,
                )
            }
        }
    }


    fun dismissWarningDialog() {
        if (context is Activity) {
            context.runOnUiThread { context.dismissAlertDialog() }
        } else {
            Handler(Looper.getMainLooper()).post { context.dismissAlertDialog() }
        }
    }

    suspend fun isInternetAvailable(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val url = URL("https://clients3.google.com/generate_204")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 500
            connection.readTimeout = 500
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            connection.disconnect()
            responseCode == 204
        } catch (e: Exception) {
            false
        }
    }


}