package com.example.routerchefdemo.routerModels

import com.example.routerchefdemo.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.json.JSONObject

class HuaweiRouterModel : RouterModel() {

    override var routerModel: String = "Huawei DG8045"
    override var loginPath: String = "https://192.168.1.1/"
    override var systemInfoPath: String = "https://192.168.1.1/api/system/deviceinfo"
    override var dslInfoPath: String = "https://192.168.1.1/api/ntwk/dslinfo"
    override var wlanSettingsPath: String = "https://192.168.1.1/html/advance.html#wlan"
    override var connectedDevicesPath: String = "https://192.168.1.1/api/system/HostInfo"
    override var rebootPath: String = "https://192.168.1.1/html/advance.html#device_mngt"
    override var wlanInfoPath: String = "https://192.168.1.1/api/system/diagnose_wlan_basic?type=1"
    override var wlanAccessPath: String = "https://192.168.1.1/api/ntwk/wlanfilter?frequency=2.4GHz"
    override var lanInterfacePath: String = "https://192.168.1.1/api/ntwk/lan_host"
    override var changePasswordPath: String = "https://192.168.1.1/html/wizard/wifi.html"


    override fun login(username: String, password: String): String {
        return "function login(user,pass, callback) {" +
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
                "console.log('userName: ' + $username);" +
                "console.log('Pass: ' + $password);" +
                "  if (result !== undefined) {" +
                "Android.callbackHandle('logged in' , result);" +
                "  }" +
                "});"
    }

    override fun getSystemInfo(): String {
        return callAPI(this.systemInfoPath , Constants.SYSTEM_INFO)
    }

    override fun getDslInfo(): String {
        return callAPI(this.dslInfoPath , Constants.DSL_INFO)
    }

    override fun getLanInterface(): String {
      return callAPI(this.lanInterfacePath , Constants.LAN_INTERFACE_STATUS)
    }

    override fun extractDslDetails(jsonData: String): DslDetails {
        val data = JSONObject(jsonData)
        val status = data.optString("Status")
        val modulation = data.optString("Modulation")
        val upCurrRate = data.optInt("UpCurrRate")
        val downCurrRate = data.optInt("DownCurrRate")
        val downstreamMaxBitRate = data.optInt("DownstreamMaxBitRate")
        val upstreamMaxBitRate = data.optInt("UpstreamMaxBitRate")
        val impulsoNoiseProUs = data.optInt("ImpulsoNoiseProUs")
        val impulsoNoiseProDs = data.optInt("ImpulsoNoiseProDs")
        val downAttenuation = data.optInt("DownAttenuation")
        val upAttenuation = data.optInt("UpAttenuation")
        val upPower = data.optInt("UpPower")
        val downPower = data.optInt("DownPower")
        val dslUpTime = data.optInt("ShowtimeStart") //TODO

        return DslDetails(
            status,
            modulation,
            upCurrRate,
            downCurrRate,
            downstreamMaxBitRate,
            upstreamMaxBitRate,
            impulsoNoiseProUs,
            impulsoNoiseProDs,
            downAttenuation,
            upAttenuation,
            upPower,
            downPower,
           formatMillisecondsToDuration(dslUpTime)
        )
    }

    override fun getWlanAccess(): String {
        return callAPI(this.wlanAccessPath , Constants.WLAN_ACCESS)
    }

    override fun changePassword(ssidName: String?, password: String?): String {
        return  "document.querySelector('button#wifi_wizard_save.atp_button.fontweight_thick').addEventListener('click', function(e){" +
                "document.querySelector('#home_wifi_access24id_ctrl').addEventListener('change', function(){" +
                "document.querySelector('#home_wifi_access24id_ctrl').value = '${ssidName}';" +
                "console.log('ssidName: ' + $ssidName);" +
                "});" +
                "document.querySelector('#hidesharekeyMenu_Password_ctrl').addEventListener('change', function(){" +
                "document.querySelector('#hidesharekeyMenu_Password_ctrl').value = '${password}';" +
                "console.log('password: ' + $password);" +
                "});" +
                "document.querySelector('#home_wifi_access24id_ctrl').dispatchEvent(new Event('change', {'bubbles': true}));" +
                "document.querySelector('#hidesharekeyMenu_Password_ctrl').dispatchEvent(new Event('change', {'bubbles': true}));" +
                "});" +
                "document.querySelector('button#wifi_wizard_save.atp_button.fontweight_thick').click();" +
                "Android.callbackHandle('change password' , 'wait until success');"
    }

