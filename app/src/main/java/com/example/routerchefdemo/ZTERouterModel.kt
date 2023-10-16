package com.example.routerchefdemo

import android.util.Xml
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

class ZTERouterModel : Router() {
  override  fun parseConnectedDevices(xmlString: String): List<ConnectedDevice> {
        val connectedDevices = mutableListOf<ConnectedDevice>()

        val parser: XmlPullParser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(StringReader(xmlString))

        var eventType = parser.eventType
        var currentHostName: String? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name == "ParaName" && parser.nextText() == "HostName") {
                        currentHostName = parser.nextText()
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "Instance" && currentHostName != null) {
                        connectedDevices.add(ConnectedDevice(currentHostName))
                        currentHostName = null
                    }
                }
            }
            eventType = parser.next()
        }

        return connectedDevices
    }

    override var urlForConnectedDevices: String = "https://192.168.1.1/api/system/HostInfo"
    override var idForConnectedDevices: String = Constants.CONNECTED_DEVICES
    override var dummyForConnectedDevices: String = "<ajax_response_xml_root><IF_ERRORPARAM>SUCC</IF_ERRORPARAM><IF_ERRORTYPE>SUCC</IF_ERRORTYPE><IF_ERRORSTR>SUCC</IF_ERRORSTR><IF_ERRORID>0</IF_ERRORID><OBJ_ACCESSDEV_ID><Instance><ParaName>HostName</ParaName><ParaValue>EGCAITSVLT134</ParaValue><ParaName>IPAddress</ParaName><ParaValue>192.168.1.2</ParaValue><ParaName>IPV6Address</ParaName><ParaValue>fe80::3188:3990:4907:9eb8</ParaValue><ParaName>MACAddress</ParaName><ParaValue>6c:6a:77:5a:ab:4f</ParaValue><ParaName>AliasName</ParaName><ParaValue>SSID5</ParaValue></Instance><Instance><ParaName>HostName</ParaName><ParaValue>OPPO-A95</ParaValue><ParaName>IPAddress</ParaName><ParaValue>192.168.1.3</ParaValue><ParaName>IPV6Address</ParaName><ParaValue>fe80::485d:dcff:fe31:93c7</ParaValue><ParaName>MACAddress</ParaName><ParaValue>4a:5d:dc:31:93:c7</ParaValue><ParaName>AliasName</ParaName><ParaValue>SSID5</ParaValue></Instance></OBJ_ACCESSDEV_ID><OBJ_WLANRADIO_ID><Instance><ParaName>RadioSwitch</ParaName><ParaValue>1</ParaValue></Instance></OBJ_WLANRADIO_ID></ajax_response_xml_root>"


}
