package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
abstract class BaseActivity : AppCompatActivity() {

    abstract fun setCurrentActivity()
    abstract fun render()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentActivity()
    }

    fun callAPI(url: String, id: String, dummy: String? = null): String {

        return ("javascript: " +
//                "var delay = ( function() {" +
//                "    var timer = 0;" +
//                "    return function(callback, ms) {" +
//                "        clearTimeout (timer);" +
//                "        timer = setTimeout(callback, ms);" +
//                "    };" +
//                "})();" +
                "function getData (){" +
//                "const http = new XMLHttpRequest();" +
                //             "http.open('GET', '$url');" +
//                "http.onreadystatechange = function() {" +
//                "if (this.readyState === 4 && this.status === 200) {" +
//                "const text = http.responseText ;" +
//                "const jsonRegex = /\\/\\*(.*?)\\*\\//s;" +
//                "const jsonMatch = text.match(jsonRegex);" +
//                "const jsonData = JSON.parse(jsonData);" +
                "Android.callbackHandle('$id' , JSON.stringify($dummy));" +
//                "}};" +
                //               "http.send();" +
                "}" +
                "getData();")


    }

    @JavascriptInterface
    public fun callbackHandle(str: String, data: String) {
        ((applicationContext as MyApp).getCurrentActivity() as BaseActivity).render()

        Log.d("CallbackHandle", "CallbackHandle called with str: $str, data: $data")
        when (str) {
            "logged in" -> startActivity(Intent(this, RouterDataActivity::class.java))
            "device info" -> Toast.makeText(this, data, Toast.LENGTH_LONG).show()
            "navigate" -> startActivity(Intent(this, RouterDataActivity::class.java))
            else -> {
//                Toast.makeText(this, data, Toast.LENGTH_LONG).show()
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
