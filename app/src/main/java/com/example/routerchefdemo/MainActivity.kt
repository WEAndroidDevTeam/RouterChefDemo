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
//            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            binding.webView.addJavascriptInterface(this, "Android");
//            binding.webView.evaluateJavascript(getGoogleScript(), null);
//            binding.webView.loadUrl("script: (" + getGoogleScript() + ")")
//            binding.webView.evaluateJavascript("javascript:(function() { document.getElementById('q').value = '\" + email + \"'; })()", null);

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
            //binding.webView.visibility = View.INVISIBLE
// run script
        }
    }
    fun getLoginScript(str: String, str2: String): String {
      //  return ("javascript: document.getElementById('index_username').value = 'e'" +
        // return ( "javascript: document.getElementById('password').value = 'e'")
        //return "script: document.getElementById(\"Btn_Next\").click();"
        return ("javascript: document.getElementById('index_username').value = '$str'; " +
                "document.getElementById('password').value = '$str2'; " +
                "document.getElementById('loginbtn').click();")
    }
  /*  fun getLoginScript(str: String, str2: String): String {
        return concatStrings(
            "script: let username = \"", "admin", "\";\n" +
                    "let password = \"", "K9114659", "\";\n\nlet exit = setTimeout(() => {\n  " +
                    " clearInterval(temp);\n    " +
                    "clearTimeout(exit);\n   " +
                    " Android.callbackHandle(JSON.stringify({ result: \"timeout\" }));\n}, 25000);\n" +
                    "let temp = setInterval(() => {\n    try {\n       " +
                    " if (document.getElementById(\"login_error_waittime\") && document.getElementById" +
                    "(\"login_error_waittime\").value > 1) {\n         " +
                    "   let seconds = document.getElementById(\"login_error_waittime\").value;\n      " +
                    "      clearInterval(temp);\n            clearTimeout(exit);\n            " +
                    "Android.callbackHandle(JSON.stringify({ result: \"retry_after\", time: parseInt(seconds) }));\n " +
                    "       }\n        else {\n            " +
                    "if ([...document.getElementsByTagName(\"input\")]" +
                    ".filter(input => input.value == \"Skip\")[0]) {\n" +
                    "  [...document.getElementsByTagName(\"input\")]." +
                    "filter(input => input.value == \"Skip\")[0].click();\n               " +
                    " Android.callbackHandle(JSON.stringify({ result: \"enforce_login\" }));\n        " +
                    "    }\n            else if (document.getElementById(\"NewPassword\")) {\n     " +
                    "           document.getElementById(\"Btn_cancel\").click();\n            }\n        " +
                    "    else if (document.getElementById('login_error_span')) {\n\n             " +
                    "   if (document.getElementById('Frm_Username')) {\n                   " +
                    " Android.callbackHandle(JSON.stringify({ result: \"logging_in\" }));\n        " +
                    "            document.getElementById('Frm_Username').value = username;\n          " +
                    "          document.getElementById('Frm_Password').value = password;\n          " +
                    "          document.getElementById('LoginId').click();\n                }\n       " +
                    "         if (document.getElementById('login_error_span').innerText.includes(\"error.\")) {\n " +
                    "                   Android.callbackHandle(JSON.stringify({ result: \"invalid_login\" }));\n   " +
                    "             }\n\n            } else if (document.getElementById(\"radio1\")) {\n   " +
                    "             Android.callbackHandle(JSON.stringify({ result: \"enforce_login\" }));\n   " +
                    "             document.getElementById(\"radio1\").checked = true;\n               " +
                    " document.getElementById(\"Btn_apply\").click();\n            }" +
                    " else if (document.getElementById(\"Btn_Finish\")) {\n           " +
                    "     document.getElementById(\"Btn_Finish\").click();\n        " +
                    "    } else if (document.getElementById(\"Btn_Next\")) {\n           " +
                    "     Android.callbackHandle(JSON.stringify({ result: \"enforce_login\" }));\n     " +
                    "           document.getElementById(\"Btn_Next\").click();\n   " +
                    "         } else if (document.getElementById('WANUrl')) {\n            " +
                    "    clearInterval(temp);\n                clearTimeout(exit);\n             " +
                    "   Android.callbackHandle(JSON.stringify({ result: \"login_success\" }));\n        " +
                    "    }\n        }\n    } catch (err){ }\n}, 500);"
        )
        return ""
    }*/

    fun getGoogleScript(): String {
        return "script: document.getElementById('q').value = 'e'; document.getElementById('btnK').click();"
    }

    @JavascriptInterface
    public fun callbackHandle(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();

    }

    public fun concatStrings(
        str: String,
        str2: String,
        str3: String,
        str4: String,
        str5: String
    ): String {
        return str + str2 + str3 + str4 + str5;
    }
}

