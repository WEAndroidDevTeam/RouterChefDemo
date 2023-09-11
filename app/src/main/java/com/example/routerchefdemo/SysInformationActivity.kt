package com.example.routerchefdemo


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.SYSTEM_INFO
import com.example.routerchefdemo.databinding.ActivityHomeBinding
import com.example.routerchefdemo.databinding.ActivitySysInformationBinding
import org.json.JSONObject

class SysInformationActivity : BaseActivity<ActivitySysInformationBinding>() {
    override fun getViewBinding() = ActivitySysInformationBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view: View = binding.root
        setContentView(view)
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/deviceinfo",
                SYSTEM_INFO,
                "{\"DeviceName\":\"DG8045\",\"SerialNumber\":\"21530369847SK9119299\",\"ManufacturerOUI\":\"00E0FC\",\"UpTime\":10638,\"SoftwareVersion\":\"V100R019C105B629 TEDATA\",\"HardwareVersion\":\"VER.A\"}"
            ), null
        )

    }

    override fun render(str: String, data: String) {
        if(str != SYSTEM_INFO)
            return

        var deviceInfo: DeviceInfo = parseDeviceInfo(data)
        binding.textView2.text = deviceInfo.deviceName
        binding.textView3.text = deviceInfo.serialNumber
        binding.tvHardVersion.text = deviceInfo.hardwareVersion
        binding.tVSoftVersion.text = deviceInfo.softwareVersion
        binding.tVTime.text = deviceInfo.uptime.toString()


    }

    fun parseDeviceInfo(jsonString: String): DeviceInfo {
        val jsonObject = JSONObject(jsonString)
        val deviceName = jsonObject.getString("DeviceName")
        val serialNumber = jsonObject.getString("SerialNumber")
        val softwareVersion = jsonObject.getString("SoftwareVersion")
        val hardwareVersion = jsonObject.getString("HardwareVersion")
        val uptime = jsonObject.getInt("UpTime")

        return DeviceInfo(deviceName, serialNumber, softwareVersion, hardwareVersion, uptime)
    }
}