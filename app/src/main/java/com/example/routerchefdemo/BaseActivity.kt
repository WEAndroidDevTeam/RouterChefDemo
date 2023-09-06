package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.json.JSONObject
import java.util.regex.Pattern

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B
    abstract fun getViewBinding(): B
    abstract fun setCurrentActivity()
    abstract fun render(str: String, data: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentActivity()
        binding = getViewBinding()
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
//                "console.log('dataaaa' + '$url' );" +
//                "const http = new XMLHttpRequest();" +
//                "http.open('GET', '$url');" +
//                "http.onreadystatechange = function() {" +
//                "if (this.readyState === 4 && this.status === 200) {" +
//                "const text = http.responseText ;" +
//                "console.log('dataaaa' + text );" +
                "Android.callbackHandle('$id' , JSON.stringify($dummy));" +
//                "}};" +
//                "http.send();" +
                "}" +
                "getData();")


    }

    @SuppressLint("SuspiciousIndentation")
    @JavascriptInterface
    public fun callbackHandle(str: String, jsonData: String) {
        var data = jsonData
//        val pattern = Pattern.compile("/\\*\\{(.*?)\\}\\*/")
//        val matcher = pattern.matcher(data)
//
//        try {
//            if (matcher.find()) {
//                data = "{"+matcher.group(1).replace("\"", "")+"}"
//            }
            ((applicationContext as MyApp).getCurrentActivity() as BaseActivity<ViewBinding>).render(
                str,
                data
            )

//        }catch (e: Exception){
//            ((applicationContext as MyApp).getCurrentActivity() as BaseActivity<ViewBinding>).render(
//                str,
//                data
//            )
//        }
    }

}
