package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.routerchefdemo.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {
    override fun getViewBinding() = ActivityChangePasswordBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)
var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root

        setContentView(view)

        setupToolbar(title = "change password")
        Constants.webview.loadUrl("https://192.168.1.1/getpage.lua?pid=1002&nextpage=Localnet_WlanBasicAd_t.lp")

        binding.bApply.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            if(count == 1) {
                Constants.webview.evaluateJavascript(
                    "javascript: " +
                            "function handleWifiSettings() {" +
                            "let applied = false;" +

                            "let ssid = '${binding.etSsid.text.toString()}';" +
                            "let password = '${binding.etPassword.text.toString()}';" +
                            "let hidden = false;" +
                            "let open = false;" +

                            "let maxClients = 28;" +

                            "                    document.getElementById('ESSID:0').value = ssid;" +
                            "                        document.getElementById('Btn_apply_WLANSSIDConf:0').click();" +
                            "console.log('clickkkkkkkkked');" +
                            "}" +
                            "handleWifiSettings();", null
                )
                return@setOnClickListener
            }
            count ++

            Constants.webview.evaluateJavascript("javascript: " +
                    "function handleWifiSettings() {" +
                    "let applied = false;" +

                    "let ssid = '${binding.etSsid.text.toString()}';" +
                    "let password = '${binding.etPassword.text.toString()}';" +
                    "let hidden = false;" +
                    "let open = false;" +

                    "let maxClients = 28;" +

                    "                document.getElementById('WLANSSIDConfBar').click();" +
                    "console.log('loadeddd');" +
                    "}" +
                    "handleWifiSettings();"
                , null)
        }
    }

    override fun render(str: String, data: String) {
        binding.progressCircular.visibility = View.GONE
        Toast.makeText(this, "$data", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@ChangePasswordActivity, MainActivity::class.java))
    }

}