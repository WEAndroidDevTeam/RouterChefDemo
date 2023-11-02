package com.example.routerchefdemo.routerModels

import com.example.routerchefdemo.ConnectedDevice
import com.example.routerchefdemo.DeviceInfo
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


    abstract fun login(username: String, password: String): String
    abstract fun getSystemInfo(): String
    abstract fun getDslInfo(): String
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
}