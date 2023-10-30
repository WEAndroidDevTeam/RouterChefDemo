package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.router_models_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem == "Select Router Model") {
                    binding.guideline.visibility = View.GONE
                    binding.etPassword.visibility = View.GONE
                    binding.tvPassword.visibility = View.GONE
                    binding.etUsername.visibility = View.GONE
                    binding.tvUsername.visibility = View.GONE
                    binding.bLogin.visibility = View.GONE

                } else {
                    binding.guideline.visibility = View.VISIBLE
                    binding.etPassword.visibility = View.VISIBLE
                    binding.tvPassword.visibility = View.VISIBLE
                    binding.etUsername.visibility = View.VISIBLE
                    binding.tvUsername.visibility = View.VISIBLE
                    binding.bLogin.visibility = View.VISIBLE
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {


            }
        }

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
                if (isLogging) {
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
                "    document.querySelector('#Frm_Username').value = user;" +
                "    document.querySelector('#Frm_Password').value = pass;" +
                "    document.querySelector('#LoginId').click();" +
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


        if (str != LOGIN)
            return
        if (data == "succeeded")
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