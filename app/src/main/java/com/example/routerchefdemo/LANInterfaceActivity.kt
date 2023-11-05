package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.LAN_INTERFACE_DHCP_SERVER
import com.example.routerchefdemo.Constants.LAN_INTERFACE_IPV6
import com.example.routerchefdemo.Constants.LAN_INTERFACE_RA
import com.example.routerchefdemo.Constants.LAN_INTERFACE_STATUS
import com.example.routerchefdemo.Constants.LAN_INTERFACE_TR064
import com.example.routerchefdemo.Constants.LAN_INTERFACE_UPNP
import com.example.routerchefdemo.databinding.ActivityLaninterfaceBinding
import com.example.routerchefdemo.routerModels.RouterModel
import org.json.JSONObject

class LANInterfaceActivity : BaseActivity<ActivityLaninterfaceBinding>() {
    override fun getViewBinding() = ActivityLaninterfaceBinding.inflate(layoutInflater)

    private var isConstrainstLayoutVisible = true
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "LAN Interface")

        if (RouterModel.getInstance().lanInterfacePath == "")
            onPageLoaded("Huawei Router")
        else
            (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().lanInterfacePath)

    }


    override fun onPageLoaded(id: String) {
        (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                RouterModel.getInstance().getLanInterface(), null
        )
    }

    override fun render(id: String, data: String) {
        binding.progressCircular.visibility = View.GONE
        when (id) {
            LAN_INTERFACE_STATUS -> {
                val deviceDetails: DeviceDetails = parseDeviceDetails(data)
                with(binding) {
                    tVMacNum.text = deviceDetails.macAddress
                    tVIpNum.text = deviceDetails.firstIP
                    tVIpaddressNum.text = deviceDetails.firstIP
                    tVSubnetMaskNum.text = deviceDetails.firstMac
                    cBSecLAN.isChecked = deviceDetails.secLanEnable
                    tVNAme.text = deviceDetails.devName
                }
            }
            LAN_INTERFACE_DHCP_SERVER -> {
                val dhcpServerJson = parseDHCPServerJson(data)
                with(binding) {
                    cBDhcpEnable.isChecked = dhcpServerJson["ServerEnable"] as Boolean
                    tVMinIP.text = dhcpServerJson["MinIP"] as String
                    tVMaxIP.text = dhcpServerJson["MaxIP"] as String
                    rBDnsMode.isChecked = dhcpServerJson["dnsmode"] as Boolean
                }
            }
            LAN_INTERFACE_RA -> {
                binding.cBRaEnable.isChecked = getRouterAdvertisementEnable(data)
            }
            LAN_INTERFACE_IPV6 -> {
                binding.cBIpv6Enable.isChecked = getDHCPServerEnable(data)
            }
            LAN_INTERFACE_UPNP -> {
                binding.cBUpnpEnable.isChecked = getEnableUPNP(data)
            }
            LAN_INTERFACE_TR064 -> {
                binding.cBTr064Enable.isChecked = getEnableTr064(data)
            }
        }
    }

    fun parseDeviceDetails(jsonString: String): DeviceDetails {
        val jsonObject = JSONObject(jsonString)
        val macAddress = jsonObject.getString("MACAddress")
        val firstIP = jsonObject.getString("FristIP")
        val firstMac = jsonObject.getString("FirstMac")
        val secLanEnable = jsonObject.getBoolean("SecLanEnable")
        val devName = jsonObject.getString("DevName")

        return DeviceDetails(macAddress, firstIP, firstMac, secLanEnable, devName)
    }

    fun parseDHCPServerJson(jsonString: String): Map<String, Any> {
        val jsonObject = JSONObject(jsonString)

        val serverEnable = jsonObject.getBoolean("ServerEnable")
        val minIP = jsonObject.getString("MinIP")
        val maxIP = jsonObject.getString("MaxIP")
        val dnsmode = jsonObject.getBoolean("dnsmode")

        return mapOf(
            "ServerEnable" to serverEnable,
            "MinIP" to minIP,
            "MaxIP" to maxIP,
            "dnsmode" to dnsmode
        )
    }

    fun getRouterAdvertisementEnable(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getBoolean("RouterAdvertisementEnable")
    }

    fun getDHCPServerEnable(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getBoolean("DHCPServerEnable")
    }

    fun getEnableUPNP(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getBoolean("enable")
    }

    fun getEnableTr064(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getBoolean("enable")
    }
}

