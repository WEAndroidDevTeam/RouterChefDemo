package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.LAN_INTERFACE_DHCP_SERVER
import com.example.routerchefdemo.Constants.LAN_INTERFACE_IPV6
import com.example.routerchefdemo.Constants.LAN_INTERFACE_RA
import com.example.routerchefdemo.Constants.LAN_INTERFACE_STATUS
import com.example.routerchefdemo.Constants.LAN_INTERFACE_TR064
import com.example.routerchefdemo.Constants.LAN_INTERFACE_UPNP
import com.example.routerchefdemo.databinding.ActivityLaninterfaceBinding
import com.example.routerchefdemo.routerModels.RouterModel
import org.json.JSONObject

class LANInterfaceActivity : BaseActivity<ActivityLaninterfaceBinding>() {
    override fun getViewBinding() = ActivityLaninterfaceBinding.inflate(layoutInflater)

    private var isConstrainstLayoutVisible = true
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "LAN Interface")

        if (RouterModel.getInstance().lanInterfacePath == "")
            onPageLoaded("Huawei Router")
        else
            (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().lanInterfacePath)
        binding.tVLanStatus.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLLanInterfaceStatus.visibility = View.VISIBLE
            } else {
                binding.cLLanInterfaceStatus.visibility = View.GONE

            }
        }
    }


    override fun onPageLoaded(id: String) {
        (applicationContext as MyApp).webView.evaluateJavascript(
            "javascript: " +
                    RouterModel.getInstance().getLanInterface(), null
        )
    }

    override fun render(id: String, data: String) {
        binding.progressCircular.visibility = View.GONE
        if (id != LAN_INTERFACE_STATUS)
            return
        val result = router.extractMacAndIp(data)
        binding.tVMacNum.text = result.first
        binding.tVIpNum.text = result.second
        binding.cLLanInterfaceStatus.visibility = View.VISIBLE

    }
}

