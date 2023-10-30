package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.routerchefdemo.Constants.RESPONSE_TYPE_JSON
import java.util.regex.Pattern

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B
    abstract fun getViewBinding(): B
    abstract fun setCurrentActivity()
    abstract fun render(str: String, data: String)
    protected val router: Router
        get() = Router.getInstance()
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

    fun callAPI(url: String, id: String, dummy: String? = null, responseType: String? = RESPONSE_TYPE_JSON): String {
        return ("javascript: " +
                "function getData (){" +
                "console.log('dataaaa' + '$url' );" +
                "const http = new XMLHttpRequest();" +
                "http.open('GET', '$url');" +
                "http.onreadystatechange = function() {" +
                "    if (this.readyState === 4) {" +
                "        if (this.status === 200) {" +
                "            const response = http.responseText;"+
                "                   console.log('logrsponse: ' + response);" +
                "            Android.callbackHandle('$id', response);" +
                "        } else {" +
                "            console.log('Request failed with status: ' + this.status);" +
                "            try {" +
                "                const response = http.responseText;" +
                "                const contentType = http.getResponseHeader('Content-Type');" +
                "                if ('$responseType' === 'JSON') {" +
                "                    const errorResponse = JSON.parse(response);" +
                "                    if (errorResponse && errorResponse.message) {" +
                "                        console.log('Error Message: ' + errorResponse.message);" +
                "                       Android.callbackHandle('$id', errorResponse.message);" +
                "                    } else {" +
                "                        console.log('Error Message: Unknown');" +
                "                    }" +
                "                } else if ('$responseType' === 'XML') {" +
                "                    const parser = new DOMParser();" +
                "                    const xmlDoc = parser.parseFromString(response, 'text/xml');" +
                "                    const errorElement = xmlDoc.querySelector('IF_ERRORSTR');" +
                "                    if (errorElement) {" +
                "                        const errorMessage = errorElement.textContent;" +
                "                        console.log('Error Message: ' + errorMessage);" +
                "                       Android.callbackHandle('$id', errorMessage);" +
                "                    } else {" +
                "                        console.log('Error Message: Unknown');" +
                "                    }" +
                "                } else {" +
                "                    console.log('Unsupported Content-Type: ' + contentType);" +
                "                    console.log('Error Message: Unknown');" +
                "                    console.log('Content-Type:', contentType);" +
                "                    console.log('Response:', response);" +
                "                }" +
                "            } catch (error) {" +
                "                console.log('Error parsing API response:', error);" +
                "                console.log('Error Message: Unknown');" +
                "                Android.callbackHandle('$id', 'relogin');" +
                "            }" +
                "        }" +
                "    }" +
                "};" +
                "http.send();" +
                "}" +
                "getData();")
    }
    @SuppressLint("SuspiciousIndentation")
    @JavascriptInterface
    public fun callbackHandle(str: String, jsonData: String, responseType: String? = RESPONSE_TYPE_JSON) {
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
