package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityWlanAccessBinding
import org.json.JSONObject

class WlanAccessActivity : BaseActivity<ActivityWlanAccessBinding>() {
    override fun getViewBinding() = ActivityWlanAccessBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/wlanfilter?frequency=2.4GHz",
                "Wlan access",
                "{\"WpsEnable\":false,\"WifiWpsApPinCode\":\"\",\"WpsCanEnable\":true,\"WpsMode\":\"pbc\",\"ID\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WPS.\",\"FrequencyBand\":\"2.4GHz\",\"wpswifiEnable\":true,\"WifiWpsPinCode\":\"\"}"
            ), null
        )
    }


    override fun render(str: String, data: String) {
        binding.progressCircular.visibility = View.GONE
        if (str == "Wlan access")
            binding.cBWPS.isChecked = extractWpsEnable(data)

    }

    fun extractWpsEnable(jsonData: String): Boolean {
        val data = JSONObject(jsonData)
        val wpsEnable = data.optBoolean("WpsEnable")

        return wpsEnable
    }
}