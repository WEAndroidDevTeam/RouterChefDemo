package com.example.routerchefdemo

import ConnectedDevicesAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routerchefdemo.Constants.CONNECTED_DEVICES
import com.example.routerchefdemo.databinding.ActivityConnectedDevicesBinding

class ConnectedDevicesActivity : BaseActivity<ActivityConnectedDevicesBinding>() {
    override fun getViewBinding() = ActivityConnectedDevicesBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    private var deviceList: List<ConnectedDevice> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "Connected Devices")
        (applicationContext as MyApp).webView.evaluateJavascript(router.getConnectedDevices(), null)
    }

    override fun onPageLoaded(id: String) {
        if (id != Constants.CONNECTED_DEVICES)
            return
    }

    override fun render(id: String, data: String) {
        if (id != CONNECTED_DEVICES) {
            return
        }
        binding.progressCircular.visibility = View.GONE
//        deviceList = router.parseConnectedDevices(data) // Assign parsed list to deviceList
        Log.d("ConnectedDevices", "List size: ${deviceList.size}")
        binding.rvConnectedDevices.layoutManager = LinearLayoutManager(this)
        binding.rvConnectedDevices.adapter = ConnectedDevicesAdapter()
        (binding.rvConnectedDevices.adapter as ConnectedDevicesAdapter).submitList(deviceList.toMutableList())
    }

//    fun parseConnectedDevices(jsonData: String): List<ConnectedDevice> {
//
//    }
}