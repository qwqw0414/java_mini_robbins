package com.java.robbins.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.java.robbins.controller.OrderController;
import com.java.robbins.controller.SalesController;
import com.java.robbins.model.vo.OrderVO;
import com.java.robbins.view.common.MyUtil;


public class PayPanel extends JPanel {
	private SalesController sc = new SalesController();
	private OrderController oc = new OrderController();
	private OrderVO vo;

	//패널 변경
	private MainFrame f;
	private MainPanel mp; //처음화면
	private OptionSelectPanel osp; //이전 화면
	
	//컨테이너 & 컴포넌트
	private JPanel rootPanel;
	private JPanel headerPanel;
	private JPanel selectPanel;
	private JPanel keypadPanel;
	private JPanel buttonPanel;
	private JPanel bottomPanel;
	private JLabel header; //headerPanel title
	private JLabel payTitle; //selectPanel title
	private JLabel padTitle; //keypadPanel title
	private JTextField price; //주문금액 출력란
	private JTextField printNum; //키패드 입력 출력란
	
	//사용자 입력 컴포넌트
	private JButton cardBtn; //카드결제
	private JButton cashBtn; //현금결제
	private JButton[] btnArr; //입력 키패드 버튼
	
	//이벤트 컴포넌트
	JButton homeBtn; //홈버튼
	JButton preBtn; //이전버튼
	JButton payBtn; //결제버튼
	
	//기타 설정
	private final int WIDTH = 900;
	private final int HEIGHT = 980;
	private final int HD_BT_HEIGHT = 80; //header, bottomPanel height
	private Color gray = new Color(53, 53, 53);
	private Color lightgray = new Color(171, 171, 171);
	private Color darkgray = new Color(45, 45, 45);
	private Color pink = new Color(245, 111, 152);
	//키패드 버튼 문자열 정보 배열
	private static String btnNum[] = { "7", "8", "9", 
								"4", "5", "6", 
								"1", "2", "3", 
								"초기화", "0", "←"};
	private String paySelected = ""; //선택된 결제방법
	private StringBuffer sb = new StringBuffer();
	
	public PayPanel(MainFrame f, MainPanel mp, OptionSelectPanel osp, OrderVO vo) {
		this.f = f;
		this.mp = mp;
		this.osp = osp;
		this.vo = vo;
		
		setBounds(0, 0, WIDTH, HEIGHT);
		
		addRootPanel();
		addHeaderPanel();
		addPaySelectPanel();
		addKeypadPanel();
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
		headerPanel.setBounds(0, 0, 900, 80);
		headerPanel.setBackground(gray);
		
		//홈버튼
		homeBtn = new JButton(new ImageIcon("images/ico_home.png"));
		homeBtn.setBounds(50, 0, 80, HD_BT_HEIGHT);
		homeBtn.setBackground(gray);
		homeBtn.setBorderPainted(false);
		homeBtn.setFocusPainted(false);
		homeBtn.addActionListener(new ChangePanelListener());
				
		//타이틀 라벨
		header = new JLabel("카드/현금 결제 선택");
		header.setBounds(0, 20, WIDTH, 40);
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		header.setForeground(Color.WHITE);
		
		headerPanel.add(homeBtn);
		headerPanel.add(header);
		rootPanel.add(headerPanel);
	}
	
	public void addPaySelectPanel() {
		selectPanel = new JPanel();
		selectPanel.setBounds(0, 80, WIDTH, 330);
		selectPanel.setBackground(gray);
		selectPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		selectPanel.setLayout(null);
		
		//라벨
		payTitle = new JLabel("결제방법을 선택해 주세요");
		payTitle.setBounds(320, 60, 500, 30);
		payTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		payTitle.setForeground(pink);
		
		//현금&카드 결제 버튼
		cardBtn = new JButton("카드", new ImageIcon("images/ico_card.png"));
		cashBtn = new JButton("현금", new ImageIcon("images/ico_cash.png"));
		
		cardBtn.setBounds(100, 130, 300, 150);
		cashBtn.setBounds(500, 130, 300, 150);
		cardBtn.setBackground(gray);   
		cashBtn.setBackground(gray);   
		cardBtn.setFocusPainted(false); 
		cashBtn.setFocusPainted(false); 
		
		cardBtn.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		cashBtn.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		cardBtn.setForeground(Color.LIGHT_GRAY);
		cashBtn.setForeground(Color.LIGHT_GRAY);
		cardBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
		cashBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
		cardBtn.setVerticalTextPosition(SwingConstants.CENTER);
		cashBtn.setVerticalTextPosition(SwingConstants.CENTER);
		
		//이벤트리스너 등록
		PaySelectListener listener = new PaySelectListener();
		cardBtn.addActionListener(listener);
		cashBtn.addActionListener(listener);
		
		selectPanel.add(payTitle);
		selectPanel.add(cardBtn);
		selectPanel.add(cashBtn);
		rootPanel.add(selectPanel);
	}
	
