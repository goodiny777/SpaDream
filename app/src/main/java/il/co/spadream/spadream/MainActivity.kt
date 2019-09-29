package il.co.spadream.spadream

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var context: Context
    lateinit var webView: WebView
    val mainUrl = "https://www.spadream.co.il/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPointer()
    }

    override fun onResume() {
        super.onResume()
        if (AppClass.link != null) {
            webView.loadUrl(mainUrl)
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            AlertDialog.Builder(context)
                .setTitle("כבר יוצא")
                .setMessage("האם אתה בטוח?")
                .setNegativeButton("לא") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("כן") { _, _ ->
                    super.onBackPressed()
                }
                .create().show()
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setPointer() {
        context = this

        webView = findViewById(R.id.webView)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE

                if (AppClass.link != null) {
                    webView.loadUrl(AppClass.link)
                }
                if (webView.url != mainUrl) {
                    ivBack.visibility = View.VISIBLE
                }else{
                    ivBack.visibility = View.GONE

                }
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = true
        webView.loadUrl(mainUrl)

        ivBack.setOnClickListener {
            if (AppClass.link != null) {
                webView.loadUrl(mainUrl)
                AppClass.link = null
            }

            onBackPressed() }
    }
}
