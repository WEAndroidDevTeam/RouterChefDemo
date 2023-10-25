package com.example.routerchefdemo

abstract class Router {

    abstract fun parseConnectedDevices(jsonData: String): List<ConnectedDevice>
    abstract fun getLoginScript(username: String, password: String): String
    abstract var urlForConnectedDevices: String
    abstract var idForConnectedDevices:String
    abstract var dummyForConnectedDevices:String
    companion object {
        private lateinit var instance: Router

        fun getInstance(): Router {
            if (!::instance.isInitialized) {
                throw IllegalStateException("Router instance has not been initialized.")
            }
            return instance
        }

        fun createRouterModel(routerModelName: String) {
            instance = when (routerModelName) {
                "Huawei DG8045" -> HuaweiRouterModel()
                "ZTE H188A"-> ZTERouterModel()
                // Add more cases for other router models if needed
                else -> throw IllegalArgumentException("Invalid router model: $routerModelName")
            }
        }
    }
}