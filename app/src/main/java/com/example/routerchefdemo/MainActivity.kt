package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.bLogin.setOnClickListener {
            val settings = binding.webView.settings
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            settings.mixedContentMode = 0
            settings.builtInZoomControls = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setAppCacheEnabled(false)

            binding.webView.addJavascriptInterface(this, "Android")

            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.webView.evaluateJavascript(getLoginScript(binding.etUsername.text.toString(), binding.etPassword.text.toString()), null)
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
            binding.webView.loadUrl("https://192.168.1.1/")
        }
    }

    fun getLoginScript(str: String, str2: String): String {
        return ("javascript: " +
                "var delay = ( function() {" +
                "    var timer = 0;" +
                "    return function(callback, ms) {" +
                "        clearTimeout (timer);" +
                "        timer = setTimeout(callback, ms);" +
                "    };" +
                "})();" +
                "delay(function(){" +

                "if (document.getElementsByName('Password')[0]) { Android.callbackHandle('failed');}" +
                "else { Android.callbackHandle('succeeded'); document.getElementById('setlogin').click(); }" +

                "}, 5000 ); " +

                "document.getElementsByName('Username')[0].value = '$str'; " +
                "document.getElementsByName('Password')[0].value = '$str2'; " +
                "document.getElementById('btnLogin').click();")
}

    @JavascriptInterface
    public fun callbackHandle(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }
}