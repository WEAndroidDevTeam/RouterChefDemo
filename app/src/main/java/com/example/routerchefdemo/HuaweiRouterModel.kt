package com.example.routerchefdemo

import com.google.gson.Gson
import com.google.gson.JsonParser

class HuaweiRouterModel : Router() {
    override fun parseConnectedDevices(jsonData: String): List<ConnectedDevice> {
        val connectedDevicesList = mutableListOf<ConnectedDevice>()


        val jsonArray = Gson().fromJson(jsonData, Array<Any>::class.java)
        jsonArray.forEach { jsonObject ->
            val jsonString = Gson().toJson(jsonObject)
            val hostName =
                JsonParser.parseString(jsonString).getAsJsonObject().get("HostName").toString()
            val iconType =
                JsonParser.parseString(jsonString).getAsJsonObject().get("IconType").toString()

            val connectedDevice = ConnectedDevice(hostName)
            connectedDevicesList.add(connectedDevice)
        }

        return connectedDevicesList
    }

    override var urlForConnectedDevices: String = "https://192.168.1.1/api/system/HostInfo"
    override var idForConnectedDevices: String = Constants.CONNECTED_DEVICES
    override var dummyForConnectedDevices: String =
        "[{\"Active\":true,\"Rssi\":\"-35\",\"HostName\":\"EGCAITSVLT134-Wireless\",\"IPv6Address\":\"fe80::3188:3990:4907:9eb8\",\"ParentControl\":false,\"LeaseTime\":\"77993\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.2.\",\"Ipv6Addrs\":[{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:b900:3312:e34:857b\"},{\"Ipv6Addr\":\"fdb4:f58e:2b86:a400:3188:3990:4907:9eb8\"},{\"Ipv6Addr\":\"fe80::3188:3990:4907:9eb8\"}],\"AssociatedDeviceRate\":\"130 Mbps\",\"Layer2Interface\":\"SSID1\",\"ActualName\":\"\",\"WlanActive\":true,\"IPAddress\":\"192.168.1.3\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"ParentControlEnable\":false,\"MACAddress\":\"6C:6A:77:5A:AB:4F\",\"MacFilterID\":\"\",\"X_PhyType\":\"802.11BGN\",\"AddressSource\":\"DHCP\",\"Active46\":true,\"V6Active\":true,\"IconType\":\"\",\"VendorClassID\":\"MSFT 5.0\"},{\"Active\":false,\"HostName\":\"OPPO-A95-Wireless\",\"IPv6Address\":\"\",\"ParentControl\":false,\"LeaseTime\":\"75629\",\"ID\":\"InternetGatewayDevice.LANDevice.1.Hosts.Host.1.\",\"Ipv6Addrs\":[],\"Layer2Interface\":\"SSID1\",\"ActualName\":\"OPPO-A95-Wireless\",\"WlanActive\":false,\"IPAddress\":\"192.168.1.2\",\"domain\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1\",\"MACAddress\":\"EE:3D:DF:A0:15:F7\",\"ParentControlEnable\":false,\"MacFilterID\":\"\",\"AddressSource\":\"DHCP\",\"Active46\":false,\"V6Active\":false,\"IconType\":\"smartphone\",\"VendorClassID\":\"android-dhcp-12\"}]"
    override fun getLoginScript(username: String, password: String): String {
        return ("javascript: " +
                "function login(user, pass, callback) {" +
                "  try {" +
                "    document.querySelector('#index_username').value = user;" +
                "    document.querySelector('#password').value = pass;" +
                "    document.querySelector('#loginbtn').click();" +
                "" +
                "    setTimeout(function () {" +
                "      var error = document.querySelector('#errorCategory').textContent;" +
                "      if (typeof callback === 'function') {" +
                "        callback(error);" +
                "      }" +
                "    }, 5000);" +
                "  } catch (err) {" +
                "    if (typeof callback === 'function') {" +
                "      callback(err.message);" +
                "    }" +
                "  }" +
                "}" +
                "" +
                "login('$username', '$password', function(result) {" +
                "  if (result !== undefined) {" +
                "Android.callbackHandle('logged in' , result);" +
                "  }" +
                "});"
                )
    }

}
