package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.routerchefdemo.databinding.ActivityChangePasswordBinding
import com.example.routerchefdemo.routerModels.RouterModel

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {
    override fun getViewBinding() = ActivityChangePasswordBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root

        setContentView(view)

        setupToolbar(title = "change password")
        (applicationContext as MyApp).webView.loadUrl(RouterModel.getInstance().changePasswordPath)

        binding.bApply.setOnClickListener {
            (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                    RouterModel.getInstance().changePassword(binding.etSsid.text.toString() , binding.etPassword.text.toString()), null
            )
        }
    }

    override fun onPageLoaded(id: String) {
        if (id != Constants.CHANGE_PASSWORD)
            return

    }

    override fun render(id: String, data: String) {
        Toast.makeText(this, "$data", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@ChangePasswordActivity, MainActivity::class.java))
    }

}