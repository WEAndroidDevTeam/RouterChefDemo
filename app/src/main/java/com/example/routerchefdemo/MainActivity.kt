package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityMainBinding
import com.example.routerchefdemo.Constants.webview as webView
@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
class MainActivity : BaseActivity(), BaseActivity.WebViewCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        webViewCallback = this

        Constants.webview = WebView(this)

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.mixedContentMode = 0
        settings.builtInZoomControls = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setAppCacheEnabled(false)

        webView.addJavascriptInterface(this, "Android")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }
        }
        webView.loadUrl("https://192.168.1.1/")

        binding.bLogin.setOnClickListener {
            startActivity(Intent(this, RouterDataActivity::class.java))
//            webView.evaluateJavascript(
//                getLoginScript(
//                    binding.etUsername.text.toString(),
//                    binding.etPassword.text.toString()
//                ), null
//            )
        }
    }

    fun getLoginScript(username: String, password: String): String {
        return ("javascript: " +
                "var delay = ( function() {" +
                "    var timer = 0;" +
                "    return function(callback, ms) {" +
                "        clearTimeout (timer);" +
                "        timer = setTimeout(callback, ms);" +
                "    };" +
                "})();" +

                // Login into Setup home page
                "function Login(username , password) {" +
                "  document.querySelector('#index_username').value=username ;" +
                "  document.querySelector('#password').value=password;" +
                "  document.querySelector('#loginbtn').click();" +
                "}" +

//                // Enter into WLAN Setup
//                "function WlanSetupSection() {" +
//                "    document.querySelector('.wifi_user_status.text_center').click();" +
//                "}" +

                "Login('$username', '$password');" +
                "Android.callbackHandle('logged in' , '');")
    }

    override fun onCallback(str: String, data: String) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()
    }


}