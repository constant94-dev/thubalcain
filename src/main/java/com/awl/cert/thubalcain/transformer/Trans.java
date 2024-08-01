package com.awl.cert.thubalcain.transformer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Trans {
    public static String token(String rtn){
        JsonElement element = JsonParser.parseString(rtn);
        return element.getAsJsonObject().get("access_token").getAsString();
    }
}
