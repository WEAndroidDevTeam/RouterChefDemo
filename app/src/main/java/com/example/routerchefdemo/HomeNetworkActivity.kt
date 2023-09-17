package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityHomeNetworkBinding

class HomeNetworkActivity : BaseActivity<ActivityHomeNetworkBinding>() {
    override fun getViewBinding() = ActivityHomeNetworkBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        binding.btnLanDevices.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ConnectedDevicesActivity::class.java
                )
            )
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
            val intent = Intent(this, WlanAccessActivity::class.java)
            startActivity(intent)
        }
    }

    override fun render(str: String, data: String) {
    }
}