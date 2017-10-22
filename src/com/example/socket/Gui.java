package com.example.socket;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * @author 木易
 *
 */
public class Gui extends JFrame {

    JTextField inputField;

    JButton send;

    private JTextArea chatContent;

    public Gui() {
	this.setTitle("Socket");
	this.setLayout(new BorderLayout());
	chatContent = new JTextArea();
	chatContent.setFont(new Font("微软雅黑", Font.PLAIN, 18));
	chatContent.setEditable(false);
	JScrollPane showPanel = new JScrollPane(chatContent);

	JPanel inputPanel = new JPanel();
	inputField = new JTextField(21);
	inputField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
	send = new JButton("SEND");
	send.setFont(new Font("微软雅黑", Font.PLAIN, 12));
	inputPanel.add(inputField);
	inputPanel.add(send);
	
	// 添加键盘监听事件，当按下回车时发送消息
	inputField.addKeyListener(new KeyAdapter() {
	    
	    @Override
	    public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String s = KeyEvent.getKeyText(keyCode);
		if("Enter".equals(s)) {
		    sendRequest();
		}
	    }
	});
	
	// 添加鼠标监听事件，当按下按钮时发送消息
	send.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		sendRequest();
	    }
	});
	

	this.add(showPanel, BorderLayout.CENTER);
	this.add(inputPanel, BorderLayout.SOUTH);
	this.setSize(450, 500);
	this.setLocation(100, 100);
	this.setResizable(false);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     *  将消息传送给客户端程序,并将返回的信息显示在界面上
     */
    public void sendRequest() {
	    String request = inputField.getText();
		String response = null;
		if (!"".equals(request)) {
		    chatContent.append("Client: " + request + "\n");
		    try {
			if ("Quit".equals(request) || "quit".equals(request)) {
			    chatContent.append("Server: " + "Buy" + "\n");
			    System.exit(0);
			}
			response = Client.request(request);

		    } catch (Exception e1) {
			e1.printStackTrace();
		    }
		    chatContent.append("Server: " + response + "\n");
		    inputField.setText("");
		}
	}

    public static void main(String[] args) throws Exception {
	try {
	    // 先随意发送一段数据，判断服务器是否开启，
	    Client.request("在吗");
	} catch (Exception e) {
	    // 出现异常说明服务器未开启，开启一个线程去开启服务器
	    new Thread() {
		
		@Override
		public void run() {
		    try {
			new Server().listen();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }.start();
	} finally {
	    new Gui();
	}
    }
}
