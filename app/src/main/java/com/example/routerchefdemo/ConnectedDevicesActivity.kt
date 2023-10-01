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

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/HostInfo",
                CONNECTED_DEVICES,
                "[{\"Active\":true,\"Rssi\":\"-35\",\"HostName\":\"EGCAITSVLT134-Wireless\",\"IPv6Address\":\"fe80::3188:3990:4907:9eb8\",\"ParentControl\":false,\"LeaseTime\":\"77993\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.2.\",\"Ipv6Addrs\":[{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:b900:3312:e34:857b\"},{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:3188:3990:4907:9eb8\"},{\"Ipv6Addr\":\"fe80::3188:3990:4907:9eb8\"}],\"AssociatedDeviceRate\":\"130 Mbps\",\"Layer2Interface\":\"SSID1\",\"ActualName\":\"\",\"WlanActive\":true,\"IPAddress\":\"192.168.1.3\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"ParentControlEnable\":false,\"MACAddress\":\"6C:6A:77:5A:AB:4F\",\"MacFilterID\":\"\",\"X_PhyType\":\"802.11BGN\",\"AddressSource\":\"DHCP\",\"Active46\":true,\"V6Active\":true,\"IconType\":\"\",\"VendorClassID\":\"MSFT 5.0\"},{\"Active\":false,\"HostName\":\"OPPO-A95-Wireless\",\"IPv6Address\":\"\",\"ParentControl\":false,\"LeaseTime\":\"75629\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.1.\",\"Ipv6Addrs\":[],\"Layer2Interface\":\"SSID1\",\"ActualName\":\"OPPO-A95-Wireless\",\"WlanActive\":false,\"IPAddress\":\"192.168.1.2\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"MACAddress\":\"EE:3D:DF:A0:15:F7\",\"ParentControlEnable\":false,\"MacFilterID\":\"\",\"AddressSource\":\"DHCP\",\"Active46\":false,\"V6Active\":false,\"IconType\":\"smartphone\",\"VendorClassID\":\"android-dhcp-12\"}]"
            ), null
        )
    }

    override fun render(str: String, data: String) {
        if (str != CONNECTED_DEVICES) {
            return
        }
        binding.progressCircular.visibility = View.GONE
        deviceList = parseConnectedDevices(data) // Assign parsed list to deviceList
        Log.d("ConnectedDevices", "List size: ${deviceList.size}")
        binding.rvConnectedDevices.layoutManager = LinearLayoutManager(this)
        binding.rvConnectedDevices.adapter = ConnectedDevicesAdapter()
        (binding.rvConnectedDevices.adapter as ConnectedDevicesAdapter).submitList(deviceList.toMutableList())
    }

    fun parseConnectedDevices(jsonData: String): List<ConnectedDevice> {
        val connectedDevicesList = mutableListOf<ConnectedDevice>()

        val jsonArray = Gson().fromJson(jsonData, Array<Any>::class.java)
        jsonArray.forEach { jsonObject ->
            val jsonString = Gson().toJson(jsonObject)
            val hostName =
                JsonParser.parseString(jsonString).getAsJsonObject().get("HostName").toString()
            val iconType =
                JsonParser.parseString(jsonString).getAsJsonObject().get("IconType").toString()

            val connectedDevice = ConnectedDevice(hostName, iconType)
            connectedDevicesList.add(connectedDevice)
        }

        return connectedDevicesList
    }
}