package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityRouterDataBinding


class RouterDataActivity : AppCompatActivity() {

    object AndroidJSInterface {
        @JavascriptInterface
        fun onClicked() {
            Log.d("HelpButton", "Help button clicked")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRouterDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.webView.loadUrl("https://192.168.1.1/")
        binding.webView.settings.javaScriptEnabled = true

      /*  binding.webView.post {
            binding.webView.loadUrl(
                "document.getElementById('index_username').value = admin;\n" +
                        "                        document.getElementById('password').value = K9114659;\n" +
                        "                        document.getElementById(\\\"loginbtn\\\").click();\n" +
                        "[8:51 PM, 8/29/2022] Mona: if (document.getElementById('wizard_wifi_title')) \n" +
                        "            Android.callbackHandle(JSON.stringify({result:\\\"login_success\\\"}));"
            )
        }*/


    }
}