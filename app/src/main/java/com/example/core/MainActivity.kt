package com.example.core

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.databinding.ActivityMainBinding
import com.zeesofttechlibraries.core.extensions.ShowCustomToast.showCustomToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            showCustomToast("Hello Zeesoft Technologies", icon = R.drawable.ic_launcher_foreground, bgColor = R.color.black)
        }
    }
}