package com.example.routerchefdemo


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityMaintainBinding
import com.example.routerchefdemo.routerModels.RouterModel

class MaintainActivity : BaseActivity<ActivityMaintainBinding>() {
    override fun getViewBinding() = ActivityMaintainBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)
        setupToolbar(title = "Maintain")

        binding.btnSysInfo.setOnClickListener {
            val intent = Intent(this, SysInformationActivity::class.java)
            startActivity(intent)
        }
        binding.btnWlanInfo.setOnClickListener {
            val intent = Intent(this, WLANInfoActivity::class.java)
            startActivity(intent)
        }
        binding.btnDslInfo.setOnClickListener {
            val intent = Intent(this, DSLInformationActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onPageLoaded(id: String) {
    }

    override fun render(id: String, data: String) {
        TODO("Not yet implemented")
    }

}