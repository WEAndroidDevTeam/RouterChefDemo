package com.example.routerchefdemo


import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.SYSTEM_INFO
import com.example.routerchefdemo.databinding.ActivitySysInformationBinding
import com.example.routerchefdemo.routerModels.RouterModel
import org.json.JSONObject

class SysInformationActivity : BaseActivity<ActivitySysInformationBinding>() {
    override fun getViewBinding() = ActivitySysInformationBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "System Info")
        (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().systemInfoPath)

    }


    override fun onPageLoaded(id: String) {
        (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                RouterModel.getInstance().getSystemInfo(), null
        )
    }

    override fun render(id: String, data: String) {
        if(id != SYSTEM_INFO)
            return
        binding.progressCircular.visibility = View.GONE
        var deviceInfo: DeviceInfo = router.parseDeviceInfo(data)
        binding.textView2.text = deviceInfo.deviceName
        binding.textView3.text = deviceInfo.serialNumber
        binding.tvHardVersion.text = deviceInfo.hardwareVersion
        binding.tVSoftVersion.text = deviceInfo.softwareVersion
//        binding.tVTime.text = formatMillisecondsToDuration(deviceInfo.uptime)


    }

    fun formatMillisecondsToDuration(milliseconds: Long): String {
        var milliseconds = milliseconds * 1000
        val seconds = (milliseconds / 1000).toInt()
        val days = seconds / 86400
        val hours = (seconds % 86400) / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, remainingSeconds)
    }

}