	public void addKeypadPanel() {
		keypadPanel = new JPanel();
		keypadPanel.setBounds(0, 410, WIDTH, 465);
		keypadPanel.setBackground(new Color(45, 45, 45));
		keypadPanel.setLayout(null);
		
		//타이틀 라벨
		padTitle = new JLabel();
		padTitle.setBounds(50, 150, 380, 30);
		padTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		padTitle.setForeground(pink);
		padTitle.setHorizontalAlignment(JLabel.CENTER);
		
		//출력 텍스트필드
		price = new JTextField("총 결제 금액 : " + Integer.toString(vo.getPrice()));
		printNum = new JTextField();
		
		price.setBounds(50, 60, 380, 60);
		printNum.setBounds(50, 200, 380, 60);
		price.setBackground(darkgray);
		printNum.setBackground(darkgray);

		price.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		printNum.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		price.setForeground(Color.BLACK);
		printNum.setForeground(pink);
		price.setHorizontalAlignment(JTextField.CENTER);
		printNum.setHorizontalAlignment(JTextField.CENTER);
		
		price.setEnabled(false);
		printNum.setEnabled(false);
		
		
		//키패드 버튼 패널 
		buttonPanel = new JPanel();
		buttonPanel.setBounds(500, 60, 330, 360);
		buttonPanel.setLayout(null);
		
		//키패드 버튼 배열 
		btnArr = new JButton[btnNum.length];
		
		int x = 0;
		int y = 0;
		for(int i=0; i<btnNum.length; i++) {
			if(i != 0 && i%3 == 0) {
				x = 0;
				y += 90;
			}
			btnArr[i] = new JButton(btnNum[i]);
			btnArr[i].setBounds(x, y, 110, 90);
			btnArr[i].setBackground(darkgray);
			btnArr[i].setFocusPainted(false);
			btnArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
			btnArr[i].setForeground(Color.LIGHT_GRAY);
			btnArr[i].addActionListener(new KeypadInputListener(i));
			
			buttonPanel.add(btnArr[i]);
					
			x += 110;
		}
		btnArr[9].setBackground(Color.GRAY);
		btnArr[11].setBackground(Color.GRAY);
			
		keypadPanel.add(padTitle);
		keypadPanel.add(price);
		keypadPanel.add(printNum);
		keypadPanel.add(buttonPanel);
		rootPanel.add(keypadPanel);
	}
	
