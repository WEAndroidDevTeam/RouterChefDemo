package com.example.routerchefdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.routerchefdemo.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        val view: View = binding.root

        setContentView(view)
        binding.btConnectedDevices.setOnClickListener {
            Constants.webview.evaluateJavascript(
                callAPI(
                    "https://192.168.1.1/api/system/getuserlevel",
                    "user level",
                    "{\"isadmin\":true}"
                ), null
            )
        }
    }
     override fun render() {
        Toast.makeText(this, "hooome", Toast.LENGTH_LONG).show()
    }
}