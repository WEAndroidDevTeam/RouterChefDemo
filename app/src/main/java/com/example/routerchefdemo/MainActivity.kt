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
                    binding.webView.evaluateJavascript(getLoginScript("admin", "762021Loka"), null)
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
                "document.getElementById('Frm_Username').value = '$str'; " +
                "document.getElementById('Frm_Password').value = '$str2'; " +
                "document.getElementById('LoginId').click();" +
                "function wait(ms){" +
                "var start = new Date().getTime();" +
                "var end = start;" +
                "while(end < start + ms) {" +
                "end = new Date().getTime();" +
                "}" +
                "}" +
                "wait(5000);" +
                "if (document.getElementById('WANUrl')) Android.callbackHandle('succeeded');" +
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

