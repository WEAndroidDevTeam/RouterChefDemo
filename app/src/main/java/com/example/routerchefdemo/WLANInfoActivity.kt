package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.WLAN_INFO
import com.example.routerchefdemo.databinding.ActivityWlaninfoBinding
import com.example.routerchefdemo.routerModels.RouterModel
import org.json.JSONObject

class WLANInfoActivity : BaseActivity<ActivityWlaninfoBinding>() {
    override fun getViewBinding() = ActivityWlaninfoBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "WLAN Info")
        (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().wlanInfoPath)


    }


    override fun onPageLoaded(id: String) {
        (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                RouterModel.getInstance().getWlanInfo(), null
        )
    }

    override fun render(id: String, data: String) {
        if (id != WLAN_INFO)
            return
        binding.progressCircular.visibility = View.GONE
        var wifiDetails : WifiDetails = router.extractWifiDetails(data)
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



}