    override fun changeSSID(
        ssidName: String?,
        password: String?,
        isEnabled: Boolean?,
        maxClients: String?,
        isHidden: Boolean,
        isOpen: Boolean
    ): String {
        val sb = StringBuilder()
        sb.append("let ssid = \"")
        sb.append(ssidName)
        sb.append("\";\nlet hidden = ")
        sb.append(isHidden)
        sb.append(";\nlet password = \"")
        sb.append(password)
        sb.append("\";\nlet open = ")
        sb.append(isOpen)
        sb.append(";\nlet maxClients = \"")
        sb.append(maxClients)
        sb.append(
            "\";\n\nlet applied = false;\n\nlet exit = setTimeout(() => {\n    clearInterval(temp);\n    clearTimeout(exit);\n    Android.callbackHandle(JSON.stringify({ result: \"timeout\" }));\n}, 10000);\n\nlet temp = setInterval(() => {\n    try {\n        if (document.getElementById('login_window')) {\n            clearInterval(temp);\n            clearTimeout(exit);\n            Android.callbackHandle(JSON.stringify({ result: \"relogin\" }));\n        } else {\n            Android.callbackHandle(JSON.stringify({ result: \"applying_settings\" }));\n\n            if (!document.getElementById(\"wlan_wifi_mi_ssid2_4GHz1_ctrl\")) {\n                (new Array(...document.getElementsByClassName(\"pull-left third_menu_font paddingtop_5 marginleft_3\"))).filter(item => item.innerText.includes(\"WLAN\"))[0].click()\n            } else if (document.getElementById(\"wlan_enc_view_data_submitstatusview_failview\")) {\n                clearInterval(temp);\n                clearTimeout(exit);\n                Android.callbackHandle(JSON.stringify({ result: \"complex_wlan_password_needed\" }));\n            }\n            else if (applied) {\n                clearInterval(temp);\n                clearTimeout(exit);\n                Android.callbackHandle(JSON.stringify({ result: \"executed\" }));\n            }\n            else {\n                Atp.WlanSendSettingsController.content[0].WifiSsid = ssid;\n                Atp.WlanSendSettingsController.content[0].X_AssociateDeviceNum = maxClients\n                Atp.WlanSendSettingsController.content[0].WifiHideBroadcast = hidden;\n\n                if (!open) {\n                    Atp.WlanSendSettingsController.content[0].BeaconType = \"WPAand11i\"\n                    password && (Atp.WlanSendSettingsController.content[0].WpaPreSharedKey = password);\n                } else {\n                    Atp.WlanSendSettingsController.content[0].BeaconType = \"None\";\n                }\n                if (document.getElementById(\"AllSsidSettings_submitbutton\")) {\n                    document.getElementById(\"AllSsidSettings_submitbutton\").click();\n                    applied=true;    \n                }\n            }\n        }\n    } catch (err){ }\n}, 1000);"
        )
        return sb.toString()
    }

    override fun getConnectedDevices(): String {
        return callAPI(this.connectedDevicesPath , Constants.CONNECTED_DEVICES)
    }

