package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityRouterDataBinding


class RouterDataActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRouterDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.bApply.setOnClickListener {
            Constants.webview.evaluateJavascript(getData("https://192.168.1.1/api/system/deviceinfo", "device info"), null)
            Constants.webview.evaluateJavascript(getData("https://192.168.1.1/api/ntwk/lan_host", "3"), null)
        }

        binding.bApplyy.setOnClickListener {
            Constants.webview.evaluateJavascript(getData("https://192.168.1.1/api/ntwk/lan_host", "3"), null)
        }
    }

    fun getData(url: String, id: String): String {
        return ("javascript: " +
                "var delay = ( function() {" +
                "    var timer = 0;" +
                "    return function(callback, ms) {" +
                "        clearTimeout (timer);" +
                "        timer = setTimeout(callback, ms);" +
                "    };" +
                "})();" +
                "function getData (){" +
                "const http = new XMLHttpRequest();" +
                "http.open('GET', '$url');" +
                "http.onreadystatechange = function() {" +
                "if (this.readyState === 4 && this.status === 200) {" +
                "const text = http.responseText ;" +
                "const jsonRegex = /\\/\\*(.*?)\\*\\//s;" +
                "const jsonMatch = text.match(jsonRegex);" +
                "const jsonData = JSON.parse(jsonMatch[1]);" +
                "Android.callbackHandle('$id' , JSON.stringify(jsonData));" +
                "}};" +
                "http.send();" +
                "}" +
                "getData();")
    }
}