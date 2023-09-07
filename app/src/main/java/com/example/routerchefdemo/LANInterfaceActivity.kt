package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityLaninterfaceBinding
import org.json.JSONObject

class LANInterfaceActivity : BaseActivity<ActivityLaninterfaceBinding>() {
    override fun getViewBinding() = ActivityLaninterfaceBinding.inflate(layoutInflater)

    private var isConstrainstLayoutVisible = true
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/deviceinfo",
                "Lan Interface status",
                "{\"SecLanEnable\":false,\"SecondIP\":\"\",\"SecondMac\":\"\",\"MACAddress\":\"B4:F5:8E:2B:86:A4\",\"DevName\":\"mediarouter\",\"ID\":\"InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.IPInterface.1.\",\"ShowLanDomainEnable\":false,\"FirstEnable\":true,\"FristIP\":\"192.168.1.1\",\"FirstMac\":\"255.255.255.0\",\"DomainName\":\"home\"}"
            ), null
        )
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/lan_server",
                "dhcp server",
                "{\"PassthroughMACAddress\":\"\",\"MinIP\":\"192.168.1.2\",\"UseAllocatedWAN\":\"Normal\",\"dnsmode\":\"true\",\"DNSServerone\":\"192.168.1.1\",\"PassthroughLease\":60,\"ServerEnable\":true,\"DHCPLeaseTime\":86400,\"ID\":\"InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.\",\"AssociatedConnection\":\"\",\"DNSServertwo\":\"192.168.1.1\",\"MaxIP\":\"192.168.1.65\"}"
            ), null
        )
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/lan_radvd",
                "Lan Interface RA",
                "{\"UseAllocatedWAN\":\"UseAllocatedSubnet\",\"ULAPreferredlifetime\":3600,\"NDProxyFlag\":\"\",\"DefaultLifetime\":\"\",\"Prefix\":\"\",\"RetransTimer\":\"\",\"ID\":\"InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.X_SLAAC.\",\"MinRtrAdvInterval\":\"\",\"Validlifetime\":7200,\"Interface\":\"\",\"PreferredRouterFlag\":\"\",\"PrefixLength\":64,\"ULAAllocatedMode\":\"ULAAuto\",\"CurHopLimit\":\"\",\"ULAPrefix\":\"\",\"ULAValidlifetime\":7200,\"MobileAgentFlag\":\"\",\"RouterAdvertisementEnable\":true,\"OtherConfigFlag\":0,\"MaxRtrAdvInterval\":\"\",\"ReachableTime\":\"\",\"ManagedFlag\":0,\"ULAPrefixLength\":64,\"Preferredlifetime\":3600,\"LinkMTU\":\"\",\"MOFlagAutoMode\":1}"
            ), null
        )
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/lan_dhcp6s",
                "Lan Interface IPV6",
                "{\"PrefixLength\":64,\"UseAllocatedWAN\":\"UseAllocatedSubnet\",\"DHCPServerEnable\":true,\"Prefix\":\"\",\"Preferredlifetime\":3600,\"Dhcp6sDNSServerone\":\"\",\"ID\":\"InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.X_DHCPv6.\",\"Dhcp6sDNSServertwo\":\"\",\"Validlifetime\":7200,\"DomainName\":\"\"}"
            ), null
        )
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/lan_upnp",
                "Lan Interface upnp",
                "{\"enable\":false}"
            ), null
        )
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/lan_tr064",
                "Lan Interface tr064",
                "{\"enable\":false}"
            ), null
        )
        binding.tVLanStatus.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLLanInterfaceStatus.visibility = View.VISIBLE
            } else {
                binding.cLLanInterfaceStatus.visibility = View.GONE

            }
        }

        binding.tVLanSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.clLanSettings.visibility = View.VISIBLE
            } else {
                binding.clLanSettings.visibility = View.GONE

            }
        }

        binding.tVDhcbServer.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clDhcpServer.visibility = View.VISIBLE
            } else {

                binding.clDhcpServer.visibility = View.GONE
            }
        }
        binding.tVRaSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clRaSettings.visibility = View.VISIBLE
            } else {

                binding.clRaSettings.visibility = View.GONE
            }
        }

        binding.tVIpv6.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clIpv6.visibility = View.VISIBLE
            } else {

                binding.clIpv6.visibility = View.GONE
            }
        }

        binding.tVUpnp.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clUpnp.visibility = View.VISIBLE
            } else {

                binding.clUpnp.visibility = View.GONE
            }

        }
        binding.tVTr064.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clTr064.visibility = View.VISIBLE
            } else {

                binding.clTr064.visibility = View.GONE
            }
        }
    }

    override fun render(str: String, data: String) {
        when (str) {
            "Lan Interface status" -> {
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
            "dhcp server" -> {
                val dhcpServerJson = parseDHCPServerJson(data)
                with(binding) {
                    cBDhcpEnable.isChecked = dhcpServerJson["ServerEnable"] as Boolean
                    tVMinIP.text = dhcpServerJson["MinIP"] as String
                    tVMaxIP.text = dhcpServerJson["MaxIP"] as String
                    rBDnsMode.isChecked = dhcpServerJson["dnsmode"] as Boolean
                }
            }
            "Lan Interface RA" -> {
                binding.cBRaEnable.isChecked = getRouterAdvertisementEnable(data)
            }
            "Lan Interface IPV6" -> {
                binding.cBIpv6Enable.isChecked = getDHCPServerEnable(data)
            }
            "Lan Interface upnp" -> {
                binding.cBUpnpEnable.isChecked = getEnableUPNP(data)
            }
            "Lan Interface tr064" -> {
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

