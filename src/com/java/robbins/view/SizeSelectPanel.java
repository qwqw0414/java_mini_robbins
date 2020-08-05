package com.java.robbins.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.java.robbins.controller.OrderController;
import com.java.robbins.model.vo.OrderVO;
import com.java.robbins.view.common.MyUtil;

public class SizeSelectPanel extends JPanel {
	private OrderController oc = new OrderController();
	private OrderVO vo;
	
	//패널 변경
	private MainFrame f;
	private MainPanel mp; //이전 화면 
	private IceSelectPanel isp; //다음 화면
	
	//컨테이너
	private JPanel rootPanel;
	private JPanel headerPanel;
	private JPanel contentsPanel;
	private JPanel bottomPanel;
	
	//컴포넌트
	private JLabel header; //headerPanel title
	private JButton[] btnArr; //사이즈별 버튼 배열
	
	//이벤트 컴포넌트
	private JButton homeBtn; //headerPanel 홈버튼
	private JButton preBtn; //bottomPanel 이전버튼
	
	//기타 설정
	private final int WIDTH = 900;
	private final int HEIGHT = 980;
	private final int HD_BT_HEIGHT = 80; //header, bottomPanel height
	private Color gray = new Color(53, 53, 53);
	private Color lightgray = new Color(171, 171, 171);
	private Color pink = new Color(245, 111, 152);
	//사이즈별 버튼 문자열 정보 배열 
	private static String[][] sArr = {{"싱글레귤러", "images/ico_singleRegular.png"},
									  {"싱글킹", "images/ico_singleKing.png"},
									  {"더블주니어", "images/ico_Doublejunior.png"},
									  {"더블레귤러", "images/ico_doubleRegualr.png"},
									  {"파인트", "images/ico_pint.png"},
									  {"쿼터", "images/ico_quart.png"},
									  {"패밀리", "images/ico_family.png"},
									  {"하프갤런", "images/ico_hafGallon.png"}};
	
	public SizeSelectPanel(MainFrame f, MainPanel mp) {
		this.f = f;
		this.mp = mp;
		
		setBounds(0, 0, WIDTH, HEIGHT); 
		
		addRootPanel();
		addHeaderPanel();
		addContentsPanel();
		addBottomPanel();
		
		add(rootPanel);
	}
	
	public void addRootPanel() {
		rootPanel = new JPanel();
		rootPanel.setBounds(0, 0, WIDTH, HEIGHT);
		rootPanel.setLayout(null);
	}
	
	public void addHeaderPanel() {
		headerPanel = new JPanel();
		headerPanel.setBounds(0, 0, WIDTH, HD_BT_HEIGHT);
		headerPanel.setBackground(gray);
		headerPanel.setLayout(null);
		
		//홈버튼
		homeBtn = new JButton(new ImageIcon("images/ico_home.png"));
		homeBtn.setBounds(50, 0, 80, HD_BT_HEIGHT);
		homeBtn.setBackground(gray);
		homeBtn.setBorderPainted(false);
		homeBtn.setFocusPainted(false);
		homeBtn.addActionListener(new ChangePanelListener());
		
		//타이틀 라벨
		header = new JLabel("Java Robbins");
		header.setBounds(0, 20, WIDTH, 40);
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		header.setForeground(Color.WHITE);
		
		
		headerPanel.add(homeBtn);
		headerPanel.add(header);
		rootPanel.add(headerPanel);
	}
	
	public void addContentsPanel() {
		contentsPanel = new JPanel();
		contentsPanel.setBounds(0, 80, WIDTH, 795);
		contentsPanel.setBackground(gray);
		contentsPanel.setLayout(null);
		
		//사이즈별 버튼 배열
		btnArr = new JButton[sArr.length];
		
		int x = 0;
		int y = 0;
		for(int i=0; i<btnArr.length; i++) {
				if(i != 0 && i%2 == 0) {
					x = 0;
					y += 200;
				}
				btnArr[i] = new JButton(sArr[i][0], new ImageIcon(sArr[i][1]));
				btnArr[i].setBounds(x, y, WIDTH/2, 200);
				btnArr[i].setBackground(gray);	
				btnArr[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));	
				btnArr[i].setFocusPainted(false); 
				btnArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 18));
				btnArr[i].setForeground(Color.LIGHT_GRAY);
				btnArr[i].addActionListener(new ChangePanelListener(i));
				
				contentsPanel.add(btnArr[i]);
				
				x += WIDTH/2;
		}
		
		
		rootPanel.add(contentsPanel);
	}
	
	public void addBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 875, WIDTH, HD_BT_HEIGHT);
		bottomPanel.setBackground(lightgray);
		bottomPanel.setLayout(null);
		
		//이전 버튼
		preBtn = new JButton("이전");
		
		preBtn.setBounds(0, 0, WIDTH/2, HD_BT_HEIGHT);
		preBtn.setBackground(lightgray);
		preBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		preBtn.setBorderPainted(false); 
		preBtn.setFocusPainted(false); 
		preBtn.addActionListener(new ChangePanelListener());
		
		bottomPanel.add(preBtn);
		rootPanel.add(bottomPanel);
	}
	
	/**
	 * 이벤트리스너 : 패널 전환
	 * 사이즈별 버튼 클릭하면 사이즈 정보를 전달해 vo객체를 생성하고, 다음 패널 IceSelectPanel로 이동하기 
	 */
	private class ChangePanelListener implements ActionListener{
		private int index;
		
		public ChangePanelListener() {}
		public ChangePanelListener(int index){
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//홈버튼, 이전버튼 클릭하면 이전 화면 MainPanel로 이동
			if(e.getSource() == preBtn || e.getSource() == homeBtn) { 
				MyUtil.changePanel(f, SizeSelectPanel.this, mp); 
			}
			
			//사이즈별 버튼 클릭하면 vo객체 생성하고, 다음 화면 IceSelectPanel로 이동
			if(e.getSource() == btnArr[index]) {
				vo = oc.selectSize(btnArr[index].getText()); //vo객체에 사이즈, 선택개수, 가격 정보 입력하기
				isp = new IceSelectPanel(f, mp, SizeSelectPanel.this, vo); //다음 화면 객체 생성. 인자값으로 vo객체 전달
				MyUtil.changePanel(f, SizeSelectPanel.this, isp); 
			}
		}
		
	}
	
}
