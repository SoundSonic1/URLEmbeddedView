package com.nguyencse

import android.os.AsyncTask

import org.jsoup.Jsoup

import java.io.IOException
import java.net.URL
import java.util.*

class URLEmbeddedTask(
        private val listener: OnLoadURLListener?
) : AsyncTask<String, Void, URLEmbeddedData>() {

    override fun doInBackground(vararg params: String): URLEmbeddedData {
        val data = URLEmbeddedData()
        try {
            var url = params[0]
            url = (if (url.startsWith(URLConstants.PROTOCOL) || url.startsWith(URLConstants.PROTOCOL_S)) "" else URLConstants.PROTOCOL) + url

            val host = URL(url)
            data.host = host.host

            val doc = Jsoup.connect(url).get()
            val elements = doc.select("meta")
            for (e in elements) {
                val tag = e.attr("property").toLowerCase(Locale.ENGLISH)
                val content = e.attr("content")
                when (tag) {
                    "og:url" -> {
                        val urlNew = URL(content)
                        data.host = urlNew.host
                    }
                    "og:image" -> data.thumbnailURL = content
                    "og:title" -> data.title = content
                    "og:description" -> data.description = content
                }
            }
            data.favorURL = URLConstants.ROOT_URL_FAVOR_ICON + data.host
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return data
    }

    override fun onPostExecute(result: URLEmbeddedData) {
        listener?.onLoadURLCompleted(result)
    }

    interface OnLoadURLListener {
        fun onLoadURLCompleted(data: URLEmbeddedData)
    }
}