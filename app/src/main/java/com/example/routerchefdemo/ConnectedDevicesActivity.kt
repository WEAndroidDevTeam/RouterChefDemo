package com.example.routerchefdemo

import ConnectedDevicesAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routerchefdemo.Constants.CONNECTED_DEVICES
import com.example.routerchefdemo.databinding.ActivityConnectedDevicesBinding
import com.google.gson.Gson
import com.google.gson.JsonParser

class ConnectedDevicesActivity : BaseActivity<ActivityConnectedDevicesBinding>() {
    override fun getViewBinding() = ActivityConnectedDevicesBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    private var deviceList: List<ConnectedDevice> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "Connected Devices")
        Constants.webview.evaluateJavascript(
            callAPI(
                router.urlForConnectedDevices,
                CONNECTED_DEVICES,
               "",
                responseType = Constants.RESPONSE_TYPE_XML
            ), null
        )
    }

    override fun render(str: String, data: String) {
        if (str != CONNECTED_DEVICES) {
            return
        }
        Log.d("dataComes",  data)

        binding.progressCircular.visibility = View.GONE
        deviceList = router.parseConnectedDevices(data) // Assign parsed list to deviceList
        Log.d("ConnectedDevices", "List size: ${deviceList.size}")
        binding.rvConnectedDevices.layoutManager = LinearLayoutManager(this)
        binding.rvConnectedDevices.adapter = ConnectedDevicesAdapter()
        (binding.rvConnectedDevices.adapter as ConnectedDevicesAdapter).submitList(deviceList.toMutableList())
    }

//    fun parseConnectedDevices(jsonData: String): List<ConnectedDevice> {
//
//    }
}