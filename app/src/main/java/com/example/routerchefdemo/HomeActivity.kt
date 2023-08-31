package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityHomeBinding
import org.json.JSONObject

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.btMaintain.setOnClickListener {
            val intent = Intent(this, MaintainActivity::class.java)
            startActivity(intent)

        }

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/getuserlevel",
                "user level",
                "{\"isadmin\":true}"
            ), null
        )
        binding.btConnectedDevices.setOnClickListener {


            startActivity(Intent(this, ConnectedDevicesActivity::class.java))
        }
        binding.btGetDevieInfo.setOnClickListener {
            val intent = Intent(this, SysInformationActivity::class.java)
            startActivity(intent)
        }
        binding.btHomeNetwork.setOnClickListener {
            val intent = Intent(this, HomeNetworkActivity::class.java)
            startActivity(intent)

        }
        Constants.webview.evaluateJavascript(callAPI("https://192.168.1.1/api/system/getuserlevel", "user level", "{\"isadmin\":true}"), null)

    }



    override fun render(str: String, data: String) {
        val jsonObject = JSONObject(data)
        val isAdmin = jsonObject.getBoolean("isadmin")
        if (isAdmin)
            binding.tvWelcome.text = "WELCOME Admin "

    }
}