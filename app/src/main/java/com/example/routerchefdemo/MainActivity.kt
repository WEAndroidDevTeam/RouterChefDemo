package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
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
            /*  val intent = Intent(this, RouterDataActivity::class.java)
              startActivity(intent)
              finish() */
            val webView = WebView(this)
            val settings = webView.settings
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            settings.mixedContentMode = 0
            settings.builtInZoomControls = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setAppCacheEnabled(false)
            //settings.setCacheMode(2);
            webView.addJavascriptInterface(this, "Android");
// run script
            getLoginScript(binding.etUsername.text.toString(),
                binding.etPassword.text.toString())?.let { it1 -> webView.evaluateJavascript(it1, null) };
        }
    }

    fun getLoginScript(str: String?, str2: String?): String? {
            return "let username = \",str,\";\nlet password = \",str2,;\n\nlet exit = setTimeout(() => {\n    clearInterval(temp);\n    clearTimeout(exit);\n    Android.callbackHandle(JSON.stringify({result:\"timeout\"}));    \n}, 25000);\nlet temp = setInterval(() => {\n        if (document.getElementById('login_window')) {\n            let errMsg = document.getElementById('errorCategory').innerText;\n            if (errMsg.includes(\"minute\")) {\n                clearInterval(temp);\n                clearTimeout(exit);\n            Android.callbackHandle(JSON.stringify({ result: \"retry_after\", time: 60 }));\n            }\n            else if(errMsg.includes(\"You are already logged in.\")){\n                clearInterval(temp);\n                clearTimeout(exit);\n                Android.callbackHandle(JSON.stringify({result:\"already_login\"}));\n            }\n             else {\n\n                if (document.getElementById(\"setfirstbutton\")) {\n                    Android.callbackHandle(JSON.stringify({result:\"enforce_login\"}));\n                    document.getElementById(\"setfirstbutton\").click();\n                } else {\n                    if (errMsg.includes(\"Incorrect\")) {\n                        Android.callbackHandle(JSON.stringify({result:\"invalid_login\"}));\n                    }\n                    if (document.getElementById('index_username')) {\n                        Android.callbackHandle(JSON.stringify({result:\"logging_in\"}));\n                        document.getElementById('index_username').value = username;\n                        document.getElementById('password').value = password;\n                        document.getElementById(\"loginbtn\").click();\n                    }\n                }\n            }\n        } else if (document.getElementById('wizard_wifi_title')) {\n            clearInterval(temp);\n            clearTimeout(exit);\n            Android.callbackHandle(JSON.stringify({result:\"login_success\"}));\n        }\n}, 1000);"

    }

}

