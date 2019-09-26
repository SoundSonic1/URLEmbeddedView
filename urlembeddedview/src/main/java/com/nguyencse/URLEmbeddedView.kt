package com.nguyencse

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import coil.api.load
import com.nguyencse.urlembeddedview.R

class URLEmbeddedView : ConstraintLayout, LifecycleObserver {

    private var txtTitle: TextView? = null
    private var txtDescription: TextView? = null
    private var txtHost: TextView? = null
    private var imgThumbnail: ImageView? = null
    private var imgFavorIcon: ImageView? = null
    private var cslOGP: ConstraintLayout? = null
    private var cslOGPData: ConstraintLayout? = null
    private var prgLoading: ProgressBar? = null
    private var urlTask: URLEmbeddedTask? = null

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_url_embedded_view, this, true)

        this.txtTitle = findViewById(R.id.txtTitle)
        this.txtDescription = findViewById(R.id.txtDescription)
        this.txtHost = findViewById(R.id.txtURL)
        this.imgThumbnail = findViewById(R.id.imgThumbnail)
        this.imgFavorIcon = findViewById(R.id.imgFavorIcon)
        this.cslOGP = findViewById(R.id.cslOGP)
        this.cslOGPData = findViewById(R.id.cslOGPData)
        this.prgLoading = findViewById(R.id.prg_loading)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.URLEmbeddedView, 0, 0)
        this.txtTitle!!.text = typedArray.getText(R.styleable.URLEmbeddedView_title)
        this.txtDescription!!.text = typedArray.getText(R.styleable.URLEmbeddedView_description)
        this.txtHost!!.text = typedArray.getText(R.styleable.URLEmbeddedView_host)
        this.imgFavorIcon!!.setImageResource(typedArray.getResourceId(R.styleable.URLEmbeddedView_favor, 0))
        this.imgThumbnail!!.setImageResource(typedArray.getResourceId(R.styleable.URLEmbeddedView_thumbnail, 0))
        typedArray.recycle()
    }

    fun title(title: String?) = title?.let {
            txtTitle?.text = it
        }

    fun description(description: String?) = description?.let {
            txtDescription?.text = it
        }

    fun host(host: String?) = host?.let {
            txtHost?.text = it
        }


    fun thumbnail(thumbnailURL: String?) =
        thumbnailURL?.let {
            imgThumbnail?.load(it) {
                crossfade(true)
                placeholder(R.drawable.ic_link)
            }
        }


    fun favor(favorURL: String?) =
        favorURL?.let {
            imgFavorIcon?.load(it) {
                crossfade(true)
                placeholder(R.drawable.ic_link)
            }
        }


    fun setURL(url: String, onLoadURLListener: OnLoadURLListener?) {
        prgLoading!!.visibility = View.VISIBLE
        cslOGP!!.visibility = View.VISIBLE
        cslOGPData!!.visibility = View.INVISIBLE

        urlTask = URLEmbeddedTask(object : URLEmbeddedTask.OnLoadURLListener {
            override fun onLoadURLCompleted(data: URLEmbeddedData) {
                prgLoading!!.visibility = View.GONE
                cslOGPData!!.visibility = View.VISIBLE
                onLoadURLListener?.onLoadURLCompleted(data)
            }
        })
        urlTask?.execute(url)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopTask() = urlTask?.cancel(true)

    interface OnLoadURLListener {
        fun onLoadURLCompleted(data: URLEmbeddedData)
    }
}
