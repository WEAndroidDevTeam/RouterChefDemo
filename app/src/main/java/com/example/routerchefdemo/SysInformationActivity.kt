package com.example.routerchefdemo


import android.content.Intent
import android.os.Bundle
import android.util.Xml
import android.view.View
import com.example.routerchefdemo.Constants.SYSTEM_INFO
import com.example.routerchefdemo.databinding.ActivityHomeBinding
import com.example.routerchefdemo.databinding.ActivitySysInformationBinding
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

class SysInformationActivity : BaseActivity<ActivitySysInformationBinding>() {
    override fun getViewBinding() = ActivitySysInformationBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "System Info")

//        Constants.webview.loadUrl("https://192.168.1.1/getpage.lua?pid=123&nextpage=ManagDiag_StatusManag_t.lp&Menu3Location=0")
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/common_page/ManagReg_lua.lua",
                SYSTEM_INFO,
                "{\"DeviceName\":\"DG8045\",\"SerialNumber\":\"21530369847SK9119299\",\"ManufacturerOUI\":\"00E0FC\",\"UpTime\":10638,\"SoftwareVersion\":\"V100R019C105B629 TEDATA\",\"HardwareVersion\":\"VER.A\"}"
            ), null
        )

    }

    override fun render(str: String, data: String) {
        if(str != SYSTEM_INFO)
            return
        binding.progressCircular.visibility = View.GONE
        var deviceInfo: DeviceInfo = parseDeviceInfo(data)
        binding.textView2.text = deviceInfo.deviceName
        binding.textView3.text = deviceInfo.serialNumber
        binding.tvHardVersion.text = deviceInfo.hardwareVersion
        binding.tVSoftVersion.text = deviceInfo.softwareVersion
        binding.tVTime.text = formatMillisecondsToDuration(deviceInfo.uptime)


    }

    fun parseDeviceInfo(jsonString: String): DeviceInfo {
        var deviceInfo: DeviceInfo? = null
        val deviceName: String
        val serialNumber: String
        var softwareVersion: String? = ""
        val hardwareVersion: String
        val uptime : String

///////////////////////////
        val parser: XmlPullParser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(StringReader(jsonString))

        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name == "ParaName" && parser.nextText() == "SoftwareVer") {
                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.eventType != XmlPullParser.START_TAG) {
                                continue
                            }

                            if (parser.name == "ParaValue") {
                                softwareVersion = parser.nextText()
                                break
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "Instance" && !softwareVersion.isNullOrBlank()) {
                        deviceInfo = DeviceInfo("nameee", "S/N", softwareVersion, "HWW", 9L)
                    }
                }
            }

            eventType = parser.next()
        }

        return deviceInfo!!
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