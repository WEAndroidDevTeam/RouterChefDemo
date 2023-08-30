package com.example.routerchefdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routerchefdemo.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity(), BaseActivity.WebViewCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        val view: View = binding.root

        setContentView(view)
        Constants.webview.evaluateJavascript(
            callAPI(
                "https://192.168.1.1/api/system/getuserlevel",
                "user level",
                "{\"isadmin\":true}"
            ), null
        )
        // Set the callback for WebView
        webViewCallback = this
    }

    override fun onCallback(str: String, data: String) {
        Log.d("HomeActivity", "Data received: $data")
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()

    }
}