package com.example.routerchefdemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class WLANSettingsActivity : AppCompatActivity() {
    private lateinit var cL_basicSettings: ConstraintLayout
    private lateinit var cL_wlanEncryption: ConstraintLayout
    private lateinit var cl_advancedSettings: ConstraintLayout
    private lateinit var tV_basicSettings: TextView
    private lateinit var tV_wlanEncryp: TextView
    private lateinit var tV_advancedSettings: TextView
    private var isConstrainstLayoutVisible = true

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wlansettings)

        cL_basicSettings = findViewById(R.id.cL_basicSettings)
        cL_wlanEncryption = findViewById(R.id.cL_wlanEncryption)
        cl_advancedSettings = findViewById(R.id.cl_advancedSettings)
        tV_basicSettings = findViewById(R.id.tV_basicSettings)
        tV_wlanEncryp = findViewById(R.id.tV_wlanEncryp)
        tV_advancedSettings = findViewById(R.id.tV_advancedSettings)

        tV_basicSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                cL_basicSettings.visibility = View.VISIBLE
            } else {
                cL_basicSettings.visibility = View.GONE

            }
        }

        tV_wlanEncryp.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                cL_wlanEncryption.visibility = View.VISIBLE
            } else {
                cL_wlanEncryption.visibility = View.GONE

            }
        }

        tV_advancedSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                cl_advancedSettings.visibility = View.VISIBLE
            } else {

                cl_advancedSettings.visibility = View.GONE
            }
        }
    }
}
