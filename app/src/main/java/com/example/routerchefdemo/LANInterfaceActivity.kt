package com.example.routerchefdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class LANInterfaceActivity : AppCompatActivity() {

    private lateinit var cL_lanInterfaceStatus: ConstraintLayout
    private lateinit var cl_lanSettings: ConstraintLayout
    private lateinit var cl_dhcpServer: ConstraintLayout
    private lateinit var cl_raSettings: ConstraintLayout
    private lateinit var cl_ipv6: ConstraintLayout
    private lateinit var cl_upnp: ConstraintLayout
    private lateinit var cl_tr064: ConstraintLayout
    private lateinit var tV_lanStatus: TextView
    private lateinit var tV_lanSettings: TextView
    private lateinit var tV_dhcbServer: TextView
    private lateinit var tV_raSettings: TextView
    private lateinit var tV_ipv6: TextView
    private lateinit var tV_upnp: TextView
    private lateinit var tV_tr064: TextView
    private var isConstrainstLayoutVisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laninterface)


        cL_lanInterfaceStatus = findViewById(R.id.cL_lanInterfaceStatus)
        cl_lanSettings = findViewById(R.id.cl_lanSettings)
        cl_dhcpServer = findViewById(R.id.cl_dhcpServer)
        cl_raSettings = findViewById(R.id.cl_raSettings)
        cl_ipv6 = findViewById(R.id.cl_ipv6)
        cl_upnp = findViewById(R.id.cl_upnp)
        cl_tr064 = findViewById(R.id.cl_tr064)

        tV_lanStatus = findViewById(R.id.tV_lanStatus)
        tV_lanSettings = findViewById(R.id.tV_lanSettings)
        tV_dhcbServer = findViewById(R.id.tV_dhcbServer)
        tV_raSettings = findViewById(R.id.tV_raSettings)
        tV_ipv6 = findViewById(R.id.tV_ipv6)
        tV_upnp = findViewById(R.id.tV_upnp)
        tV_tr064 = findViewById(R.id.tV_tr064)

        tV_lanStatus.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                cL_lanInterfaceStatus.visibility = View.VISIBLE
            } else {
                cL_lanInterfaceStatus.visibility = View.GONE

            }
        }

        tV_lanSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                cl_lanSettings.visibility = View.VISIBLE
            } else {
                cl_lanSettings.visibility = View.GONE

            }
        }

        tV_dhcbServer.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                cl_dhcpServer.visibility = View.VISIBLE
            } else {

                cl_dhcpServer.visibility = View.GONE
            }
        }
        tV_raSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                cl_raSettings.visibility = View.VISIBLE
            } else {

                cl_raSettings.visibility = View.GONE
            }
        }

        tV_ipv6 . setOnClickListener {
            if (isConstrainstLayoutVisible) {

                cl_ipv6.visibility = View.VISIBLE
            } else {

                cl_ipv6.visibility = View.GONE
            }
        }

        tV_upnp . setOnClickListener {
            if (isConstrainstLayoutVisible) {

                cl_upnp.visibility = View.VISIBLE
            } else {

                cl_upnp.visibility = View.GONE
            }

        }
        tV_tr064 . setOnClickListener {
            if (isConstrainstLayoutVisible) {

                cl_tr064.visibility = View.VISIBLE
            } else {

                cl_tr064.visibility = View.GONE
            }
        }
    }
}

