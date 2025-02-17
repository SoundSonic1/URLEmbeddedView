package com.nguyencse.urlembedded

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nguyencse.URLEmbeddedData
import com.nguyencse.URLEmbeddedView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_url.setOnClickListener {
            url_embedded_view.setURL(edt_url.text.toString(), object : URLEmbeddedView.OnLoadURLListener {
                override fun onLoadURLCompleted(data: URLEmbeddedData) {
                    url_embedded_view.setData(data)
                }
            })
        }
    }
}
