package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.http.SslError
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.viewbinding.ViewBinding
import java.util.regex.Pattern

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
class MyApp : Application() {
    var isLogging = false
    lateinit var webView: WebView

    private var mCurrentActivity: Activity? = null
    fun getCurrentActivity(): Activity? {
        return mCurrentActivity
    }

    fun setCurrentActivity(mCurrentActivity: Activity?) {
        this.mCurrentActivity = mCurrentActivity
    }
    override fun onCreate() {
        super.onCreate()
        initializeWebview()
    }

    public fun initializeWebview() {
//        webView = (mCurrentActivity as BaseActivity<ViewBinding>).binding.root.findViewById<WebView>(R.id.webview)
        webView = WebView(this)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.mixedContentMode = 0
        settings.builtInZoomControls = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setAppCacheEnabled(false)
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        webView.addJavascriptInterface(this, "Android")
        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                (mCurrentActivity as BaseActivity<ViewBinding>).binding.root.findViewById<ProgressBar>(R.id.progress_circular)?.visibility = View.GONE
                (mCurrentActivity as BaseActivity<ViewBinding>).onPageLoaded(Constants.LOGIN)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val newUrl = request!!.url.toString()
                if (isLogging) {
                    (mCurrentActivity as BaseActivity<ViewBinding>).render(Constants.LOGIN, "succeeded")
                }
                return true
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

    }



    @SuppressLint("SuspiciousIndentation")
    @JavascriptInterface
    public fun callbackHandle(id: String, jsonData: String) {
        Log.i("ROUTER", "$id $jsonData")
        if(jsonData == "relogin"){
            mCurrentActivity?.startActivity(Intent(this, MainActivity::class.java))
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
            getCurrentActivity()?.runOnUiThread {
                ((applicationContext as MyApp).getCurrentActivity() as BaseActivity<ViewBinding>).render(
                    id,
                    data
                )
            }
        }
    }

}