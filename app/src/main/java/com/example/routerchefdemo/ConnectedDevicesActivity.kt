package com.example.routerchefdemo

import ConnectedDevicesAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routerchefdemo.databinding.ActivityConnectedDevicesBinding
import com.example.routerchefdemo.databinding.ActivityHomeBinding

class ConnectedDevicesActivity : BaseActivity() {
    private lateinit var binding: ActivityConnectedDevicesBinding


    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)


    override fun render(str: String, data: String) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectedDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Create a list of devices (replace with your actual data)
        val deviceList = listOf(
            ConnectedDevice("Device 1", ""),
            ConnectedDevice("Device 2"," R.drawable.device2"),
            ConnectedDevice("Device 3", "R.drawable.device3")
        )

        // Initialize the adapter
       val adapter = ConnectedDevicesAdapter(deviceList)

        // Set up the RecyclerView
        binding.rvConnectedDevices.adapter = adapter
        binding.rvConnectedDevices.layoutManager = LinearLayoutManager(this)



    }
}