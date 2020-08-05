package com.java.robbins.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.java.robbins.controller.OrderController;
import com.java.robbins.model.vo.OrderVO;
import com.java.robbins.view.common.MyUtil;

public class OptionSelectPanel extends JPanel{
	private OrderController oc = new OrderController();
	private OrderVO vo;
	
	//패널 변경
	private MainFrame f;
	private MainPanel mp; //처음 화면
	private IceSelectPanel isp; //이전 화면
	private PayPanel pp; //다음 화면
	
	//컨테이너 & 컴포넌트
	private JPanel rootPanel; 
	private JPanel headerPanel;
	private JPanel selectPanel;
	private JPanel cartPanel;
	private JPanel bottomPanel;
	private JLabel header; //headerPanel title
	private JLabel opTitle; //selectPanel title
	private JLabel place; 
	private JLabel conCup;
	private JLabel spoon;
	private JLabel cartTitle; //cartPanel title
	private JLabel size;
	private JLabel sort;
	private JLabel price;
	
	//사용자 입력 컴포넌트
	private JButton btnTakeout; //포장
	private JButton btnToHere; //매장
	private JButton btnCone; //콘
	private JButton btnCup; //컵
	private JComboBox<Integer> spoonEa; //스푼개수
	
	//이벤트 컴포넌트
	private JButton homeBtn; //홈버튼
	private JButton preBtn; //이전버튼
	private JButton nextBtn; //다음버튼
	
	//기타 설정
	private final int WIDTH = 900;
	private final int HEIGHT = 980;
	private final int HD_BT_HEIGHT = 80; //header, bottomPanel height
	private Color gray = new Color(53, 53, 53);
	private Color lightgray = new Color(171, 171, 171);
	private Color pink = new Color(245, 111, 152);
	
	public OptionSelectPanel(MainFrame f, MainPanel mp, IceSelectPanel isp, OrderVO vo) {
		this.f = f;
		this.mp = mp;
		this.isp = isp;
		this.vo = vo;
		
		setBounds(0, 0, WIDTH, HEIGHT);
		
		addRootPanel();
		addHeaderPanel();
		addOptionSelectPanel();
		addCartPanel();
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
		header = new JLabel("Ice Cream");
		header.setBounds(0, 20, WIDTH, 40);
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		header.setForeground(Color.WHITE);
		
		headerPanel.add(homeBtn);
		headerPanel.add(header);
		rootPanel.add(headerPanel);
	}
	
	public void addOptionSelectPanel() {
		selectPanel = new JPanel();
		selectPanel.setBounds(0, 80, WIDTH, 600);
		selectPanel.setBackground(gray);
		selectPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		selectPanel.setLayout(null);
		
		//라벨
		opTitle = new JLabel("주문내역 확인후 아래 항목을 선택해 주세요");
		place = new JLabel("⊙식사장소를 선택해 주세요.");
		conCup = new JLabel("⊙콘/컵을 선택해 주세요.");
		spoon = new JLabel("⊙스푼개수를 선택해 주세요.");
		
		opTitle.setBounds(230, 60, 500, 30);
		place.setBounds(100, 160, 500, 30);
		conCup.setBounds(100, 320, 500, 30);
		spoon.setBounds(100, 480, 500, 30);
		
		opTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		place.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		conCup.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		spoon.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		opTitle.setForeground(pink);
		place.setForeground(Color.LIGHT_GRAY);
		conCup.setForeground(Color.LIGHT_GRAY);
		spoon.setForeground(Color.LIGHT_GRAY);

		//사용자 입력 버튼
		btnTakeout = new JButton("포장", new ImageIcon("images/ico_takeout.png"));
		btnToHere = new JButton("매장", new ImageIcon("images/ico_tohere.png"));
		btnCone = new JButton("콘", new ImageIcon("images/ico_cone.png"));
		btnCup = new JButton("컵", new ImageIcon("images/ico_cup.png"));
		spoonEa = new JComboBox<>();
		for(int i=0; i<=10; i++) {
			spoonEa.addItem(i);
		}
		
		btnTakeout.setBounds(500, 160, 120, 120);
		btnToHere.setBounds(660, 160, 120, 120);
		btnCone.setBounds(500, 320, 120, 120);
		btnCup.setBounds(660, 320, 120, 120);
		spoonEa.setBounds(500, 480, 160, 40);
		
		//버튼 설정
		JButton[] btnArr = {btnTakeout, btnToHere, btnCone, btnCup};
		for(int i=0; i<btnArr.length; i++) {
			btnArr[i].setBackground(gray);	
			btnArr[i].setFocusPainted(false);
			btnArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 18));
			btnArr[i].setForeground(Color.LIGHT_GRAY);
			btnArr[i].setHorizontalTextPosition(SwingConstants.CENTER);
			btnArr[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			btnArr[i].addActionListener(new SelectOptionListener());
			
			selectPanel.add(btnArr[i]);
		}  
		spoonEa.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		spoonEa.setBackground(pink);
		spoonEa.addActionListener(new SelectOptionListener());
		
