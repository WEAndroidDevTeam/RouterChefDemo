package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityHomeNetworkBinding

class HomeNetworkActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeNetworkBinding
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeNetworkBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.btnLanDevices.setOnClickListener {

        }
        binding.btnLanInterface.setOnClickListener {

        }
        binding.btnWlanSettings.setOnClickListener {

        }
        binding.btnWlanAccess.setOnClickListener {

        }
    }

    override fun render(str: String, data: String) {
    }
}