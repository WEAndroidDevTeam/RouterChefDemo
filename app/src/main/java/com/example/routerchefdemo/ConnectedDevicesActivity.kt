package com.example.routerchefdemo

import ConnectedDevicesAdapter
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routerchefdemo.databinding.ActivityConnectedDevicesBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject

class ConnectedDevicesActivity : BaseActivity() {
    private lateinit var binding: ActivityConnectedDevicesBinding


    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)


    override fun render(str: String, data: String) {
        Log.e("connectes", data)
        parseConnectedDevices(data)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectedDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/HostInfo",
                id = "Connected Devices",
                "[{\"Active\":true,\"Rssi\":\"-35\",\"HostName\":\"EGCAITSVLT134-Wireless\",\"IPv6Address\":\"fe80::3188:3990:4907:9eb8\",\"ParentControl\":false,\"LeaseTime\":\"77993\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.2.\",\"Ipv6Addrs\":[{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:b900:3312:e34:857b\"},{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:3188:3990:4907:9eb8\"},{\"Ipv6Addr\":\"fe80::3188:3990:4907:9eb8\"}],\"AssociatedDeviceRate\":\"130 Mbps\",\"Layer2Interface\":\"SSID1\",\"ActualName\":\"\",\"WlanActive\":true,\"IPAddress\":\"192.168.1.3\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"ParentControlEnable\":false,\"MACAddress\":\"6C:6A:77:5A:AB:4F\",\"MacFilterID\":\"\",\"X_PhyType\":\"802.11BGN\",\"AddressSource\":\"DHCP\",\"Active46\":true,\"V6Active\":true,\"IconType\":\"\",\"VendorClassID\":\"MSFT 5.0\"},{\"Active\":false,\"HostName\":\"OPPO-A95-Wireless\",\"IPv6Address\":\"\",\"ParentControl\":false,\"LeaseTime\":\"75629\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.1.\",\"Ipv6Addrs\":[],\"Layer2Interface\":\"SSID1\",\"ActualName\":\"OPPO-A95-Wireless\",\"WlanActive\":false,\"IPAddress\":\"192.168.1.2\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"MACAddress\":\"EE:3D:DF:A0:15:F7\",\"ParentControlEnable\":false,\"MacFilterID\":\"\",\"AddressSource\":\"DHCP\",\"Active46\":false,\"V6Active\":false,\"IconType\":\"smartphone\",\"VendorClassID\":\"android-dhcp-12\"}]"
            ), null
        )
        // Create a list of devices (replace with your actual data)
        val deviceList = listOf(
            ConnectedDevice("Device 1", ""),
            ConnectedDevice("Device 2", " R.drawable.device2"),
            ConnectedDevice("Device 3", "R.drawable.device3")
        )

        // Initialize the adapter
        val adapter = ConnectedDevicesAdapter(deviceList)

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