		selectPanel.add(opTitle);
		selectPanel.add(place);
		selectPanel.add(conCup);
		selectPanel.add(spoon);
		selectPanel.add(spoonEa);
		rootPanel.add(selectPanel);
	}
	
	public void addCartPanel() {
		cartPanel = new JPanel();
		cartPanel.setBounds(0, 680, WIDTH, 195);
		cartPanel.setBackground(new Color(45, 45, 45));
		cartPanel.setLayout(null);
		
		cartTitle = new JLabel("주문할 상품");
		size = new JLabel("사이즈 ["+vo.getSize()+"]");
		sort = new JLabel("<html><p style=\"width:500px\">"+"종류  "+vo.getSelectedList()+"</p></html>");
		price = new JLabel(vo.getPrice() + "원");
		
		cartTitle.setBounds(100, 30, 300, 30);
		size.setBounds(100, 80, 300, 30);
		sort.setBounds(100, 90, 700, 100);
		price.setBounds(670, 70, 200, 30);
		
		cartTitle.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		size.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		sort.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		price.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		
		cartTitle.setForeground(pink);
		size.setForeground(Color.LIGHT_GRAY);
		sort.setForeground(Color.LIGHT_GRAY);
		price.setForeground(pink);
		
		cartPanel.add(cartTitle);
		cartPanel.add(size);
		cartPanel.add(sort);
		cartPanel.add(price);
		rootPanel.add(cartPanel);
	}
	
	public void addBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 875, WIDTH, HD_BT_HEIGHT);
		bottomPanel.setBackground(lightgray);
		bottomPanel.setLayout(null);
		
		//이전, 주문하기 버튼
		preBtn = new JButton("이전");
		nextBtn = new JButton("주문하기     ￦"+vo.getPrice());
		
		preBtn.setBounds(0, 0, WIDTH/2, HD_BT_HEIGHT);
		nextBtn.setBounds(450, 0, WIDTH/2, HD_BT_HEIGHT);
		preBtn.setBackground(lightgray);
		nextBtn.setBackground(pink);
		
		preBtn.setBorderPainted(false); 
		nextBtn.setBorderPainted(false); 
		preBtn.setFocusPainted(false); 
		nextBtn.setFocusPainted(false); 
		
		preBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		nextBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		nextBtn.setForeground(Color.WHITE);
		
		//이벤트리스너 등록
		ChangePanelListener listener = new ChangePanelListener();
		preBtn.addActionListener(listener);
		nextBtn.addActionListener(listener);
		
		bottomPanel.add(preBtn);
		bottomPanel.add(nextBtn);
		rootPanel.add(bottomPanel);
	}
	
	/**
	 * 이벤트리스너 : selectPanel의 옵션 선택 버튼 처리
	 */
	private class SelectOptionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//식사장소 선택
			if(e.getSource() == btnTakeout) {
				vo.setTakeOut(btnTakeout.getText()); //선택한 정보 객체에 담기
				//초기화
				btnToHere.setBackground(gray);
				btnToHere.setForeground(Color.LIGHT_GRAY);
				//변경
				btnTakeout.setBackground(pink);
				btnTakeout.setForeground(gray);
			}
			else if(e.getSource() == btnToHere) {
				vo.setTakeOut(btnToHere.getText());
				//초기화
				btnTakeout.setBackground(gray);
				btnTakeout.setForeground(Color.LIGHT_GRAY);
				//변경
				btnToHere.setBackground(pink);
				btnToHere.setForeground(gray);
			}
			
			//콘&컵 선택
			if(e.getSource() == btnCone) {
				vo.setConeCup(btnCone.getText());
				//초기화
				btnCup.setBackground(gray);
				btnCup.setForeground(Color.LIGHT_GRAY);
				//변경
				btnCone.setBackground(pink);
				btnCone.setForeground(gray);
			}
			else if(e.getSource() == btnCup) {
				vo.setConeCup(btnCup.getText());
				//초기화
				btnCone.setBackground(gray);
				btnCone.setForeground(Color.LIGHT_GRAY);
				//변경
				btnCup.setBackground(pink);
				btnCup.setForeground(gray);
			}
			
			//스푼개수 선택
			if(e.getSource() == spoonEa) {
				vo.setSpoonEa((int)(spoonEa.getSelectedItem()));
			}
			
		}//end of actionPerformed method
		
	}//end of SelectOptionListener class
	
	/**
	 * 이벤트리스너 : 이전, 다음 버튼을 눌렀을 때 패널 변경
	 */
	private class ChangePanelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//처음 화면 MainPanel로 이동
			if(e.getSource() == homeBtn) {
				MyUtil.changePanel(f, OptionSelectPanel.this, mp); 
			}
			//이전 화면  IceSelectPanel로 이동
			else if(e.getSource() == preBtn) {
				MyUtil.changePanel(f, OptionSelectPanel.this, isp); 
			}
			//다음 화면 PayPanel로 이동
			else if(e.getSource() == nextBtn) {
				//옵션을 다 선택하지 않은 경우 경고창 띄우기
				if(vo.getTakeOut() == null || vo.getConeCup() == null || vo.getSpoonEa() == 0) {
					JOptionPane.showMessageDialog(OptionSelectPanel.this, "옵션을 전부 선택해 주세요.");
					return;
				}
				
				pp = new PayPanel(f, mp, OptionSelectPanel.this, vo); //다음화면 객체 생성. vo객체 전달
				MyUtil.changePanel(f, OptionSelectPanel.this, pp); 
			}
			
		}//end of actionPerformed method
		
	}//end of ChangePanelListener class
	
}

