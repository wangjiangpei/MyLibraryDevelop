package com.dscs.tools.utils

import org.json.JSONObject

/**
 *
 */
class JsonUtils(var json:String){
    var obj:JSONObject? = null;
    init {
       obj = JSONObject(json)
    }
    fun getJsonStr(str:String):String{
        return obj!!.getString(str)
    }
}