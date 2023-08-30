package com.example.routerchefdemo

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

        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/getuserlevel",
                "user level",
                "{\"isadmin\":true}"
            ), null
        )

    }

    override fun render(str: String, data: String) {
        val jsonObject = JSONObject(data)
        val isAdmin = jsonObject.getBoolean("isadmin")
        if (isAdmin)
            binding.tvWelcome.text = "WELCOME Admin "
    }
}