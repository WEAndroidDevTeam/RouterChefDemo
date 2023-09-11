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
                "function getData (){" +
                "console.log('dataaaa' + '$url' );" +
                "const http = new XMLHttpRequest();" +
                "http.open('GET', '$url');" +
//                "    const timeoutDuration = 5;" +
//                "http.timeout = timeoutDuration;" +
//                "    http.ontimeout = function() {" +
//                "        console.log('Request timed out after ' + timeoutDuration + ' milliseconds');" +
//                "    };" +
                "http.onreadystatechange = function() {" +
                "if (this.readyState === 4) {" +
                "            if (this.status === 200) {" +
                "                const text = http.responseText;" +
                "                Android.callbackHandle('$id', text);" +
                "            } else {" +
                "                console.log('fail');" +
                "                console.log('Request failed with status: ' + this.status);" +
                "                try {" +
                "                    const errorResponse = JSON.parse(http.responseText);" +
                "                    if (errorResponse && errorResponse.message) {" +
                "                        console.log('Error Message: ' + errorResponse.message);" +
                "                Android.callbackHandle('$id', errorResponse.message);" +
                "                    } else {" +
                "                        console.log('Error Message: Unknown');" +
                "                    }" +
                "                } catch (error) {" +
                "                    console.log('Error parsing API response:', error);" +
                "                    console.log('Error Message: Unknown');" +
                "                }" +
                "            }" +
                "        }" +
                "    };" +
                "http.send();" +
                "}" +
                "getData();")


    }

    @SuppressLint("SuspiciousIndentation")
    @JavascriptInterface
    public fun callbackHandle(str: String, jsonData: String) {
        var data = jsonData
        val pattern = Pattern.compile("/\\*\\{(.*?)\\}\\*/")
        val matcher = pattern.matcher(data)

        try {
            if (matcher.find()) {
                data = "{"+matcher.group(1)+"}"
            }else {
                val pattern = Pattern.compile("/\\*\\[(.*?)\\]\\*/")
                val matcher = pattern.matcher(data)
                if (matcher.find()) {
                    data = "[" + matcher.group(1)+ "]"
                }
            }
            ((applicationContext as MyApp).getCurrentActivity() as BaseActivity<ViewBinding>).render(
                str,
                data
            )

        }catch (e: Exception){
            ((applicationContext as MyApp).getCurrentActivity() as BaseActivity<ViewBinding>).render(
                str,
                data
            )
        }
    }

}
