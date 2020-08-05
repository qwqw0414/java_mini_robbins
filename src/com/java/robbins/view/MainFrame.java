package com.java.robbins.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	//패널 변경
	private JPanel mainPanel;
	private JPanel loginPanel;

	//관리자 페이지 로그인 비밀번호
	private final String PASSWORD = "1111";
	
	public MainFrame() {
		
		//화면 가운데에 위치시키기 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int width = 900;
		int height = 980;
		int x = screenSize.width/2 - width/2;
		
		setTitle("자바라빈스 주문 키오스크");
		setBounds(x, 10, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null); 
		setResizable(false);
		
		Image img = new ImageIcon("images/pavicon.png").getImage();
		setIconImage(img);
		
		mainPanel = new MainPanel(this);
		
		add(mainPanel);
		
		setVisible(true);
	}
	
	public String getPASSWORD() {
		return PASSWORD;
	}
	
}
