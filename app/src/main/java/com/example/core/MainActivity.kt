package com.example.core

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.core.databinding.ActivityMainBinding
import com.zeesofttechlibraries.core.extensions.CustomLoading.showCustomLoading

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        showCustomLoading("Loading......", isSquarer = true, lottieRaw = R.raw.premium_animation)
        showCustomLoading(this, "Fetching Data......", isSquarer = true, lottieRaw = R.raw.premium_animation,)

    }
}