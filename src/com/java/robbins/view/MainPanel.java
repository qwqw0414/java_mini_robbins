package com.java.robbins.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.java.robbins.view.common.MyUtil;

public class MainPanel extends JPanel {
	
	//패널 변경
	private MainFrame f;
	private SizeSelectPanel ssp; //다음 화면
	private AdminPanel ap; //관리자 화면
	private LoginPanel lp; //관리자 로그인 화면
	
	//컨테이너 & 컴포넌트
	private JPanel rootPanel;
	private JPanel headerPanel;
	private JPanel contentsPanel;
	private JLabel header; //headerPanel title
	private JLabel conTitle; //contentsPanel title
	private JLabel describe1; //contentsPanel 설명
	private JLabel describe2; //contentsPanel 설명
	private JLabel bgImg; //contentsPanel bg-image
	
	//이벤트 컴포넌트
	private JButton adminBtn; //headerPanel 관리자 버튼
	
	//기타 설정
	private final int WIDTH = 900;
	private final int HEIGHT = 980;
	private final int HD_BT_HEIGHT = 80; //header, bottomPanel height
	private Color gray = new Color(53, 53, 53);
	private Color pink = new Color(245, 111, 152);
	
	public MainPanel(MainFrame f) {
		this.f = f;
		this.ap = ap;
		this.lp=lp;
		
		ssp = new SizeSelectPanel(f, this); //다음 패널 객체 생성 
		lp = new LoginPanel(f, this);
		
		setBounds(0, 0, WIDTH, HEIGHT);
		setLayout(null);
		
		addRootPanel();
		addHeaderPanel();
		addContentsPanel();
		
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
		
		//타이틀
		header = new JLabel("Java Robbins");
		header.setBounds(300, 20, 300, 40);
		header.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		header.setForeground(Color.WHITE);
		
		//관리자 버튼
		adminBtn = new JButton(new ImageIcon("images/ico_admin.png"));
		adminBtn.setBounds(770, 0, 80, 80);
		adminBtn.setContentAreaFilled(false);
		adminBtn.setBorderPainted(false);
		adminBtn.setFocusPainted(false);
		adminBtn.addActionListener(new ChangePanelListener());
		
		headerPanel.add(header);
		headerPanel.add(adminBtn);
		rootPanel.add(headerPanel);
	}
	
	public void addContentsPanel() {
		contentsPanel = new JPanel();
		contentsPanel.setBounds(0, 80, WIDTH, 900);
		contentsPanel.setBackground(gray);
		contentsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentsPanel.setLayout(null);
		
		//라벨
		conTitle = new JLabel("Ice Cream");
		describe1 = new JLabel("프리미엄 아이스크림의 기준");
		describe2 = new JLabel("자바라빈스");
		bgImg = new JLabel(new ImageIcon("images/메인.png"));
		
		conTitle.setBounds(100, 50, 800, 100);
		describe1.setBounds(100, 180, 800, 35);
		describe2.setBounds(100, 215, 800, 35);
		bgImg.setBounds(300, 290, 550, 550);
		
		conTitle.setFont(new Font("Helvetica", Font.BOLD, 80));
		describe1.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		describe2.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		
		conTitle.setForeground(pink);
		describe1.setForeground(Color.WHITE);
		describe2.setForeground(Color.WHITE);

		
		contentsPanel.add(conTitle);
		contentsPanel.add(describe1);
		contentsPanel.add(describe2);
		contentsPanel.add(bgImg);
		//이벤트 리스너 등록 : 다음 패널 SizeSelectPanel로 이동
		contentsPanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				MyUtil.changePanel(f, MainPanel.this, ssp);
			}
			
		});
		
		rootPanel.add(contentsPanel);
	}

	/**
	 * 이벤트리스너 : 패널 전환 
	 */
	private class ChangePanelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//관리페이지 AdminPanel로 이동
			if(e.getSource() == adminBtn) {
				//화면 가운데에 위치시키기 
				Toolkit tk = Toolkit.getDefaultToolkit();
				Dimension screenSize = tk.getScreenSize();
				int width = 600;
				int height = 400;
				int x = screenSize.width/2 - width/2;
				int y = screenSize.height/2 - height/2;
				
				f.setTitle("관리자 권한 확인");
				f.setBounds(x, y, width, height);
				MyUtil.changePanel(f, MainPanel.this, lp); 
			}
		}
		
	}//end of ChangePanelListener class
	
}
