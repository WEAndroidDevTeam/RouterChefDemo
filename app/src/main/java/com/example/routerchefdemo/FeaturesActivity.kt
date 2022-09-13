package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityFeaturesBinding
import com.example.routerchefdemo.databinding.ActivityMainBinding
import com.example.routerchefdemo.Constants.webview as webView

class FeaturesActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFeaturesBinding.inflate(layoutInflater)
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
                "Android.callbackHandle('navigate');")
    }
}