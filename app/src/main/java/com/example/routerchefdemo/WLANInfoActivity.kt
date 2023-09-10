package com.example.routerchefdemo

import android.os.Bundle
import android.view.View
import com.example.routerchefdemo.databinding.ActivityWlaninfoBinding

class WLANInfoActivity :BaseActivity<ActivityWlaninfoBinding>() {
    override fun getViewBinding() = ActivityWlaninfoBinding.inflate(layoutInflater)
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