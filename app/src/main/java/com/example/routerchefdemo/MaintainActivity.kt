package com.example.routerchefdemo


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityMaintainBinding

class MaintainActivity : BaseActivity() {
    private lateinit var binding: ActivityMaintainBinding
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.btnSysInfo.setOnClickListener {
            val intent = Intent(this, SysInformationActivity::class.java)
            startActivity(intent)
        }
        binding.btnWlanInfo.setOnClickListener {

        }
        binding.btnDslInfo.setOnClickListener {

        }

    }

    override fun render(str: String, data: String) {
        TODO("Not yet implemented")
    }

}