package com.example.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private static final int PORT = 7793;
    
    public void listen() throws Exception {
	// 监听 7793 端口，在服务器忙时，可以与之保持连接请求的等待客户数量为 5。
	ServerSocket server = new ServerSocket(PORT, 5);
	while(true) {
	    // 等待客户端的连接
	    final Socket client = server.accept();
	    new Thread() {
		public void run() {
		    OutputStream os = null;
		    try {
			// 读取客户端传来的请求
			InputStream is = client.getInputStream();
			byte[] buf = new byte[1024];
			int len = is.read(buf);
			String response = HttpRequest.sendRequest(new String(buf, 0, len));
			// 写入回复
			os = client.getOutputStream();
			os.write(response.getBytes());
		    } catch(Exception e) {
			e.printStackTrace();
		    } finally {
			try {
			    if(os != null) {
				os.close();
			    }
			    if (client != null) {
				client.close();
			    }
			} catch (Exception e1) {
			    e1.printStackTrace();
			}
		    }
		}
	    }.start();
	}
    }
}
