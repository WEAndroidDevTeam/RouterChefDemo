package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityWlansettingsBinding
import org.json.JSONArray
import org.json.JSONObject

class WLANSettingsActivity : BaseActivity<ActivityWlansettingsBinding>() {
    override fun getViewBinding() = ActivityWlansettingsBinding.inflate(layoutInflater)

    private var isConstrainstLayoutVisible = true
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "WLAN Settings")

        binding.tVBasicSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLBasicSettings.visibility = View.VISIBLE
            } else {
                binding.cLBasicSettings.visibility = View.GONE

            }
        }

        binding.tVWlanEncryp.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLWlanEncryption.visibility = View.VISIBLE
            } else {
                binding.cLWlanEncryption.visibility = View.GONE

            }
        }

        binding.tVAdvancedSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clAdvancedSettings.visibility = View.VISIBLE
            } else {

                binding.clAdvancedSettings.visibility = View.GONE
            }
        }
        (applicationContext as MyApp).webView.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/wlanradio",
                "Wlan radio",
                "{\"Enable2G\":true}"
            ), null
        )
        (applicationContext as MyApp).webView.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/wlan_common",
                "Wlan Common",
                "{\"CountryCode\":\"EG\",\"TransmitPower\":100,\"Rate\":0,\"WMMStatus\":true,\"GIControl\":\"long\",\"Channel\":0,\"Mode\":\"b/g/n\",\"Bandwidth\":\"20/40\",\"Mcs\":33}"
            ), null
        )
        (applicationContext as MyApp).webView.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/ntwk/wlan_ssids?showpass=true",
                "Wlan frequency",
                "[{\"WpaIEEEEncModes\":\"AESEncryption\",\"WpaPreSharedKey\":\"********\",\"Enable\":false,\"IsolateControl\":false,\"ID\":\"InternetGatewayDevice.LANDevice.1.WLANConfiguration.2.\",\"SecMode\":\"Basic\",\"WepKeys\":{\"1\":\"********\",\"4\":\"********\",\"3\":\"********\",\"2\":\"********\"},\"WpaMixEncModes\":\"TKIPEncryption\",\"Ssid\":\"SSID-2\",\"WpaEncModes\":\"TKIPEncryption\",\"BasicAuthMode\":\"None\",\"X_AssociateDeviceNum\":32,\"Name\":\"SSID2\",\"HideBroadcast\":false,\"WepEncLevel\":\"104-bit\",\"WepKeyIndex\":1,\"Frequency\":\"2.4GHz\"}]"
            ), null
        )
    }

    override fun render(id: String, data: String) {
        binding.progressCircular.visibility = View.GONE
        when (id) {
            "Wlan radio" -> {
                binding.cBEnableWlan.isChecked = getEnable2G(data)
            }
            "Wlan Common" -> {
                var networkSettings = getNetworkSettings(data)
                binding.tvTransmitPower.text = networkSettings.transmitPower.toString()
                binding.cBWMM.isChecked = networkSettings.wmmStatus
                binding.tv11nValue.text = networkSettings.bandwidth
                binding.tV11nMCSValue.text = networkSettings.mcs.toString()
            }
            "Wlan frequency" -> {
                binding.tVFrequencyValue.text = getFrequency(data)
            }
        }
    }

    fun getEnable2G(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getBoolean("Enable2G")
    }

    fun getNetworkSettings(jsonString: String): NetworkSettings {
        val jsonObject = JSONObject(jsonString)
        val transmitPower = jsonObject.getInt("TransmitPower")
        val wmmStatus = jsonObject.getBoolean("WMMStatus")
        val bandwidth = jsonObject.getString("Bandwidth")
        val mcs = jsonObject.getInt("Mcs")
        return NetworkSettings(transmitPower, wmmStatus, bandwidth, mcs)
    }

    fun getFrequency(jsonArrayString: String): String {
        val jsonArray = JSONArray(jsonArrayString)
        val jsonObject = jsonArray.getJSONObject(0)
        return jsonObject.getString("Frequency")
    }

}
