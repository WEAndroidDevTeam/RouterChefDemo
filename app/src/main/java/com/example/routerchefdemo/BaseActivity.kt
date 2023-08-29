package com.example.routerchefdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
abstract class BaseActivity : AppCompatActivity() {
    interface WebViewCallback {
        fun onCallback(str: String, data: String)
    }
    protected var webViewCallback: WebViewCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun CallAPI(url: String, id: String): String {
        val jsonData = """
        {
            "key1": "value1",
            "key2": "value2",
            "key3": "value3"
        }
    """.trimIndent()
        return ("javascript: " +
//                "var delay = ( function() {" +
//                "    var timer = 0;" +
//                "    return function(callback, ms) {" +
//                "        clearTimeout (timer);" +
//                "        timer = setTimeout(callback, ms);" +
//                "    };" +
//                "})();" +
//                "function getData (){" +
//                "const http = new XMLHttpRequest();" +
//                "http.open('GET', '$url');" +
//                "http.onreadystatechange = function() {" +
//                "if (this.readyState === 4 && this.status === 200) {" +
//                "const text = http.responseText ;" +
//                "const jsonRegex = /\\/\\*(.*?)\\*\\//s;" +
//                "const jsonMatch = text.match(jsonRegex);" +
//                "const jsonData = JSON.parse(jsonMatch[1]);" +
                "Android.callbackHandle('$id' , JSON.stringify(jsonData));" +
                "}};" +
                "http.send();" +
                "}" +
                "getData();")


    }
    @JavascriptInterface
    public fun callbackHandle(str: String, data: String) {
        // Call the interface method with the received data
        webViewCallback?.onCallback(str, data)
        Log.d("CallbackHandle", "CallbackHandle called with str: $str, data: $data")
        when (str) {
            "logged in" -> startActivity(Intent(this, RouterDataActivity::class.java))
            "device info" -> Toast.makeText(this, data, Toast.LENGTH_LONG).show()
            "navigate" -> startActivity(Intent(this, RouterDataActivity::class.java))
            else -> {
                Toast.makeText(this, data, Toast.LENGTH_LONG).show()
//                finishAffinity()
//                System.exit(0)
//                finishAffinity()
//                System.exit(0)
//                finishAffinity()
//                System.exit(0)
            }
        }
    }

}
