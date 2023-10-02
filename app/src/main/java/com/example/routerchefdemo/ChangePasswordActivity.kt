package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.routerchefdemo.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {
    override fun getViewBinding() = ActivityChangePasswordBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root

        setContentView(view)

        setupToolbar(title = "change password")
        Constants.webview.loadUrl("https://192.168.1.1/html/wizard/wifi.html")

        binding.bApply.setOnClickListener {
            Constants.webview.evaluateJavascript("javascript: " +
                    "document.querySelector('button#wifi_wizard_save.atp_button.fontweight_thick').addEventListener('click', function(e){" +
                    "document.querySelector('#home_wifi_access24id_ctrl').addEventListener('change', function(){" +
                    "document.querySelector('#home_wifi_access24id_ctrl').value = '${binding.etSsid.text.toString()}';" +
                    "});" +
                    "document.querySelector('#hidesharekeyMenu_Password_ctrl').addEventListener('change', function(){" +
                    "document.querySelector('#hidesharekeyMenu_Password_ctrl').value = '${binding.etPassword.text.toString()}';" +
                    "});" +
                    "document.querySelector('#home_wifi_access24id_ctrl').dispatchEvent(new Event('change', {'bubbles': true}));" +
                    "document.querySelector('#hidesharekeyMenu_Password_ctrl').dispatchEvent(new Event('change', {'bubbles': true}));" +
                    "});" +
                    "document.querySelector('button#wifi_wizard_save.atp_button.fontweight_thick').click();" +
                    "Android.callbackHandle('change password' , 'wait until success');", null)
        }
    }

    override fun render(str: String, data: String) {
        Toast.makeText(this, "$data", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@ChangePasswordActivity, MainActivity::class.java))
    }

}