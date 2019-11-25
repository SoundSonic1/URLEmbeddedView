package com.example.urlembeddedview

import com.nguyencse.URLEmbeddedData
import com.nguyencse.URLEmbeddedTask
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Test class
 */
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class URLEmbeddedTaskTest {

    /**
     * Override Dispatchers.Main
     * see: https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
     */
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private var testData: URLEmbeddedData? = null

    private val task = URLEmbeddedTask(object : URLEmbeddedTask.OnLoadURLListener {
        override fun onLoadURLCompleted(data: URLEmbeddedData) {
            testData = data
        }
    })

    /**
     * Reset data before each test
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        testData = null
    }

    @Test
    fun testUrlEmbeddedData() {

        val expectedData = URLEmbeddedData().apply {
            host = "github.com"
            title = "Build software better, together"
            // there are multiple images on the website, we take the last one
            thumbnailURL = "https://github.githubassets.com/images/modules/open_graph/github-octocat.png"
            favorURL = "https://www.google.com/s2/favicons?domain=github.com"
            description = "GitHub is where people build software. More than 40 million people use GitHub to discover, fork, and contribute to over 100 million projects."
        }

        runBlocking {
            // wait for job to finish
            task.fetchData("https://github.com/").join()
        }
        Assert.assertEquals(expectedData.title, testData?.title)
        Assert.assertEquals(expectedData.host, testData?.host)
        Assert.assertEquals("Expected octacat image", expectedData.thumbnailURL, testData?.thumbnailURL)
        Assert.assertEquals(expectedData.favorURL, testData?.favorURL)
        Assert.assertEquals(expectedData.description, testData?.description)

    }

    @Test
    fun testInvalidUrl() {

        runBlocking {
            task.fetchData("").join()
        }
        Assert.assertEquals("", testData?.title)
        Assert.assertEquals("", testData?.host)
        Assert.assertEquals("", testData?.thumbnailURL)
        Assert.assertEquals("", testData?.favorURL)
        Assert.assertEquals("", testData?.description)

        // TODO: make test work with invalid urls
        runBlocking {
            task.fetchData("https://github.c").join()
        }
        /*Assert.assertEquals("", testData?.title)
        Assert.assertEquals("", testData?.host) */
        Assert.assertEquals("", testData?.thumbnailURL)
        Assert.assertEquals("", testData?.favorURL)
        Assert.assertEquals("", testData?.description)

    }

}
