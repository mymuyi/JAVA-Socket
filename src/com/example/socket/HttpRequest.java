package com.example.socket;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 木易
 *
 */
public class HttpRequest {

    public static String sendRequest(String request) throws IOException {
	HttpURLConnection connection = null;
	BufferedReader reader = null;
	InputStream in = null;
	String response = null;
	try {
	    URL url = new URL("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + request);
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setConnectTimeout(5000);
	    connection.setReadTimeout(5000);

	    in = connection.getInputStream();
	    reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	    connection.disconnect();
	    in.close();
	    reader.close();
	    response = JSON.parse(sb.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	    response = "服务器出问题啦";
	} finally {
	    if (in != null) {
		in.close();
	    }
	    if (reader != null) {
		reader.close();
	    }
	}
	return response;
    }
}
