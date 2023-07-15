package com.dossantos.g_test.ui.test.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dossantos.g_test.databinding.ActivityTouchScreenTestBinding

class TouchScreenTestActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTouchScreenTestBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        fun startActivity(context: Context) =
            context.startActivity(Intent(context, TouchScreenTestActivity::class.java))
    }
}