package com.dossantos.g_test.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dossantos.g_test.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun setupClickListener() = with(binding) {
        buttonInitTest.setOnClickListener { }
    }
}