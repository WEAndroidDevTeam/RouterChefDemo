package com.example.routerchefdemo.routerModels

import com.example.routerchefdemo.ConnectedDevice
import com.example.routerchefdemo.DeviceInfo
import com.example.routerchefdemo.DslDetails
import com.example.routerchefdemo.WifiDetails

sealed class RouterModel {
    abstract var routerModel: String
    abstract var loginPath: String
    abstract var systemInfoPath: String
    abstract var dslInfoPath: String
    abstract var wlanSettingsPath: String
    abstract var connectedDevicesPath: String
    abstract var rebootPath: String
    abstract var wlanInfoPath: String
    abstract var wlanAccessPath: String
    abstract var lanInterfacePath: String


    abstract fun login(username: String, password: String): String
    abstract fun getSystemInfo(): String
    abstract fun getDslInfo(): String
    abstract fun getLanInterface(): String
    abstract fun changeSSID(
        ssidName: String?,
        password: String?,
        isEnabled: Boolean?,
        maxClients: String?,
        isHidden: Boolean,
        isOpen: Boolean
    ): String

    abstract fun getConnectedDevices(): String
    abstract fun reboot(): String
    abstract fun parseConnectedDevices(data: String): List<ConnectedDevice>
    abstract fun parseDeviceInfo(data: String): DeviceInfo
    abstract fun getWlanInfo(): String
    abstract fun extractWifiDetails(data: String): WifiDetails
    abstract fun extractDslDetails(data: String): DslDetails
    abstract fun getWlanAccess(): String


    companion object {
        private var instance: RouterModel? = null

        fun getInstance(): RouterModel {
            return instance ?: throw IllegalStateException("Router instance has not been initialized.")
        }

        fun createRouterModel(routerModelName: String): RouterModel {
            instance = when (routerModelName) {
                "Huawei DG8045" -> HuaweiRouterModel()
                "ZTE H188A" -> ZTERouterModel()
                else -> throw IllegalArgumentException("Invalid router model: $routerModelName")
            }
            return instance as RouterModel
        }
    }


    fun callAPI(url: String, id: String, dummy: String? = null): String {

        return (
                "function getData (){" +
                        "console.log('dataaaa' + '$url' );" +
                        "const http = new XMLHttpRequest();" +
                        "http.open('GET', '$url');" +
//                "    const timeoutDuration = 5;" +
//                "http.timeout = timeoutDuration;" +
//                "    http.ontimeout = function() {" +
//                "        console.log('Request timed out after ' + timeoutDuration + ' milliseconds');" +
//                "    };" +
                        "http.onreadystatechange = function() {" +
                        "if (this.readyState === 4) {" +
                        "            if (this.status === 200) {" +
                        "                const text = http.responseText;" +
                        "                Android.callbackHandle('$id', text);" +
                        "            } else {" +
                        "                console.log('fail');" +
                        "                console.log('Request failed with status: ' + this.status);" +
                        "                try {" +
                        "                    const errorResponse = JSON.parse(http.responseText);" +
                        "                    if (errorResponse && errorResponse.message) {" +
                        "                        console.log('Error Message: ' + errorResponse.message);" +
                        "                Android.callbackHandle('$id', errorResponse.message);" +
                        "                    } else {" +
                        "                        console.log('Error Message: Unknown');" +
                        "                    }" +
                        "                } catch (error) {" +
                        "                    console.log('Error parsing API response:', error);" +
                        "                    console.log('Error Message: Unknown');" +
                        "                Android.callbackHandle('$id', 'relogin');" +
                        "                }" +
                        "            }" +
                        "        }" +
                        "    };" +
                        "http.send();" +
                        "}" +
                        "getData();")


    }

}