package com.nguyencse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException
import java.net.URL
import java.util.Locale

class URLEmbeddedTask(private val listener: OnLoadURLListener) {

    fun fetchData(url: String) = CoroutineScope(Dispatchers.Main).launch {
        val result = getUrlEmbeddedData(url)
        listener.onLoadURLCompleted(result)
    }

    // TODO: check whether url is valid
    private suspend fun getUrlEmbeddedData(url: String) = withContext(Dispatchers.IO) {
        val data = URLEmbeddedData()

        if (url.isBlank()) return@withContext data

        try {
            val host = URL(url)
            data.host = host.host

            Jsoup.connect(url).get().select("meta").forEach {
                val tag = it.attr("property").toLowerCase(Locale.ENGLISH)
                val content = it.attr("content")
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

        data
    }

    interface OnLoadURLListener {
        fun onLoadURLCompleted(data: URLEmbeddedData)
    }
}
