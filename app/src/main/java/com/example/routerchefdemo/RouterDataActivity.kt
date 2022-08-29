package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityRouterDataBinding

class RouterDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRouterDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.webView.loadUrl("https://www.google.com/")
    }
}