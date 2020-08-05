package com.java.robbins.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.java.robbins.controller.OrderController;
import com.java.robbins.model.vo.OrderVO;
import com.java.robbins.view.common.MyUtil;

public class IceSelectPanel extends JPanel {
	private OrderController oc = new OrderController();
	private OrderVO vo;
	
	//패널 변경
	private MainFrame f;
	private MainPanel mp; //처음 화면
	private SizeSelectPanel ssp; //이전 화면
	private OptionSelectPanel osp; //다음 화면
	
	//컨테이너 & 컴포넌트
	private JPanel rootPanel; 
	private JPanel headerPanel;
	private JPanel selectPanel;
	private JPanel printPanel;
	private JPanel scoopPanel;
	private JPanel bottomPanel;
	private JLabel header; //headerPanel title
	private JLabel flavor; //printPanel title 
	private JLabel change;
	
	//사용자 입력 컴포넌트
	private JButton[] bArr; //selectPanel 아이스크림 종류 
	private JLabel[] scoopArr; //scoopPanel 선택된 아이스크림
	
	//이벤트 컴포넌트
	private JButton homeBtn; //headerPanel 홈버튼
	private JButton preBtn;  //bottomPanel 이전버튼
	private JButton nextBtn; //bottomPanel 다음버튼
	
	//기타 설정
	private final int WIDTH = 900;
	private final int HEIGHT = 980;
	private final int HD_BT_HEIGHT = 80; //header, bottomPanel height
	private Color gray = new Color(53, 53, 53);
	private Color lightgray = new Color(171, 171, 171);
	private Color pink = new Color(245, 111, 152);
	//종류별 버튼 문자열 정보 배열
	private static String[][] sArr = {{"31요거트", "images/31요거트.png"},
									  {"그린티", "images/그린티.png"},
									  {"뉴욕 치즈 케이크", "images/뉴욕 치즈 케이크.png"},
									  {"레인보우 샤베트", "images/레인보우 샤베트.png"},
									  {"민트 초콜릿 칩", "images/민트 초콜릿 칩.png"},
									  {"바람과 함께 사라지다", "images/바람과 함께 사라지다.png"},
									  {"사랑에 빠진 딸기", "images/사랑에 빠진 딸기.png"},
									  {"슈팅스타", "images/슈팅스타.png"},
									  {"심슨에 반하나", "images/심슨에 반하나.png"},
									  {"쌀떡궁합", "images/쌀떡궁합.png"},
									  {"아몬드 봉봉", "images/아몬드 봉봉.png"},
									  {"엄마는 외계인", "images/엄마는 외계인.png"},
									  {"이상한나라의 솜사탕", "images/이상한 나라의 솜사탕.png"},
									  {"초코나무 숲", "images/초코나무 숲.png"},
									  {"쿠키 앤 크림", "images/쿠키 앤 크림.png"},
									  {"피스타치오 아몬드", "images/피스타치오 아몬드.png"}};
	private int eaCnt = 0; //아이스크림 선택할 수 있는 개수
	private int clickCnt = 0; //버튼 클릭한 횟수
	private int scoopCnt = 0; //
	
	public IceSelectPanel(MainFrame f, MainPanel mp, SizeSelectPanel ssp, OrderVO vo) {
		this.f = f;
		this.mp = mp;
		this.ssp = ssp;
		this.vo = vo;
		
		eaCnt = vo.getIceEa(); //이벤트 리스너 선택개수 초기화
		
		setBounds(0, 0, WIDTH, HEIGHT);

		addRootPanel();
		addHeaderPanel();
		addIceSelectPanel();
		addSelectedPrintPanel();
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
		header = new JLabel(""+vo.getSize());
		header.setBounds(0, 20, WIDTH, 40);
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		header.setForeground(Color.WHITE);
		
		headerPanel.add(homeBtn);
		headerPanel.add(header);
		rootPanel.add(headerPanel);
	}
	
	public void addIceSelectPanel() {
		selectPanel = new JPanel();
		selectPanel.setBounds(0, 80, WIDTH, 600);
		selectPanel.setBackground(gray);
		selectPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		selectPanel.setLayout(null);
	
		//아이스크림 종류별 버튼 배열
		bArr = new JButton[sArr.length];
		
		int x = 100;
		int y = 20;
		for(int i=0; i<bArr.length; i++) {
			if(i != 0 && i%4 == 0) {
				x = 100;
				y += 140;
			}
			bArr[i] = new JButton(sArr[i][0], new ImageIcon(sArr[i][1]));
			bArr[i].setBounds(x, y, 180, 140);
			bArr[i].setBackground(gray);
			bArr[i].setBorderPainted(false);
			bArr[i].setFocusPainted(false);
			bArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 15));
			bArr[i].setForeground(Color.LIGHT_GRAY);
			bArr[i].setHorizontalTextPosition(SwingConstants.CENTER);
			bArr[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			bArr[i].addActionListener(new SortSelectListener(i));
			
			selectPanel.add(bArr[i]);
			
			x += 175;
		}
		
