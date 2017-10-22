package com.example.socket;

import java.io.*;
import java.net.Socket;

/**
 * 
 * @author 木易
 * 
 */
public class Client {
    
    private static final int PORT = 7793;
    
    public static String request(String request) throws Exception {
	Socket client = new Socket("127.0.0.1", PORT);
	OutputStream os = client.getOutputStream();
	os.write(request.getBytes());
	Thread.sleep(100);
	InputStream is = client.getInputStream();
	byte[] buf = new byte[1024];
	int len = is.read(buf);
	is.close();
	client.close();
	return new String(buf, 0, len);
    }
}
