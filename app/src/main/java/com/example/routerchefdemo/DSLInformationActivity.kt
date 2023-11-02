package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.Constants.DSL_INFO
import com.example.routerchefdemo.databinding.ActivityDslinformationBinding
import com.example.routerchefdemo.routerModels.RouterModel
import org.json.JSONObject

class DSLInformationActivity : BaseActivity<ActivityDslinformationBinding>() {
    override fun getViewBinding() = ActivityDslinformationBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "DSL Info")

        (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().dslInfoPath)

//        (applicationContext as MyApp).webView.evaluateJavascript(
//            callAPI(
//                "https://192.168.1.1/api/ntwk/dslinfo",
//                DSL_INFO,
//                "{\"DownPower\":0,\"Modulation\":\"\",\"UpCurrRate\":0,\"ShowtimeStart\":0,\"DownstreamMaxBitRate\":0,\"DownAttenuation\":0,\"Status\":\"NoSignal\",\"DataPath\":\"\",\"UpstreamMaxBitRate\":0,\"UpPower\":0,\"ImpulsoNoiseProUs\":0,\"ImpulsoNoiseProDs\":0,\"InterleaveDelayDs\":0,\"UpAttenuation\":0,\"DownMargin\":0,\"InterleaveDelayUs\":0,\"UpMargin\":0,\"DownCurrRate\":0,\"UpDepth\":0,\"DownDepth\":0}"
//            ), null
//        )

    }


    override fun onPageLoaded(id: String) {
        (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                RouterModel.getInstance().getDslInfo(), null
        )
    }

    override fun render(id: String, data: String) {
        if (id != DSL_INFO)
            return
        binding.progressCircular.visibility = View.GONE
        var dslDetails:DslDetails = router.extractDslDetails(data)
        binding.tVDslSync.text = dslDetails.status
        binding.tVConnectionStatus.text = dslDetails.modulation
        binding.tVUpStreamLine.text = dslDetails.upCurrRate.toString()
        binding.tVDownStreamLine.text = dslDetails.downCurrRate.toString()
        binding.tVMaxUpRate.text = dslDetails.upstreamMaxBitRate.toString()
        binding.tVMaxDownRate.text = dslDetails.downstreamMaxBitRate.toString()
        binding.tVUpNoise.text = dslDetails.impulsoNoiseProUs.toString()
        binding.tVDownNoise.text = dslDetails.impulsoNoiseProDs.toString()
        binding.tVUpAttenuation.text = dslDetails.upAttenuation.toString()
        binding.tVDownAttenuation.text = dslDetails.downAttenuation.toString()
        binding.tVUpOutPower.text = dslDetails.upPower.toString()
        binding.tVDownOutPower.text = dslDetails.downPower.toString()
        binding.tVDslUpTime.text = formatMillisecondsToDuration(dslDetails.dslUpTime)
    }


    fun formatMillisecondsToDuration(milliseconds: Int?): String {
        var milliseconds = milliseconds?.times(1000)
        val seconds = (milliseconds?.div(1000))?.toInt()
        val days = seconds?.div(86400)
        val hours = (seconds?.rem(86400))?.div(3600)
        val minutes = (seconds?.rem(3600))?.div(60)
        val remainingSeconds = seconds?.rem(60)

        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, remainingSeconds)
    }
}