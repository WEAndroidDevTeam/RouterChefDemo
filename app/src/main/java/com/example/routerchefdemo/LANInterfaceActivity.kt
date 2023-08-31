package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityLaninterfaceBinding

class LANInterfaceActivity : BaseActivity() {
    private lateinit var binding: ActivityLaninterfaceBinding

    private var isConstrainstLayoutVisible = true
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaninterfaceBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.tVLanStatus.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLLanInterfaceStatus.visibility = View.VISIBLE
            } else {
                binding.cLLanInterfaceStatus.visibility = View.GONE

            }
        }

        binding.tVLanSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.clLanSettings.visibility = View.VISIBLE
            } else {
                binding.clLanSettings.visibility = View.GONE

            }
        }

        binding.tVDhcbServer.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clDhcpServer.visibility = View.VISIBLE
            } else {

                binding.clDhcpServer.visibility = View.GONE
            }
        }
        binding.tVRaSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clRaSettings.visibility = View.VISIBLE
            } else {

                binding.clRaSettings.visibility = View.GONE
            }
        }

        binding.tVIpv6 . setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clIpv6.visibility = View.VISIBLE
            } else {

                binding.clIpv6.visibility = View.GONE
            }
        }

        binding.tVUpnp. setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clUpnp.visibility = View.VISIBLE
            } else {

                binding.clUpnp.visibility = View.GONE
            }

        }
        binding.tVTr064 . setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clTr064.visibility = View.VISIBLE
            } else {

                binding.clTr064.visibility = View.GONE
            }
        }
    }

    override fun render(str: String, data: String) {

    }
}

