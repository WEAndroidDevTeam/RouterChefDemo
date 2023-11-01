package com.example.routerchefdemo

import com.example.routerchefdemo.routerModels.HuaweiRouterModel
import com.example.routerchefdemo.routerModels.RouterModel
import com.example.routerchefdemo.routerModels.ZTERouterModel

class BaseRouter private constructor(private val routerModel: RouterModel) {

    var systemInfoPath = routerModel.systemInfoPath
    var dslInfoPath = routerModel.dslInfoPath
    fun login(username: String, password: String): String = routerModel.login(username, password)
    fun getSystemInfo(): String = routerModel.getSystemInfo()
    fun getDslInfo(): String = routerModel.getDslInfo()
    fun changeSSID(
        username: String?,
        password: String?,
        isEnabled: Boolean?,
        maxClients: String?,
        isHidden: Boolean,
        isOpen: Boolean
    ): String = routerModel.changeSSID(username, password, isEnabled, maxClients, isHidden, isOpen)

    fun getConnectedDevices(): String = routerModel.getConnectedDevices()
    fun reboot(): String = routerModel.reboot()


    companion object {
        private var instance: BaseRouter? = null

        fun getInstance(): BaseRouter {
            return instance ?: throw IllegalStateException("Router instance has not been initialized.")
        }

        fun createRouterModel(routerModelName: String): BaseRouter {
            val model = when (routerModelName) {
                "Huawei DG8045" -> HuaweiRouterModel()
                "ZTE H188A" -> ZTERouterModel()
                else -> throw IllegalArgumentException("Invalid router model: $routerModelName")
            }
            instance = BaseRouter(model)
            return instance as BaseRouter
        }
    }
}