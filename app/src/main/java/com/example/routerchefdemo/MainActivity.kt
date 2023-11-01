package com.example.routerchefdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.routerchefdemo.Constants.LOGIN
import com.example.routerchefdemo.databinding.ActivityMainBinding
import com.example.routerchefdemo.routerModels.RouterModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun setCurrentActivity() = (applicationContext as MyApp).setCurrentActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = binding.root
        setContentView(view)

        setupToolbar(title = "Router App", showUp = false)
        initializeViews()

        (applicationContext as MyApp).webView.loadUrl("https://192.168.1.1/")
    }

    private fun initializeViews() {

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.router_models_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem == "Select Router Model") {
                    binding.guideline.visibility = View.GONE
                    binding.etPassword.visibility = View.GONE
                    binding.tvPassword.visibility = View.GONE
                    binding.etUsername.visibility = View.GONE
                    binding.tvUsername.visibility = View.GONE
                    binding.bLogin.visibility = View.GONE

                } else {
                    binding.guideline.visibility = View.VISIBLE
                    binding.etPassword.visibility = View.VISIBLE
                    binding.tvPassword.visibility = View.VISIBLE
                    binding.etUsername.visibility = View.VISIBLE
                    binding.tvUsername.visibility = View.VISIBLE
                    binding.bLogin.visibility = View.VISIBLE

                    RouterModel.createRouterModel(selectedItem)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spinner.setSelection(2)

        binding.bLogin.setOnClickListener {
            (applicationContext as MyApp).isLogging = true
            binding.progressCircular.visibility = View.VISIBLE

            (applicationContext as MyApp).webView.evaluateJavascript("javascript: " +
                    RouterModel.getInstance().login(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                ), null
            )
        }
    }

//    fun getLoginScript(username: String, password: String): String {
//        return ("javascript: " +
//                "function login(user, pass, callback) {" +
//                "  try {" +
//                "    document.querySelector('#index_username').value = user;" +
//                "    document.querySelector('#password').value = pass;" +
//                "    document.querySelector('#loginbtn').click();" +
//                "" +
//                "    setTimeout(function () {" +
//                "      var error = document.querySelector('#errorCategory').textContent;" +
//                "      if (typeof callback === 'function') {" +
//                "        callback(error);" +
//                "      }" +
//                "    }, 5000);" +
//                "  } catch (err) {" +
//                "    if (typeof callback === 'function') {" +
//                "      callback(err.message);" +
//                "    }" +
//                "  }" +
//                "}" +
//                "" +
//                "login('$username', '$password', function(result) {" +
//                "  if (result !== undefined) {" +
//                "Android.callbackHandle('logged in' , result);" +
//                "  }" +
//                "});"
//                )
//    }

    override fun onPageLoaded(id: String) {
        if (id != LOGIN)
            return
    }

    override fun render(id: String, data: String) {
        if (id != LOGIN)
            return
        if (data == "succeeded")
            startActivity(Intent(this, HomeActivity::class.java))
        else
            Toast.makeText(this, "login failed $data", Toast.LENGTH_LONG).show()
        (applicationContext as MyApp).isLogging = false
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}