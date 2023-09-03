package com.example.routerchefdemo


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityHomeBinding
import com.example.routerchefdemo.databinding.ActivitySysInformationBinding

class SysInformationActivity : BaseActivity<ActivitySysInformationBinding>() {
    override fun getViewBinding() = ActivitySysInformationBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view: View = binding.root
        setContentView(view)

    }

    override fun render(str: String, data: String) {
    }

}