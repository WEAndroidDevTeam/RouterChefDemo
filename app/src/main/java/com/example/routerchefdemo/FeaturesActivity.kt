package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityFeaturesBinding
import org.json.JSONObject
import com.example.routerchefdemo.Constants.webview as webView

class FeaturesActivity : BaseActivity<ActivityFeaturesBinding>() {
    override fun getViewBinding() = ActivityFeaturesBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)

        binding.bWifiSettings.setOnClickListener {
            webView.evaluateJavascript(getNavigationScript(), null)
        }
    }

    fun getNavigationScript(): String {
        return ("javascript: " +
                // Enter into WLAN Setup
                "function WlanSetupSection() {" +
                "    document.querySelector('.wifi_user_status.text_center').click();" +
                "}" +

                "WlanSetupSection();" +
                "Android.callbackHandle('navigate', '');")
    }

    override fun render(id: String, data: String) {
        val jsonObject = JSONObject(data)
    }
}