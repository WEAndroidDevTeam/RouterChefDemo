package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityWlanAccessBinding

class WlanAccessActivity :BaseActivity<ActivityWlanAccessBinding>() {
    override fun getViewBinding() = ActivityWlanAccessBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)

    }


    override fun render(str: String, data: String) {
        TODO("Not yet implemented")
    }
}