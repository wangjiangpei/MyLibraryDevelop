package com.dscs.tools.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */

public class JsonUtilsForJava {
    JSONObject obj;

    public JsonUtilsForJava(String json) {
        try {
            this.obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getJsonStr(String str){
            try {
                return obj.getString(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
    }
}
