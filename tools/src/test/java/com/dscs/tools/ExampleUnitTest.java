package com.dscs.tools;

import com.dscs.tools.utils.JsonUtilsForJava;
import com.dscs.tools.utils.LogUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String json  = "{\n" +
                "    \"name\": \"BeJson\",\n" +
                "    \"url\": \"http://www.bejson.com\",\n" +
                "    \"page\": 88,\n" +
                "    \"isNonProfit\": true\n" +
                "}";
        String url = new JsonUtilsForJava(json).getJsonStr("url");
        LogUtils.i(url);
    }
}