	public void addBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 875, WIDTH, HD_BT_HEIGHT);
		bottomPanel.setBackground(lightgray);
		bottomPanel.setLayout(null);
		
		//이전, 결제하기 버튼 
		preBtn = new JButton("이전");
		payBtn = new JButton("결제하기");
		
		preBtn.setBounds(0, 0, WIDTH/2, HD_BT_HEIGHT);
		payBtn.setBounds(450, 0, WIDTH/2, HD_BT_HEIGHT);
		preBtn.setBackground(lightgray);
		payBtn.setBackground(pink);

		preBtn.setBorderPainted(false); 
		payBtn.setBorderPainted(false); 
		preBtn.setFocusPainted(false);
		payBtn.setFocusPainted(false); 
		
		preBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		payBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		payBtn.setForeground(Color.WHITE);
		
		//이벤트리스너 등록
		ChangePanelListener listener = new ChangePanelListener();
		preBtn.addActionListener(listener);
		payBtn.addActionListener(listener);
		
		bottomPanel.add(preBtn);
		bottomPanel.add(payBtn);
		rootPanel.add(bottomPanel);
	}
	
	/**
	 * 이벤트 리스너 :  카드, 현금 결제 버튼 처리 
	 * keypadPanel의 padTitle라벨 text가 변경되도록 하기
	 */
	private class PaySelectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == cardBtn) {
				paySelected = cardBtn.getText();
				//초기화
				sb.setLength(0);
				printNum.setText("");
				cashBtn.setBackground(gray);
				cashBtn.setForeground(Color.LIGHT_GRAY);
				//변경
				cardBtn.setBackground(pink);
				cardBtn.setForeground(gray);
				
				//padTitle의 text변경하기 
				keypadPanel.remove(padTitle);
				padTitle = new JLabel("카드번호를 입력해 주세요");
				padTitle.setBounds(50, 150, 380, 30);
				padTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				padTitle.setForeground(pink);
				padTitle.setHorizontalAlignment(JLabel.CENTER);
				
				keypadPanel.add(padTitle);
				keypadPanel.repaint();
			}
			else if(e.getSource() == cashBtn) {
				paySelected = cashBtn.getText();
				//초기화
				sb.setLength(0); 
				printNum.setText("");
				cardBtn.setBackground(gray);
				cardBtn.setForeground(Color.LIGHT_GRAY);
				//변경
				cashBtn.setBackground(pink);
				cashBtn.setForeground(gray);
				
				//padTitle의 text변경하기 
				keypadPanel.remove(padTitle);
				padTitle = new JLabel("지불할 금액을 입력해 주세요");
				padTitle.setBounds(50, 150, 380, 30);
				padTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				padTitle.setForeground(pink);
				padTitle.setHorizontalAlignment(JLabel.CENTER);
				
				keypadPanel.add(padTitle);
				keypadPanel.repaint();
			}
			
		}//end of actionPerformed method
		
	}//end of PaySelectListener class
	
	/**
	 * 이벤트 리스너 : 키패드 버튼 입력 처리
	 *
	 */
	private class KeypadInputListener implements ActionListener {
		private int index;

		public KeypadInputListener(int index) {
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			String result = "";
			
			//결제방법 선택 안했을 때 경고창 띄우기
			if(paySelected.equals("")) {
				JOptionPane.showMessageDialog(PayPanel.this, "결제 방법을 선택해 주세요.");
				return;
			}
			
			//초기화 버튼
			if(btn.getText().equals("초기화")) {
				sb.setLength(0); //sb 초기화
				printNum.setText("");
			}
			//지우기 버튼
			else if(btn.getText().equals("←")) {
				//입력된 게 없다면 return
				if(sb.length() == 0) return;
				
				sb.deleteCharAt(sb.length()-1); //sb 마지막 문자 지우기
				result = sb.substring(0);
				printNum.setText(result);
			}
			else {
				//카드 결제 : 4자리 마다 - 더해주기
				if(paySelected.equals("카드")) {
					//19자리까지만 입력 가능
					if(sb.length() == 19) return;
					
					int insert = 4;
					sb.append(btn.getText());
					do {
						if(sb.length() == insert) sb.append("-");
						insert += 5;
					} while(insert < 16);
					
				}
				//현금 결제 : , 더해주기
				else if(paySelected.equals("현금")) {
					//10만원대 까지만 입력 가능(7자리)
					if(sb.length() == 7) return;
					
					sb.append(btn.getText());
				}
				
				result = sb.substring(0);
				printNum.setText(result);
				
			}//end of if
			
		}//end of actionPerformed method
		
	}//end of KeypadInputListener
	
	/**
	 * 이벤트리스너 : 이전, 결제하기 버튼을 눌렀을 때 패널 변경
	 */
	private class ChangePanelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//첫화면 MainPanel로 이동
			if(e.getSource() == homeBtn) {
				MyUtil.changePanel(f, PayPanel.this, mp); 
			}
			//이전 화면 OptionSelectPanel로 이동
			else if(e.getSource() == preBtn) {
				MyUtil.changePanel(f, PayPanel.this, osp); 
			}
			//결제하기 : 유효성 검사 -> 결제 완료 -> 갱신, db파일 입출력 -> 메인 화면으로 돌아가기
			else if(e.getSource() == payBtn) {
				//결제방법 선택 안했을 때 경고창 띄우기
				if(paySelected.equals("")) {
					JOptionPane.showMessageDialog(PayPanel.this, "결제 방법을 선택해 주세요.");
					return;
				}
				
				//카드 : 입력한 카드번호가 19자리(-포함)가 아니면 경고창 띄우기
				if(paySelected.equals("카드")) {
					if(printNum.getText().length() != 19) {
						JOptionPane.showMessageDialog(PayPanel.this, "카드번호 16자리를 정확히 입력해 주세요.");
						return;
					}
				}
				//현금
				else if(paySelected.equals("현금")) {
					//입력한 현금 금액이 0일 때 경고창 띄우기
					if(printNum.getText().equals("")) {
						JOptionPane.showMessageDialog(PayPanel.this, "금액을 입력해 주세요.");
						return;
					}
					else {
						int inputPrice = Integer.parseInt(printNum.getText());
						
						//입력한 금액이 더 적을 경우 경고창 띄우기
						if(inputPrice < vo.getPrice()) {
							JOptionPane.showMessageDialog(PayPanel.this, "입력하신 금액이 더 적습니다.");
							return;
						}
						else {
							//입력금액이 더 클 경우 거스름돈 표시
							if(inputPrice > vo.getPrice()) {
								int rest = inputPrice - vo.getPrice();
								JOptionPane.showMessageDialog(PayPanel.this, "거스름돈 ["+rest+"원]");
							}
						}
					}
					
				}//end of if (payBtn)
				
				//결제 완료
				JOptionPane.showMessageDialog(PayPanel.this, "결제가 완료되었습니다.\n감사합니다.");

				//항목별 판매량 갱신
				String s = oc.splitList(vo);
				StringTokenizer st = new StringTokenizer(s, ",");
				while(st.hasMoreTokens()) {
					sc.updateStock(st.nextToken());
				}
				
				//파일 입력 : 불러오기
				List<OrderVO> voList = new ArrayList<>();
				voList = oc.loadIcecream();
				
				//현재 주문 객체 추가
				voList.add(vo);
				
				//파일 출력 : 저장, 덮어씌우기
				oc.saveIcecream(voList);
				
				//메인 화면으로 돌아가기
				MyUtil.changePanel(f, PayPanel.this, mp); 
				
			}//end of if
			
		}//end of actionPerformed method
		
	}//end of ChangePanelListener class
	
}
