package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.routerchefdemo.databinding.ActivityHomeBinding
import org.json.JSONObject

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)
    lateinit var connectedDevices: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        binding.btMaintain.setOnClickListener {
            val intent = Intent(this, MaintainActivity::class.java)
            startActivity(intent)

        }

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/getuserlevel",
                "user level",
                "{\"isadmin\":true}"

            ), null
        )

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/HostInfo",
                id = "Connected Devices",
                "[{\"Active\":true,\"Rssi\":\"-35\",\"HostName\":\"EGCAITSVLT134-Wireless\",\"IPv6Address\":\"fe80::3188:3990:4907:9eb8\",\"ParentControl\":false,\"LeaseTime\":\"77993\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.2.\",\"Ipv6Addrs\":[{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:b900:3312:e34:857b\"},{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:3188:3990:4907:9eb8\"},{\"Ipv6Addr\":\"fe80::3188:3990:4907:9eb8\"}],\"AssociatedDeviceRate\":\"130 Mbps\",\"Layer2Interface\":\"SSID1\",\"ActualName\":\"\",\"WlanActive\":true,\"IPAddress\":\"192.168.1.3\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"ParentControlEnable\":false,\"MACAddress\":\"6C:6A:77:5A:AB:4F\",\"MacFilterID\":\"\",\"X_PhyType\":\"802.11BGN\",\"AddressSource\":\"DHCP\",\"Active46\":true,\"V6Active\":true,\"IconType\":\"\",\"VendorClassID\":\"MSFT 5.0\"},{\"Active\":false,\"HostName\":\"OPPO-A95-Wireless\",\"IPv6Address\":\"\",\"ParentControl\":false,\"LeaseTime\":\"75629\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.1.\",\"Ipv6Addrs\":[],\"Layer2Interface\":\"SSID1\",\"ActualName\":\"OPPO-A95-Wireless\",\"WlanActive\":false,\"IPAddress\":\"192.168.1.2\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"MACAddress\":\"EE:3D:DF:A0:15:F7\",\"ParentControlEnable\":false,\"MacFilterID\":\"\",\"AddressSource\":\"DHCP\",\"Active46\":false,\"V6Active\":false,\"IconType\":\"smartphone\",\"VendorClassID\":\"android-dhcp-12\"}]"
            ), null
        )
        binding.btConnectedDevices.setOnClickListener {


            startActivity(
                Intent(
                    this,
                    ConnectedDevicesActivity::class.java
                ).putExtra("connectedDevices", connectedDevices)
            )
        }
        binding.btGetDevieInfo.setOnClickListener {
            val intent = Intent(this, SysInformationActivity::class.java)
            startActivity(intent)
        }
        binding.btHomeNetwork.setOnClickListener {
            val intent = Intent(this, HomeNetworkActivity::class.java)
            startActivity(intent)

        }
        binding.btMaintain.setOnClickListener {
            val intent = Intent(this, MaintainActivity::class.java)
            startActivity(intent)
        }
//        Constants.webview.evaluateJavascript(callAPI("https://192.168.1.1/api/system/getuserlevel", "user level"), null)

    }


    override fun render(str: String, data: String) {
        if (str == "user level") {
            Log.e("data", data)
            val jsonObject = JSONObject(data)
            val isAdmin = jsonObject.getBoolean("isadmin")
            if (isAdmin)
                binding.tvWelcome.setText("WELCOME Admin ")
        } else if (str == "Connected Devices") {
            connectedDevices = data
        }
    }
}