package com.example.socket;

import net.sf.json.JSONObject;

/**
 * 
 * @author 木易
 * 
 */
public class JSON {
    
    /**
     * 解析 JSON 数据
     * @param jsonData
     * @return
     * @throws Exception
     */
    public static String parse(String jsonData) throws Exception {
	JSONObject jsonObject = JSONObject.fromObject(jsonData);
	return jsonObject.getString("content").replace("{br}", "\n");
    }
}
