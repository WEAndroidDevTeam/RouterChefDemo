package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.WLAN_INFO
import com.example.routerchefdemo.databinding.ActivityWlaninfoBinding
import org.json.JSONObject

class WLANInfoActivity : BaseActivity<ActivityWlaninfoBinding>() {
    override fun getViewBinding() = ActivityWlaninfoBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "WLAN Info")

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/diagnose_wlan_basic?type=1",
                WLAN_INFO,
                "{\"WEPEncryptionLevel\":\"104-bit\",\"TransmitPower\":100,\"Enable\":1,\"BeaconType\":\"WPAand11i\",\"Bandwidth\":\"20/40\",\"AutoChannelEnable\":true,\"X_WlanStandard\":\"b/g/n\",\"SSID\":\"WE2B86A4\",\"RegulatoryDomain\":\"EG\",\"ObjAcc\":65535,\"X_Wlan11NBWControl\":\"20/40\",\"X_MixedEncryptionModes\":\"TKIPandAESEncryption\",\"ApNums\":0,\"BasicEncryptionModes\":\"None\",\"X_OperatingFrequencyBand\":\"2.4GHz\",\"BasicAuthenticationMode\":\"None\",\"IEEE11iEncryptionModes\":\"AESEncryption\",\"WEPKeyIndex\":1,\"Channel\":8,\"BSSID\":\"b4:f5:8e:2b:86:bc\",\"MaxBitRate\":\"Auto\",\"WPAEncryptionModes\":\"TKIPEncryption\",\"Signal\":2}"
            ), null
        )

    }

    override fun render(id: String, data: String) {
        if (id != WLAN_INFO)
            return
        binding.progressCircular.visibility = View.GONE
        var wifiDetails = extractWifiDetails(data)
        binding.tVSsidNam.text = wifiDetails.ssid
        if (wifiDetails.enable == 1)
            binding.tVOn.text = "ON"
        binding.tvMacNum.text = wifiDetails.bssid
        if (wifiDetails.autoChannelEnable == true)
            binding.tvChannelNum.text = "AUTO"
        binding.tVTransmitNum.text = wifiDetails.transmitPower.toString()
        if (wifiDetails.region == "EG")
            binding.tVRegionName.text = "Egypt"
    }

    fun extractWifiDetails(jsonData: String): WifiDetails {
        val data = JSONObject(jsonData)
        val ssid = data.optString("SSID")
        val enable = data.optInt("Enable")
        val bssid = data.optString("BSSID")
        val autoChannelEnable = data.optBoolean("AutoChannelEnable")
        val transmitPower = data.optInt("TransmitPower")
        val region = data.optString("RegulatoryDomain")

        return WifiDetails(ssid, enable, bssid, autoChannelEnable, transmitPower, region)
    }

}