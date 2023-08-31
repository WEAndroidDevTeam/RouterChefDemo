package com.example.routerchefdemo

import android.content.Intent
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
            val intent = Intent(this, LANInterfaceActivity::class.java)
            startActivity(intent)
        }
        binding.btnWlanSettings.setOnClickListener {
            val intent = Intent(this, WLANSettingsActivity::class.java)
            startActivity(intent)

        }
        binding.btnWlanAccess.setOnClickListener {

        }
    }

    override fun render(str: String, data: String) {
    }
}