		rootPanel.add(selectPanel);
	}
	
	public void addSelectedPrintPanel() {
		printPanel = new JPanel();
		printPanel.setBounds(0, 680, WIDTH, 195);
		printPanel.setBackground(new Color(45, 45, 45));
		printPanel.setLayout(null);
		
		//라벨
		flavor = new JLabel(oc.printFlavor(vo.getIceEa())+" 플레이버를 선택해주세요. ("
							+clickCnt+"/"+vo.getIceEa()+")");
		flavor.setBounds(50, 20, 800, 30);
		flavor.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		flavor.setForeground(pink);
		
		//아이스크림 선택 패널 
		scoopPanel = new JPanel();
		scoopPanel.setBounds(0, 70, WIDTH, 95);
		scoopPanel.setBackground(new Color(45, 45, 45));
		scoopPanel.setLayout(null);
		
		//선택된 아이스크림 배열
		scoopArr = new JLabel[vo.getIceEa()];
		
		int x = 20;
		for(int i=0; i<scoopArr.length; i++) {
			scoopArr[i] = new JLabel(new ImageIcon("images/ico_scoop.png"));
			scoopArr[i].setBounds(x, 5, 175, 95);
			
			scoopPanel.add(scoopArr[i]);
			
			x += 135;
		}		
		
		printPanel.add(flavor);
		printPanel.add(scoopPanel);
		rootPanel.add(printPanel);
	}
	
	public void addBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 875, WIDTH, HD_BT_HEIGHT);
		bottomPanel.setBackground(lightgray);
		bottomPanel.setLayout(null);
		
		//이전, 다음버튼 
		preBtn = new JButton("이전");
		nextBtn = new JButton("다음");
		
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
	 * 이벤트리스너 : selectPanel의 아이스크림 종류별 선택 버튼 처리
	 * vo.iceEa에 맞게 클릭 수를 제한하고,
	 * setter를 이용해 전달받은 vo객체에 정보를 추가하기
	 */
	private class SortSelectListener implements ActionListener{
		int index = 0;
		
		public SortSelectListener(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
//			if(e.getSource() == bArr[index]) {
				//선택할 수 있는 개수가 0일 때 버튼 비활성화, 경고 팝업창 띄우기
				if(eaCnt == 0) {
					for(JButton btn: bArr) {
						btn.setEnabled(false);
					}
					JOptionPane.showMessageDialog(IceSelectPanel.this, "더 이상 선택하실 수 없습니다.");
				}
				else {
					vo.setSelectedList(bArr[index].getText()); //선택한 정보 객체에 담기
					
					eaCnt--;
					clickCnt++;
					
					//flavor라벨의 text변경하기
					printPanel.remove(flavor); //부모 컴포넌트에서 자식컴포넌트 제거 후, 새로 생성. 
					String flavorText = oc.printFlavor(vo.getIceEa())+" 플레이버를 선택해주세요. ("+clickCnt+"/"+vo.getIceEa()+")";
					flavor = new JLabel(flavorText);
					flavor.setBounds(50, 20, 800, 30);
					flavor.setFont(new Font("맑은 고딕", Font.BOLD, 18));
					flavor.setForeground(pink);
					
					printPanel.add(flavor);
					printPanel.repaint();
					
					//printPanel의 스쿱라벨 이미지를 선택한 아이스크림 이미지로 변경
					Image img = new ImageIcon(sArr[index][1]).getImage().getScaledInstance(175, 95, 0);
					scoopArr[scoopCnt++].setIcon(new ImageIcon(img));
				}
				
//			}//end of if
			
		}//end of actionPerformed method
		
	}//end of SortSelectListener class
	
	/**
	 * 이벤트리스너 : 이전, 다음 버튼을 눌렀을 때 패널 변경
	 */
	private class ChangePanelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//첫화면 MainPanel로 이동
			if(e.getSource() == homeBtn) {
				MyUtil.changePanel(f, IceSelectPanel.this, mp); 
			}
			//이전 화면 SizeSelectPanel 로 이동
			else if(e.getSource() == preBtn) {
				MyUtil.changePanel(f, IceSelectPanel.this, ssp); 
			}
			//다음 화면 OptionSelectPanel 로 이동
			else if(e.getSource() == nextBtn) {
				//개수만큼 다 선택하지 않은 경우 경고창 띄우기
				if(!(clickCnt > eaCnt)) {
					JOptionPane.showMessageDialog(IceSelectPanel.this, "플레이버를 전부 선택해 주세요.");
					return;
				}
				osp = new OptionSelectPanel(f, mp, IceSelectPanel.this, vo); //다음 화면 객체 생성
				MyUtil.changePanel(f, IceSelectPanel.this, osp); 
			}
			
		}//end of actionPerformed method
		
	}//end of ChangePanelListener class
	
}
