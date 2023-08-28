package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityRouterDataBinding


class RouterDataActivity : BaseActivity() {

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRouterDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.bApply.setOnClickListener {
            Constants.webview.evaluateJavascript(CallAPI("https://192.168.1.1/api/system/deviceinfo", "device info"), null)
            Constants.webview.evaluateJavascript(CallAPI("https://192.168.1.1/api/ntwk/lan_host", "3"), null)
        }

        binding.bApplyy.setOnClickListener {
            Constants.webview.evaluateJavascript(CallAPI("https://192.168.1.1/api/ntwk/lan_host", "3"), null)
        }
    }


}