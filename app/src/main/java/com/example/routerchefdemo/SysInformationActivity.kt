package com.example.routerchefdemo


import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivitySysInformationBinding

class SysInformationActivity : BaseActivity() {
    private lateinit var binding: ActivitySysInformationBinding
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySysInformationBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

    }

    override fun render(str: String, data: String) {
    }

}