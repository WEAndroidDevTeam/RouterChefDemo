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
        Constants.webview.loadUrl("https://192.168.1.1/getpage.lua?pid=1002&nextpage=Localnet_WlanBasicAd_t.lp")

        binding.bApply.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            Constants.webview.evaluateJavascript("javascript: " +
                    "function handleWifiSettings() {" +
                    "let applied = false;" +

                    "let ssid = '${binding.etSsid.text.toString()}';" +
                    "let password = '${binding.etPassword.text.toString()}';" +
                    "let hidden = false;" +
                    "let open = false;" +

                    "let maxClients = 28;" +
                    "    let exit = setTimeout(() => {" +
                    "        clearInterval(temp);" +
                    "        clearTimeout(exit);" +
                    "                console.log('relogin relogin relogin relogin');" +
                    "        Android.callbackHandle('${Constants.CHANGE_PASSWORD}' , 'relogin');" +
                    "    }, 10000);" +

                    "    let temp = setInterval(() => {" +
                    "        try {" +
                    "            if (document.getElementsByClassName('emFont loginTitle')[0]) {" +
                    "                clearInterval(temp);" +
                    "                clearTimeout(exit);" +
                    "                console.log('relogin relogin relogin relogin');" +
                    "                Android.callbackHandle('${Constants.CHANGE_PASSWORD}' , 'relogin');" +
                    "            } else if (document.getElementById('WLANSSIDConf_container').style.display === 'none') {" +
                    "                console.log('clickkkk on WLANSSIDConfBar');" +
                    "                document.getElementById('WLANSSIDConfBar').click();" +
                    "            } else {" +
                    "                if (document.getElementById('confirmLayer').style.display !== 'none') {" +
                    "                    document.getElementById('confirmOK').click();" +
                    "                    clearInterval(temp);" +
                    "                    clearTimeout(exit);" +
                    "                    setTimeout(() => {" +

                    "                console.log('confirmmmmm');" +
                    "                    }, 1000);" +
                    "                } else if (applied) {" +
                    "                    clearInterval(temp);" +
                    "                    clearTimeout(exit);" +
                    "                    setTimeout(() => {" +

                    "                console.log('appliedddddd');" +
                    "                        Android.callbackHandle('${Constants.CHANGE_PASSWORD}' , 'wait until success');" +
                    "                    }, 1000);" +
                    "                } else if (!document.getElementById('KeyPassphrase:0').value.includes('/t') && document.getElementById('KeyPassphrase:0').value !== '\\t\\t\\t\\t\\t\\t') {" +

                    "                console.log('elseeeeeeeeiffffffffffffffff');" +
                    "                    document.getElementById('ESSID:0').value = ssid;" +
                    "                    if (hidden) {" +
                    "                        document.getElementById('ESSIDHideEnable0:0').checked = true;" +
                    "                    } else {" +
                    "                        document.getElementById('ESSIDHideEnable1:0').checked = true;" +
                    "                    }" +
                    "                    if (!open) {" +
                    "                        document.getElementById('EncryptionType:0').value = 'WPA/WPA2-PSK-TKIP/AES';" +
                    "                        if (password) {" +
                    "                            document.getElementById('KeyPassphrase:0').value = password;" +
                    "                        }" +
                    "                    } else {" +
                    "                        document.getElementById('EncryptionType:0').value = 'No Security';" +
                    "                    }" +
                    "                    document.getElementById('MaxUserNum:0') && (document.getElementById('MaxUserNum:0').value = maxClients);" +
                    "                    if (document.getElementById('Btn_apply_WLANSSIDConf:0')) {" +
                    "                        document.getElementById('Btn_apply_WLANSSIDConf:0').click();" +
                    "                        applied = true;" +
                    "                        console.log('applied trueeeeee');" +
                    "                    }" +
                    "                } else {" +
                    "                    document.getElementById('Switch_KeyPassType:0').click();" +
                    "                }" +
                    "            }" +
                    "        } catch (err) {" +
                    "           console.log('caaaaaaaaaaatch');" +
                    "        }" +
                    "    }, 1000);" +
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