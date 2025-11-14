package com.example.core

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.core.databinding.ActivityMainBinding
import com.zeesofttechlibraries.core.R
import com.zeesofttechlibraries.core.extensions.CustomLoading
import com.zeesofttechlibraries.core.extensions.CustomLoading.showCustomLoading
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        showCustomLoading("Loading......", isSquarer = true, lottieRaw = R.raw.premium_animation)
        showCustomLoading(this, "Fetching Data......", isSquarer = true, isBlurred = true)


        lifecycleScope.launch {
            delay(5000)
            CustomLoading.dismissDialog(this@MainActivity)
        }

//        binding.blurView.setupWith(binding.blurTarget)
//            .setFrameClearDrawable(window?.decorView?.background)
//            .setBlurRadius(70f)

    }
}