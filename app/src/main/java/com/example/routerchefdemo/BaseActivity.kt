package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = getViewBinding()
        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        binding.root.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener{onBackPressed()}
    }

    //region toolbar//
    @SuppressLint("ResourceAsColor")
    fun setupToolbar(titleResId: Int, isLight: Boolean = false, showUp: Boolean = true, show: Boolean = true) {
        setupToolbar(getString(titleResId), isLight, showUp, show)
    }

    @SuppressLint("ResourceAsColor")
    fun setupToolbar(title: String, isLight: Boolean = false, showUp: Boolean = true, show: Boolean = true) {
        when (show) {
            true -> {
                supportActionBar?.show()
                supportActionBar?.title = title
                supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
//                if (isLight) {
//                }
            }
            false -> supportActionBar?.hide()
        }
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
                "                console.log('httpresponseText ' + text);" +
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
                "                Android.callbackHandle('$id', 'relogin');" +
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
        if(jsonData == "relogin"){
            startActivity(Intent(this@BaseActivity, MainActivity::class.java))
            return
        }
        if(jsonData.isNullOrEmpty())
            return

        var data = jsonData

        try {
            val pattern = Pattern.compile("/\\*\\{(.*?)\\}\\*/")
            val matcher = pattern.matcher(data)
            if (matcher.find()) {
                data = "{"+matcher.group(1)+"}"
            }else {
                val pattern = Pattern.compile("/\\*\\[(.*?)\\]\\*/")
                val matcher = pattern.matcher(data)
                if (matcher.find()) {
                    data = "[" + matcher.group(1)+ "]"
                }
            }
        }catch (e: Exception){
        }finally {
            runOnUiThread {
                ((applicationContext as MyApp).getCurrentActivity() as BaseActivity<ViewBinding>).render(
                    str,
                    data
                )
            }
        }
    }

}
