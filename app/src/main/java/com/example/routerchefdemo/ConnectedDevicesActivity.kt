package com.example.routerchefdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.routerchefdemo.databinding.ActivityConnectedDevicesBinding
import com.example.routerchefdemo.databinding.ActivityHomeBinding

class ConnectedDevicesActivity : BaseActivity() {
    private lateinit var binding: ActivityConnectedDevicesBinding


    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)


    override fun render(str: String, data: String) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connected_devices)
    }
}