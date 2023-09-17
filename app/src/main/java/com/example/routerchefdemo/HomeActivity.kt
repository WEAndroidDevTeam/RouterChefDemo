package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.routerchefdemo.Constants.USER_LEVEL
import com.example.routerchefdemo.databinding.ActivityHomeBinding
import org.json.JSONObject

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/getuserlevel",
                USER_LEVEL,
                "{\"isadmin\":true}"

            ), null
        )

        binding.btConnectedDevices.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ConnectedDevicesActivity::class.java
                )
            )
        }
        binding.btGetDevieInfo.setOnClickListener {
            val intent = Intent(this, SysInformationActivity::class.java)
            startActivity(intent)
        }
        binding.btHomeNetwork.setOnClickListener {
            val intent = Intent(this, HomeNetworkActivity::class.java)
            startActivity(intent)

        }
        binding.btMaintain.setOnClickListener {
            val intent = Intent(this, MaintainActivity::class.java)
            startActivity(intent)
        }
        binding.btLogout.setOnClickListener {
            Constants.webview.evaluateJavascript("javascript: " +
                    "function LogOut(){" +
                    "    try{" +
                    "       document.querySelector('#signout_ctrl').click();" +
                    "       Android.callbackHandle('logout' , 'relogin');" +
                    "    }catch(err){" +
                    "       Android.callbackHandle('logout' , 'relogin');" +
                    "        return err.message;" +
                    "    }" +
                    "}" +
                    "LogOut();" , null
            )
            }
        }


    override fun render(str: String, data: String) {
        if (str == USER_LEVEL) {
            Log.e("data", data)
            val jsonObject = JSONObject(data)
            val isAdmin = jsonObject.getBoolean("isadmin")
            if (isAdmin)
                binding.tvWelcome.setText("WELCOME Admin ")
        }
    }
}