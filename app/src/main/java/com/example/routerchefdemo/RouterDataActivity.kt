package com.example.routerchefdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityRouterDataBinding


class RouterDataActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRouterDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.bApply.setOnClickListener {
            Constants.webview.evaluateJavascript(getChangeDataScript(binding.etSsid.text.toString(), binding.etPassword.text.toString()), null)
        }
    }


    fun getChangeDataScript(ssid: String, password: String): String {
        return ("javascript: " +
                "document.querySelector('button#wifi_wizard_save.atp_button.fontweight_thick').addEventListener('click', function(e){" +
                "document.querySelector('#home_wifi_access24id_ctrl').addEventListener('change', function(){" +
                "document.querySelector('#home_wifi_access24id_ctrl').value = '$ssid';" +
                "});" +
                "document.querySelector('#hidesharekeyMenu_Password_ctrl').addEventListener('change', function(){" +
                "document.querySelector('#hidesharekeyMenu_Password_ctrl').value = '$password';" +
                "});" +
                "document.querySelector('#home_wifi_access24id_ctrl').dispatchEvent(new Event('change', {'bubbles': true}));" +
                "document.querySelector('#hidesharekeyMenu_Password_ctrl').dispatchEvent(new Event('change', {'bubbles': true}));" +
                "});" +
                "document.querySelector('button#wifi_wizard_save.atp_button.fontweight_thick').click();" +
                "Android.callbackHandle('wait few seconds till new settings apply');")
    }
}