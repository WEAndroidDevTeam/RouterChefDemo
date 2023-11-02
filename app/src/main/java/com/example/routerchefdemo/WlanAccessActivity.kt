package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityWlanAccessBinding
import com.example.routerchefdemo.routerModels.RouterModel
import org.json.JSONObject

class WlanAccessActivity : BaseActivity<ActivityWlanAccessBinding>() {
    override fun getViewBinding() = ActivityWlanAccessBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "WLAN Access")

        (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().wlanAccessPath)
    }



    override fun onPageLoaded(id: String) {
        (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                RouterModel.getInstance().getWlanAccess(), null
        )
    }

    override fun render(id: String, data: String) {
        binding.progressCircular.visibility = View.GONE
        if (id == "Wlan access")
            binding.cBWPS.isChecked = extractWpsEnable(data)

    }

    fun extractWpsEnable(jsonData: String): Boolean {
        val data = JSONObject(jsonData)
        val wpsEnable = data.optBoolean("WpsEnable")

        return wpsEnable
    }
}