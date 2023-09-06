package com.example.routerchefdemo

import ConnectedDevicesAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routerchefdemo.databinding.ActivityConnectedDevicesBinding
import com.google.gson.Gson
import com.google.gson.JsonParser

class ConnectedDevicesActivity : BaseActivity<ActivityConnectedDevicesBinding>() {
    override fun getViewBinding() = ActivityConnectedDevicesBinding.inflate(layoutInflater)
    private lateinit var adapter: ConnectedDevicesAdapter
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    private var deviceList: List<ConnectedDevice> = emptyList()
    override fun render(str: String, data: String) {

//
        adapter.notifyDataSetChanged() // Notify adapter of data change


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        val deviceList = listOf(
//            ConnectedDevice("Device 1", ""),
//            ConnectedDevice("Device 2", " R.drawable.device2"),
//            ConnectedDevice("Device 3", "R.drawable.device3")
//        )

        val connectedDevices = intent.getStringExtra("connectedDevices")

        deviceList = parseConnectedDevices(connectedDevices!!) // Assign parsed list to deviceList
        Log.d("ConnectedDevices", "List size: ${deviceList.size}")
        adapter = ConnectedDevicesAdapter(deviceList)
        // Set up the RecyclerView
        binding.rvConnectedDevices.adapter = adapter
        binding.rvConnectedDevices.layoutManager = LinearLayoutManager(this)


    }

    fun parseConnectedDevices(jsonData: String): List<ConnectedDevice> {
        val connectedDevicesList = mutableListOf<ConnectedDevice>()

        val jsonArray = Gson().fromJson(jsonData, Array<Any>::class.java)
        jsonArray.forEach { jsonObject ->
            val jsonString = Gson().toJson(jsonObject)
            val hostName = JsonParser.parseString(jsonString).getAsJsonObject().get("HostName").toString()
            val iconType = JsonParser.parseString(jsonString).getAsJsonObject().get("IconType").toString()

            val connectedDevice = ConnectedDevice(hostName, iconType)
            connectedDevicesList.add(connectedDevice)
        }

        return connectedDevicesList
    }
}