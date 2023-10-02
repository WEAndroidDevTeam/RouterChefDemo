package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.example.routerchefdemo.Constants.LOGIN
import com.example.routerchefdemo.databinding.ActivityMainBinding
import com.example.routerchefdemo.Constants.webview as webView

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    private var isLogging = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "Router App", showUp = false)

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
                binding.progressCircular.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val newUrl = request!!.url.toString()
                if(isLogging && newUrl.startsWith("https://192.168.1.1/html/wizard/wizard.html")){
                    render(LOGIN, "succeeded")
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
        webView.loadUrl("https://192.168.1.1/")

        binding.bLogin.setOnClickListener {
            isLogging = true
            webView.evaluateJavascript(
                getLoginScript(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                ), null
            )
        }
    }

    fun getLoginScript(username: String, password: String): String {
        return ("javascript: " +
                "function login(user, pass, callback) {" +
                "  try {" +
                "    document.querySelector('#index_username').value = user;" +
                "    document.querySelector('#password').value = pass;" +
                "    document.querySelector('#loginbtn').click();" +
                "" +
                "    setTimeout(function () {" +
                "      var error = document.querySelector('#errorCategory').textContent;" +
                "      if (typeof callback === 'function') {" +
                "        callback(error);" +
                "      }" +
                "    }, 5000);" +
                "  } catch (err) {" +
                "    if (typeof callback === 'function') {" +
                "      callback(err.message);" +
                "    }" +
                "  }" +
                "}" +
                "" +
                "login('$username', '$password', function(result) {" +
                "  if (result !== undefined) {" +
                "Android.callbackHandle('logged in' , result);" +
                "  }" +
                "});"
                )
    }

    override fun render(str: String, data: String) {
        if(str != LOGIN)
            return
        if(data == "succeeded")
            startActivity(Intent(this, HomeActivity::class.java))
        else
            Toast.makeText(this, "login failed $data", Toast.LENGTH_LONG).show()
        isLogging = false
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}