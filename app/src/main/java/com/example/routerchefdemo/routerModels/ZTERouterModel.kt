package com.example.routerchefdemo.routerModels

import android.util.Xml
import com.example.routerchefdemo.*
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class ZTERouterModel : RouterModel() {

    override var routerModel: String = "ZTE H188A"
    override var loginPath: String = "https://192.168.1.1/"
    override var systemInfoPath: String =
        "https://192.168.1.1/getpage.lua?pid=1002&nextpage=ManagDiag_StatusManag_t.lp"
    override var dslInfoPath: String =
        "https://192.168.1.1/getpage.lua?pid=1002&nextpage=Internet_AdminInternetStatus_DSL_t.lp"
    override var wlanSettingsPath: String =
        "https://192.168.1.1/getpage.lua?pid=1002&nextpage=Localnet_WlanBasicAd_t.lp"
    override var connectedDevicesPath: String =
        "https://192.168.1.1/"
    override var rebootPath: String =
        "https://192.168.1.1/getpage.lua?pid=1002&nextpage=ManagDiag_DeviceManag_t.lp"
    override var wlanInfoPath: String = ""
    override var wlanAccessPath: String = ""
    override var lanInterfacePath: String = "https://192.168.1.1/getpage.lua?pid=1002&nextpage=Localnet_LocalnetStatusAd_t.lp"
    override var changePasswordPath: String = "https://192.168.1.1/getpage.lua?pid=1002&nextpage=Localnet_WlanBasicAd_t.lp"

    override fun login(username: String, password: String): String {
//        return "function login(user, pass, callback) {" +
//                "  try {" +
//                "    document.querySelector('#Frm_Username').value = user;" +
//                "    document.querySelector('#Frm_Password').value = pass;" +
//                "    document.querySelector('#LoginId').click();" +
//                "  } catch (err) {" +
//                "  }" +
//                "}" +
//                "login('$username', '$password', function(result) {" +
//                "  if (result !== undefined) {" +
//                "Android.callbackHandle(id, 'logged in' , result);" +
//                "  }" +
//                "});"
        return "function login(){" +
                "" +
                "let id = '${Constants.LOGIN}' ;" +
                "" +
                "let usernamee = '$username' ;" +
                "" +
                "let password = '$password' ;" +
                "" +
                "" +
                "let exit = setTimeout(() => {" +
                "    clearInterval(temp);" +
                "    clearTimeout(exit);" +
                "    Android.callbackHandle(id, 'timeout');" +
                "}, 100000);" +
                "let temp = setInterval(() => {" +
                "    try {" +
                "        if (document.getElementById('login_error_waittime') && document.getElementById('login_error_waittime').value > 1) {" +
                "            let seconds = document.getElementById('login_error_waittime').value;" +
                "            clearInterval(temp);" +
                "            clearTimeout(exit);" +
                "            Android.callbackHandle(id, 'retry_after '+ parseInt(seconds) );" +
                "        }" +
                "        else {" +
                "            if ([...document.getElementsByTagName('input')].filter(input => input.value == 'Skip')[0]) {" +
                "                [...document.getElementsByTagName('input')].filter(input => input.value == 'Skip')[0].click();" +
                "                Android.callbackHandle(id, 'relogin');" +
                "            }" +
                "            else if (document.getElementById('NewPassword')) {" +
                "                document.getElementById('Btn_cancel').click();" +
                "            }" +
                "            else if (document.getElementById('login_error_span')) {" +
                "" +
                "                if (document.getElementById('Frm_Username')) {" +
                "                    document.getElementById('Frm_Username').value = usernamee;" +
                "                    document.getElementById('Frm_Password').value = password;" +
                "                    document.getElementById('LoginId').click();" +
                "                }" +
                "                if (document.getElementById('login_error_span').innerText.includes('error.')) {" +
                "                    Android.callbackHandle(id, 'invalid_login');" +
                "                }" +
                "" +
                "            } else if (document.getElementById('radio1')) {" +
                "                Android.callbackHandle(id, 'relogin');" +
                "                document.getElementById('radio1').checked = true;" +
                "                document.getElementById('Btn_apply').click();" +
                "            } else if (document.getElementById('Btn_Finish')) {" +
                "                document.getElementById('Btn_Finish').click();" +
                "            } else if (document.getElementById('Btn_Next')) {" +
                "                Android.callbackHandle(id, 'relogin');" +
                "                document.getElementById('Btn_Next').click();" +
                "            } else if (document.getElementById('WANUrl')) {" +
                "                clearInterval(temp);" +
                "                clearTimeout(exit);" +
                "                Android.callbackHandle(id, 'logged in');" +
                "            }" +
                "        }" +
                "    } catch (err){ }" +
                "}, 500);" +
                "" +
                "}" +
                "" +
                "login();"
    }

    override fun getSystemInfo(): String {
        return "function getSystemInfo(){" +
                "" +
                "let id = '${Constants.SYSTEM_INFO}' ;" +
                "" +
                "let exit = setTimeout(() => {" +
//                "    clearInterval(temp);" +
                "    clearTimeout(exit);" +
                "    Android.callbackHandle(id, 'timeout');" +
                "}, 100000);" +
                "let temp = setInterval(() => {" +
                "    try {" +
                "        if (document.getElementsByClassName('emFont loginTitle')[0]) {" +
//                "            clearInterval(temp);" +
                "            clearTimeout(exit);" +
                "            Android.callbackHandle(id, 'relogin');" +
                "" +
                "        } else {" +
                "            Android.callbackHandle(id, 'showing_info');" +
                "            let lineRate = document.getElementById('crate:0').innerText;" +
                "            let upload = (Number(lineRate.split('/')[0]) / 1024).toFixed(1);" +
                "            let download = (Number(lineRate.split('/')[1].split('k')[0]) / 1024).toFixed(1);" +
                "            let lineRateMb = upload + '/' + download + ' Mbps';" +
                "" +
                "            let maxRate = document.getElementById('cmaxrate:0').innerText;" +
                "            let maxUpload = (Number(maxRate.split('/')[0]) / 1024).toFixed(1);" +
                "            let maxDownload = (Number(maxRate.split('/')[1].split('k')[0]) / 1024).toFixed(1);" +
                "            let maxRateMb = maxUpload + '/' + maxDownload + ' Mbps';" +
                "" +
                "" +
                "            if (document.getElementById('cModule_type:0').innerText == 'N/A') {" +
                "                Android.callbackHandle(id, 'null_dsl_info');" +
//                "                clearInterval(temp);" +
                "                clearTimeout(exit);" +
                "            } else {" +
                "" +
                "                let info = {" +
                "                    result: 'dsl_info'," +
                "                    modType: document.getElementById('cModule_type:0').innerText," +
                "                    upload: upload," +
                "                    download: download," +
                "                    lineRate: lineRateMb," +
                "                    maxUpload: maxUpload," +
                "                    maxDownload: maxDownload," +
                "                    maxRate: maxRateMb," +
                "                    noise: document.getElementById('cmargin:0').innerText," +
                "                    chanType: document.getElementById('cdatapath:0').innerText," +
                "                    depth: document.getElementById('cdepth:0').innerText," +
                "                    delay: document.getElementById('cdelay:0').innerText," +
                "                    crc: document.getElementById('ccrc:0').innerText," +
                "                    fec: document.getElementById('cfec:0').innerText," +
                "                    upTime: document.getElementById('cststart:0').innerText" +
                "                }" +
                "" +
//                "                clearInterval(temp);" +
                "                clearTimeout(exit);" +
                "                Android.callbackHandle(id, 'info');" +
                "            }" +
                "        }" +
                "    } catch (err){ }" +
                "}, 500);" +
                "" +
                "}" +
                "" +
                "getSystemInfo();"
    }

    override fun getLanInterface(): String {
        return callAPI(
            "https://192.168.1.1/getpage.lua?pid=1005&nextpage=lanStatus_lua.lua&InstNum=5",
            Constants.LAN_INTERFACE_STATUS
        )
    }

    override fun getDslInfo(): String {
        return "function getDslInfo(){" +
                "let id = '${Constants.DSL_INFO}' ;" +
                "let exit = setTimeout(() => {" +
                "    clearInterval(temp);" +
                "    clearTimeout(exit);" +
                "    Android.callbackHandle(id, 'timeout');" +
                "}, 100000);" +
                "let temp = setInterval(() => {" +
                "    try {" +
                "        if (document.getElementsByClassName('emFont loginTitle')[0]) {" +
                "            clearInterval(temp);" +
                "            clearTimeout(exit);" +
                "            Android.callbackHandle(id, 'relogin');" +
                "" +
                "        } else {" +
                "            let lineRate = document.getElementById('crate:0').innerText;" +
                "            let upload = (Number(lineRate.split('/')[0]) / 1024).toFixed(1);" +
                "            let download = (Number(lineRate.split('/')[1].split('k')[0]) / 1024).toFixed(1);" +
                "            let lineRateMb = upload + '/' + download + ' Mbps';" +
                "" +
                "            let maxRate = document.getElementById('cmaxrate:0').innerText;" +
                "            let maxUpload = (Number(maxRate.split('/')[0]) / 1024).toFixed(1);" +
                "            let maxDownload = (Number(maxRate.split('/')[1].split('k')[0]) / 1024).toFixed(1);" +
                "            let maxRateMb = maxUpload + '/' + maxDownload + ' Mbps';" +
                "" +
                "" +
                "            if (document.getElementById('cModule_type:0').innerText == 'N/A') {" +
                "                Android.callbackHandle(id, 'null_dsl_info');" +
                "                clearInterval(temp);" +
                "                clearTimeout(exit);" +
                "            } else {" +
                "" +
                "                let info = {" +
                "                    result: 'dsl_info'," +
                "                    modType: document.getElementById('cModule_type:0').innerText," +
                "                    upload: upload," +
                "                    download: download," +
                "                    lineRate: lineRateMb," +
                "                    maxUpload: maxUpload," +
                "                    maxDownload: maxDownload," +
                "                    maxRate: maxRateMb," +
                "                    noise: document.getElementById('cmargin:0').innerText," +
                "                    chanType: document.getElementById('cdatapath:0').innerText," +
                "                    depth: document.getElementById('cdepth:0').innerText," +
                "                    delay: document.getElementById('cdelay:0').innerText," +
                "                    crc: document.getElementById('ccrc:0').innerText," +
                "                    fec: document.getElementById('cfec:0').innerText," +
                "                    upTime: document.getElementById('cststart:0').innerText" +
                "                };" +
                "" +
                "                clearInterval(temp);" +
                "                clearTimeout(exit);" +
                "                Android.callbackHandle(id, JSON.stringify(info));" +
                "            }" +
                "        }" +
                "    } catch (err){ }" +
                "}, 5000);" +
                "" +
                "}" +
                "" +
                "getDslInfo();"
    }

    override fun changeSSID(
        ssidName: String?,
        password: String?,
        isEnabled: Boolean?,
        maxClients: String?,
        isHidden: Boolean,
        isOpen: Boolean
    ): String {
        val sb = StringBuilder()
        sb.append("let ssid = '")
        sb.append(ssidName)
        sb.append(
            "';" +
                    "let hidden = "
        )
        sb.append(isHidden)
        sb.append(
            ";" +
                    "let password = '"
        )
        sb.append(password)
        sb.append(
            "';" +
                    "let open = "
        )
        sb.append(isOpen)
        sb.append(
            ";" +
                    "let maxClients = '"
        )
        sb.append(maxClients)
        sb.append(
            "';" +
                    "" +
                    "let id = '${Constants.CHANGE_PASSWORD}' ;" +
                    "let applied = false;" +
                    "let exit = setTimeout(() => {" +
                    "    clearInterval(temp);" +
                    "    clearTimeout(exit);" +
                    "    Android.callbackHandle(id, 'timeout');" +
                    "}, 10000);" +
                    "" +
                    "let temp = setInterval(() => {" +
                    "    try {" +
                    "        if (document.getElementsByClassName('emFont loginTitle')[0]) {" +
                    "            clearInterval(temp);" +
                    "            clearTimeout(exit);" +
                    "            Android.callbackHandle(id, 'relogin');" +
                    "        }" +
                    "        else if (document.getElementById('WLANSSIDConf_container').style.display == 'none') {" +
                    "            document.getElementById('WLANSSIDConfBar').click();" +
                    "            Android.callbackHandle(id, 'applying_settings');" +
                    "        } else {" +
                    "            if (document.getElementById('confirmLayer').style.display != 'none') {" +
                    "                document.getElementById('confirmOK').click();" +
                    "                clearInterval(temp)" +
                    "                clearTimeout(exit);" +
                    "                setTimeout(() => {" +
                    "                    Android.callbackHandle(id, 'executed');" +
                    "                }, 1000);" +
                    "" +
                    "            }else if(applied){" +
                    "                clearInterval(temp)" +
                    "                clearTimeout(exit);" +
                    "                setTimeout(() => {" +
                    "                    Android.callbackHandle(id, 'executed');" +
                    "                }, 1000);" +
                    "            }" +
                    "            else if (!document.getElementById('KeyPassphrase:0').value.includes('/t') && document.getElementById('KeyPassphrase:0').value != '\t\t\t\t\t\t') {" +
                    "" +
                    "                document.getElementById('ESSID:0').value = ssid;" +
                    "" +
                    "                if (hidden) {" +
                    "                    document.getElementById('ESSIDHideEnable0:0').checked = true;" +
                    "                } else {" +
                    "                    document.getElementById('ESSIDHideEnable1:0').checked = true;" +
                    "                }" +
                    "" +
                    "                if (!open) {" +
                    "                    document.getElementById('EncryptionType:0').value = 'WPA/WPA2-PSK-TKIP/AES';" +
                    "                    if (password) {" +
                    "                        document.getElementById('KeyPassphrase:0').value = password;" +
                    "                    }" +
                    "                } else {" +
                    "                    document.getElementById('EncryptionType:0').value = 'No Security';" +
                    "                }" +
                    "                document.getElementById('MaxUserNum:0') && (document.getElementById('MaxUserNum:0').value = maxClients);" +
                    "                if (document.getElementById('Btn_apply_WLANSSIDConf:0')) {" +
                    "                    document.getElementById('Btn_apply_WLANSSIDConf:0').click();" +
                    "                    applied = true;" +
                    "                }" +
                    "            } else {" +
                    "                document.getElementById('Switch_KeyPassType:0').click();" +
                    "            }" +
                    "        }" +
                    "    } catch (err){ }" +
                    "}, 1000);"
        )
        return sb.toString()
    }

    override fun getConnectedDevices(): String {
        return callAPI(
            "https://192.168.1.1/getpage.lua?pid=1005&nextpage=home_wlanDevice_lua.lua&InstNum=5",
            Constants.CONNECTED_DEVICES
        )
    }

    override fun extractMacAndIp(data: String): Pair<String, String> {
        val docBuilderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docBuilderFactory.newDocumentBuilder()
        val inputSource = InputSource(StringReader(data))
        val doc = docBuilder.parse(inputSource)

        val lanInstances: NodeList = (doc.getElementsByTagName("OBJ_ETH_ID").item(0) as Element).getElementsByTagName("Instance")
        var macAddress = ""
        var ipAddress = ""

        for (i in 0 until lanInstances.length) {
            val instance = lanInstances.item(i) as Element
            val lanId = instance.getElementsByTagName("ParaValue").item(1).textContent // LAN ID

            if (lanId == "LAN4") {
                macAddress = instance.getElementsByTagName("ParaValue").item(5).textContent // MAC Address
                val wanLanInstances = (doc.getElementsByTagName("OBJ_WANLAN_ID").item(0) as Element).getElementsByTagName("Instance")
                for (j in 0 until wanLanInstances.length) {
                    val wanLanInstance = wanLanInstances.item(j) as Element
                    val wanLanId = wanLanInstance.getElementsByTagName("ParaValue").item(0).textContent // WANLAN ID
                    if (wanLanId == "IGD.LD1.ETH4") {
                        ipAddress = wanLanInstance.getElementsByTagName("ParaValue").item(1).textContent // IP Address
                        break
                    }
                }
                break
            }
        }

        return Pair(macAddress, ipAddress)
    }
    override fun reboot(): String {
        return "let id = '${Constants.REBOOT}' ;" +
                "let exit = setTimeout(() => {" +
                "    clearInterval(temp);" +
                "    clearTimeout(exit);" +
                "    Android.callbackHandle(id, 'timeout');" +
                "}, 10000);" +
                "" +
                "let temp = setInterval(() => {" +
                "    try {" +
                "        if (document.getElementsByClassName('emFont loginTitle')[0]) {" +
                "            clearInterval(temp);" +
                "            clearTimeout(exit);" +
                "            Android.callbackHandle(id, 'relogin');" +
                "        }" +
                "        else if (document.getElementById('confirmOK').style.display == 'block') {" +
                "            document.getElementById('confirmOK').click();" +
                "            clearInterval(temp);" +
                "            clearTimeout(exit);" +
                "            Android.callbackHandle(id, 'executed');" +
                "        } else {" +
                "            Android.callbackHandle(id, 'applying_settings');" +
                "            document.getElementById('Btn_restart').click();" +
                "        }" +
                "    } catch (err){ }" +
                "}, 500);"
    }

    override fun parseConnectedDevices(xmlString: String): List<ConnectedDevice> {
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
                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.eventType != XmlPullParser.START_TAG) {
                                continue
                            }

                            if (parser.name == "ParaValue") {
                                currentHostName = parser.nextText()
                                break
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "Instance" && currentHostName != null) {
                        connectedDevices.add(ConnectedDevice(currentHostName ,"" ))
                        currentHostName = null
                    }
                }
            }

            eventType = parser.next()
        }

        return connectedDevices
    }

    override fun parseDeviceInfo(xmlData: String): DeviceInfo {
        val docBuilderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docBuilderFactory.newDocumentBuilder()
        val inputSource = InputSource(StringReader(xmlData))
        val doc = docBuilder.parse(inputSource)

        val instanceNode = doc.getElementsByTagName("Instance").item(0) as? Element

        // Check if the instanceNode is null or not
        if (instanceNode == null) {
            println("Instance node not found")
            return DeviceInfo("", "", "", "", 0)
        }

        val paraValues = instanceNode.getElementsByTagName("ParaValue")

        val deviceName = paraValues.item(3)?.textContent ?: ""
        val serialNumber = paraValues.item(5)?.textContent ?: ""
        val softwareVersion = paraValues.item(2)?.textContent ?: ""
        val hardwareVersion = paraValues.item(4)?.textContent ?: ""

        // As the VerDate is in the format 'yyyyMMddHHmmss', parse it accordingly
        val verDate = paraValues.item(1)?.textContent?.toLongOrNull() ?: 0
        val currentTime = System.currentTimeMillis()
        val uptime = currentTime - verDate

        val deviceInfo = DeviceInfo(deviceName, serialNumber, softwareVersion, hardwareVersion, uptime)

        println("Device Information:")
        println("Device Name: ${deviceInfo.deviceName}")
        println("Serial Number: ${deviceInfo.serialNumber}")
        println("Software Version: ${deviceInfo.softwareVersion}")
        println("Hardware Version: ${deviceInfo.hardwareVersion}")
        println("Uptime (seconds): ${deviceInfo.uptime}")

        return deviceInfo
    }

    override fun getWlanInfo(): String {
        TODO("Not yet implemented")
    }

    override fun extractWifiDetails(data: String): WifiDetails {
        TODO("Not yet implemented")
    }

    override fun extractDslDetails(data: String): DslDetails {
        TODO("Not yet implemented")
    }

    override fun getWlanAccess(): String {
        TODO("Not yet implemented")
    }

    override fun changePassword(ssidName: String?, password: String?): String {
        val sb = StringBuilder()
        sb.append("function changeSSID(){")
        sb.append("let ssid = '")
        sb.append(ssidName)
        sb.append(
            "';" +
                    "let password = '"
        )
        sb.append(password)
        sb.append(
            "';" +
                    "" +
                    "let id = '${Constants.CHANGE_PASSWORD}' ;" +
                    "let applied = false;" +
                    "let exit = setTimeout(() => {" +
                    "    clearInterval(temp);" +
                    "    clearTimeout(exit);" +
                    "    Android.callbackHandle(id, 'timeout');" +
                    "}, 15000);" +
                    "" +
                    "let temp = setInterval(() => {" +
                    "    try {" +
                    "        if (document.getElementsByClassName('emFont loginTitle')[0]) {" +
                    "            clearInterval(temp);" +
                    "            clearTimeout(exit);" +
                    "            Android.callbackHandle(id, 'relogin');" +
                    "        }" +
                    "        else if (document.getElementById('WLANSSIDConf_container').style.display == 'none') {" +
                    "            document.getElementById('WLANSSIDConfBar').click();" +
                    "        } else {" +
                    "            if (document.getElementById('confirmLayer').style.display != 'none') {" +
                    "                document.getElementById('confirmOK').click();" +
                    "                clearInterval(temp);" +
                    "                clearTimeout(exit);" +
                    "                setTimeout(() => {" +
                    "                    Android.callbackHandle(id, 'executed');" +
                    "                }, 1000);" +
                    "" +
                    "            }else if(applied){" +
                    "                clearInterval(temp);" +
                    "                clearTimeout(exit);" +
                    "                setTimeout(() => {" +
                    "                    Android.callbackHandle(id, 'executed');" +
                    "                }, 1000);" +
                    "            }" +
                    "            else if (!document.getElementById('KeyPassphrase:0').value.includes('/t') && document.getElementById('KeyPassphrase:0').value != '\t\t\t\t\t\t') {" +
                    "" +
                    "                document.getElementById('ESSID:0').value = ssid;" +
                    "" +
                    "                document.getElementById('EncryptionType:0').value = 'WPA/WPA2-PSK-TKIP/AES';" +
                    "                if (password) {" +
                    "                    document.getElementById('KeyPassphrase:0').value = password;" +
                    "                }" +
                    "                if (document.getElementById('Btn_apply_WLANSSIDConf:0')) {" +
                    "                    document.getElementById('Btn_apply_WLANSSIDConf:0').click();" +
                    "                    applied = true;" +
                    "                }" +
                    "            } else {" +
                    "                document.getElementById('Switch_KeyPassType:0').click();" +
                    "            }" +
                    "        }" +
                    "    } catch (err){ }" +
                    "}, 2000);"
        )
        sb.append("} changeSSID();")
        return sb.toString()
    }
}
