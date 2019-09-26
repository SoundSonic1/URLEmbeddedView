package com.nguyencse.urlembedded

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.nguyencse.URLEmbeddedData
import com.nguyencse.URLEmbeddedView

class MainActivity : AppCompatActivity() {

    private var edtURL: EditText? = null
    private var btnURL: Button? = null
    private var urlEmbeddedView: URLEmbeddedView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtURL = findViewById(R.id.edt_url)
        btnURL = findViewById(R.id.btn_url)
        urlEmbeddedView = findViewById(R.id.uev)

        btnURL!!.setOnClickListener {
            urlEmbeddedView!!.setURL(edtURL!!.text.toString(), object : URLEmbeddedView.OnLoadURLListener {
                override fun onLoadURLCompleted(data: URLEmbeddedData) {
                    urlEmbeddedView!!.title(data.title)
                    urlEmbeddedView!!.description(data.description)
                    urlEmbeddedView!!.host(data.host)
                    urlEmbeddedView!!.thumbnail(data.thumbnailURL)
                    urlEmbeddedView!!.favor(data.favorURL)
                }
            })
        }
    }
}
