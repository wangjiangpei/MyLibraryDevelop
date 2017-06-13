package com.dscs.tools

import com.dscs.tools.utils.JsonUtils
import org.junit.Test

/**
 *
 */
class MyTest {
    @Test
    fun testsWork() {
        val json:String  = "{\n" +
                "    \"name\": \"BeJson\",\n" +
                "    \"url\": \"http://www.bejson.com\",\n" +
                "    \"page\": 88,\n" +
                "    \"isNonProfit\": true\n" +
                "}"
        val url = JsonUtils(json).getJsonStr("url")
        println(url)
    }
}