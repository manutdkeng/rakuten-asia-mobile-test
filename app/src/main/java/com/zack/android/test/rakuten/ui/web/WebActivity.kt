package com.zack.android.test.rakuten.ui.web

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.zack.android.test.rakuten.databinding.ActivityWebBinding

class WebActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return false
            }
        }
        intent.dataString?.replace("rakuten", "http")?.let {
            binding.webView.loadUrl(it)
        }

    }
}