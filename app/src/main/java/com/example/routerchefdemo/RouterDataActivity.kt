package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.routerchefdemo.databinding.ActivityRouterDataBinding

class RouterDataActivity : BaseActivity<ActivityRouterDataBinding>() {
    override fun getViewBinding() = ActivityRouterDataBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root

        setContentView(view)

        binding.bApply.setOnClickListener {
            Constants.webview.evaluateJavascript(callAPI("https://192.168.1.1/api/system/deviceinfo", "device info"), null)
            Constants.webview.evaluateJavascript(callAPI("https://192.168.1.1/api/ntwk/lan_host", "3"), null)
        }

        binding.bApplyy.setOnClickListener {
            Constants.webview.evaluateJavascript(callAPI("https://192.168.1.1/api/ntwk/lan_host", "3"), null)
        }
    }

    override fun render(str: String, data: String) {
        Toast.makeText(this, "dataa", Toast.LENGTH_LONG).show()
    }

}