    override fun extractMacAndIp(data: String): Pair<String, String> {
        val jsonObject = JSONObject(data)
        val macAddress = jsonObject.getString("MACAddress")
        val ipAddress = jsonObject.getString("FristIP")
        return Pair(macAddress, ipAddress)
    }

//    override fun getLanInterface(): String {
//        val urls = listOf(
//            "url1",
//            "url2",
//            "url3",
//            "url4",
//            "url5",
//            "url6"
//
//        )
//
//        val scriptBuilder = StringBuilder()
//
//        scriptBuilder.append("function getData(url, id) {\n" +
//                "            console.log('dataaaa' + url);\n" +
//                "            const http = new XMLHttpRequest();\n" +
//                "            http.open('GET', url);\n" +
//                "            http.onreadystatechange = function() {\n" +
//                "                if (this.readyState === 4) {\n" +
//                "                    if (this.status === 200) {\n" +
//                "                        const text = http.responseText;\n" +
//                "                        Android.callbackHandle(id, text);\n" +
//                "                    } else {\n" +
//                "                        console.log('fail');\n" +
//                "                        console.log('Request failed with status: ' + this.status);\n" +
//                "                        try {\n" +
//                "                            const errorResponse = JSON.parse(http.responseText);\n" +
//                "                            if (errorResponse && errorResponse.message) {\n" +
//                "                                console.log('Error Message: ' + errorResponse.message);\n" +
//                "                                Android.callbackHandle(id, errorResponse.message);\n" +
//                "                            } else {\n" +
//                "                                console.log('Error Message: Unknown');\n" +
//                "                            }\n" +
//                "                        } catch (error) {\n" +
//                "                            console.log('Error parsing API response:', error);\n" +
//                "                            console.log('Error Message: Unknown');\n" +
//                "                            Android.callbackHandle(id, 'relogin');\n" +
//                "                        }\n" +
//                "                    }\n" +
//                "                }\n" +
//                "            };\n" +
//                "            http.send();\n" +
//                "        }")
//
//        urls.forEachIndexed { index, url ->
//            val id = "lanInterface_$index"
//            scriptBuilder.append("getData('$url', '$id');\n")
//        }
//
//        return scriptBuilder.toString()
//    }


    override fun reboot(): String {
        return "let exit = setTimeout(() => {\n    clearInterval(temp);\n    clearTimeout(exit);\n    Android.callbackHandle(JSON.stringify({ result: \"timeout\" }));\n}, 10000);\n\nlet temp = setInterval(() => {\n    try {\n        if (document.getElementById('login_window')) {\n            clearInterval(temp);\n            clearTimeout(exit);\n            Android.callbackHandle(JSON.stringify({ result: \"relogin\" }));\n        } else {\n            Android.callbackHandle(JSON.stringify({ result: \"applying_settings\" }));\n            Atp.RebootController.click_proc();\n            clearInterval(temp);\n            clearTimeout(exit);\n            Android.callbackHandle(JSON.stringify({ result: \"executed\" }));\n        }\n    } catch (err){ }\n}, 500);"
    }

    override fun logout(): String {
        return "function LogOut(){" +
                "    try {" +
                "       document.querySelector('#signout_ctrl').click();" +
                "       Android.callbackHandle('logout' , 'relogin');" +
                "    }catch(err){" +
                "       Android.callbackHandle('logout' , 'relogin');" +
                "        return err.message;" +
                "    }" +
                "}" +
                "LogOut();"
    }

    override fun parseConnectedDevices(jsonData: String): List<ConnectedDevice> {
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

    override fun parseDeviceInfo(jsonString: String): DeviceInfo {
        val jsonObject = JSONObject(jsonString)
        val deviceName = jsonObject.getString("DeviceName")
        val serialNumber = jsonObject.getString("SerialNumber")
        val softwareVersion = jsonObject.getString("SoftwareVersion")
        val hardwareVersion = jsonObject.getString("HardwareVersion")
        val uptime = jsonObject.getLong("UpTime")

        return DeviceInfo(deviceName, serialNumber, softwareVersion, hardwareVersion, uptime)
    }

    override fun getWlanInfo(): String {
        return callAPI(this.wlanInfoPath , Constants.WLAN_INFO)
    }

    override fun extractWifiDetails(jsonData: String): WifiDetails {
        val data = JSONObject(jsonData)
        val ssid = data.optString("SSID")
        val enable = data.optInt("Enable")
        val bssid = data.optString("BSSID")
        val autoChannelEnable = data.optBoolean("AutoChannelEnable")
        val transmitPower = data.optInt("TransmitPower")
        val region = data.optString("RegulatoryDomain")

        return WifiDetails(ssid, enable, bssid, autoChannelEnable, transmitPower, region)
    }
    fun formatMillisecondsToDuration(milliseconds: Int?): String {
        var milliseconds = milliseconds?.times(1000)
        val seconds = (milliseconds?.div(1000))?.toInt()
        val days = seconds?.div(86400)
        val hours = (seconds?.rem(86400))?.div(3600)
        val minutes = (seconds?.rem(3600))?.div(60)
        val remainingSeconds = seconds?.rem(60)
        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, remainingSeconds)
    }
}
