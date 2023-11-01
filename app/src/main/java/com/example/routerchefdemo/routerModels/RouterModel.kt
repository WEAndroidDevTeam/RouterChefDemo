package com.example.routerchefdemo.routerModels

sealed class RouterModel {
    abstract var routerModel: String
    abstract var loginPath: String
    abstract var systemInfoPath: String
    abstract var dslInfoPath: String
    abstract var wlanSettingsPath: String
    abstract var connectedDevicesPath: String
    abstract var rebootPath: String

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
}