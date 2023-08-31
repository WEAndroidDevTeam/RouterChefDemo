package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityWlansettingsBinding

class WLANSettingsActivity : BaseActivity() {
    private lateinit var binding: ActivityWlansettingsBinding

    private var isConstrainstLayoutVisible = true
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWlansettingsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)


        binding.tVBasicSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLBasicSettings.visibility = View.VISIBLE
            } else {
                binding.cLBasicSettings.visibility = View.GONE

            }
        }

        binding.tVWlanEncryp.setOnClickListener {
            if (isConstrainstLayoutVisible) {
                binding.cLWlanEncryption.visibility = View.VISIBLE
            } else {
                binding.cLWlanEncryption.visibility = View.GONE

            }
        }

        binding.tVAdvancedSettings.setOnClickListener {
            if (isConstrainstLayoutVisible) {

                binding.clAdvancedSettings.visibility = View.VISIBLE
            } else {

                binding.clAdvancedSettings.visibility = View.GONE
            }
        }
    }
    override fun render(str: String, data: String) {

    }
}
