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
                    binding.webView.evaluateJavascript(getLoginScript("admin", "K9114659"), null)
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
        return ("javascript: document.getElementById('index_username').value = '$str'; " +
                "document.getElementById('password').value = '$str2'; " +
                "document.getElementById('loginbtn').click();" +
                "if (document.getElementById('wizard_wifi_title')) Android.callbackHandle('succeeded');" +
                "else Android.callbackHandle('failed');")
    }

    @JavascriptInterface
    public fun callbackHandle(str: String) {
        if (str == "succeeded")
            Toast.makeText(this, "succeeded", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "failed", Toast.LENGTH_LONG).show();

    }
}

