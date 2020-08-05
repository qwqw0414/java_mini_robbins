package com.java.robbins.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.java.robbins.controller.OrderController;
import com.java.robbins.controller.SalesController;
import com.java.robbins.model.vo.OrderVO;
import com.java.robbins.model.vo.SalesVO;
import com.java.robbins.view.common.MyUtil;


/**
 * 참고
 * http://www.trossystems.com/download/tros-kiosk-solution.pdf
 * https://www.slideshare.net/starrynight1/project-java-swinghamburger180119-96655715
 *
 */

public class AdminPanel extends JPanel {
	private SalesController sc = new SalesController();
	private OrderController oc = new OrderController();
	private OrderVO vo;
	
	//패널 변경
	private MainFrame f;
	private LoginPanel lp; //이전 화면
	private MainPanel mp; //다음 화면
	
	//컨테이너 & 컴포넌트
	private JPanel rootPanel;
	private JPanel headerPanel;
	private JPanel contentsPanel;
	private JLabel header; //headerPanel title
	private JTabbedPane tab;
	private JPanel p1; //tab의 1번 패널
	private JPanel p2; //tab의 2번 패널
	private JLabel total; //p1 총 매출
	private JTable table1; //p1 테이블
	private JTable table2; //p2 테이블
	private JScrollPane scr1; //table1의 스크롤페인
	private JScrollPane scr2; //table2의 스크롤페인
	private TableColumnModel tcm1; //table1의 컬럼모델
	private TableColumnModel tcm2; //table2의 컬럼모델
	
	//이벤트 컴포넌트
	JButton homeBtn; //홈버튼
	
	//기타 설정
	private final int WIDTH = 900;
	private final int HEIGHT = 630;
	private final int HD_BT_HEIGHT = 80; //header, bottomPanel height
	Color gray = new Color(53, 53, 53);
	Color pink = new Color(245, 111, 152);

	public AdminPanel(MainFrame f, LoginPanel lp) {
		this.f = f;
		this.mp = mp;
		this.lp = lp;
		
//		mp = new MainPanel(f, AdminPanel.this);
		
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
		
		//홈버튼
		homeBtn = new JButton(new ImageIcon("images/ico_home.png"));
		homeBtn.setBounds(50, 0, 80, HD_BT_HEIGHT);
		homeBtn.setBackground(gray);
		homeBtn.setBorderPainted(false);
		homeBtn.setFocusPainted(false);
		homeBtn.addActionListener(new ChangePanelListener());
		
		//타이틀
		header = new JLabel("Java Robbins");
		header.setBounds(300, 20, 300, 40);
		header.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		header.setForeground(Color.WHITE);
		
		headerPanel.add(homeBtn);
		headerPanel.add(header);
		rootPanel.add(headerPanel);
	}
	
	public void addContentsPanel() {
		contentsPanel = new JPanel();
		contentsPanel.setBounds(0, 80, WIDTH, 530);
		contentsPanel.setBackground(gray);
		contentsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentsPanel.setLayout(null);
		
		//패널 탭
		tab = new JTabbedPane();
		tab.setBounds(50, 10, 800, 500);
		
		//1번 패널
		p1 = new JPanel();
		p1.setBounds(0, 0, 800, 500);
		p1.setBackground(gray);
		p1.setLayout(null);
		
		//orderDB파일 읽어오기
		List<OrderVO> voList = new ArrayList<>();
		voList = oc.loadIcecream();
		
		//리스트를 배열로 변환
		OrderVO[] voArr = voList.toArray(new OrderVO[voList.size()]);
		Object[] columns = {"주문번호", "사이즈", "선택 아이스크림", "가격"};
		Object[][] rowData = new Object[voArr.length][4];
		
		int cnt = 0;
		int sum = 0;
		for(OrderVO vo: voArr) {
			rowData[cnt][0] = cnt;
			rowData[cnt][1] = vo.getSize();
			rowData[cnt][2] = vo.getSelectedList();
			rowData[cnt][3] = vo.getPrice();
			sum += vo.getPrice();
			cnt++;
		}
		
		//총 매출
		total = new JLabel("총 매출 : "+sum+"원");
		total.setBounds(50, 30, 500, 40);
		total.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		total.setForeground(pink);
		
		//테이블1
		table1 = new JTable(rowData, columns);
		table1.setEnabled(false); //에디터 비활성화
		scr1 = new JScrollPane(table1);
		scr1.setBounds(30, 100, 740, 350);
		
		//테이블1 가운데 정렬
		DefaultTableCellRenderer alignCenter = new DefaultTableCellRenderer();
		alignCenter.setHorizontalAlignment(JLabel.CENTER);
		tcm1 = table1.getColumnModel();
		
		for(int i=0; i<tcm1.getColumnCount(); i++) {
			tcm1.getColumn(i).setCellRenderer(alignCenter);
		}
		
		//2번 패널
		p2 = new JPanel();
		p2.setBounds(0, 0, 800, 500);
		p2.setBackground(gray);
		p2.setLayout(null);
		
		//salesDB파일 읽어오기
		List<SalesVO> stockList = new ArrayList<>();
		stockList = sc.loadStock();
		
		SalesVO[] voArr2 = stockList.toArray(new SalesVO[stockList.size()]);
		Object[] columns2 = {"아이스크림 품목", "판매량"};
		Object[][] rowData2 = new Object[voArr2.length][2];
		cnt = 0;
		for(SalesVO s: voArr2) {
			rowData2[cnt][0] = s.getIceName();
			rowData2[cnt][1] = s.getIceStock();
			cnt++;
		}
		
		//테이블2
		table2 = new JTable(rowData2, columns2);
		table2.setAutoCreateRowSorter(true); 
		table2.setEnabled(false);
		scr2 = new JScrollPane(table2);
		scr2.setBounds(30, 30, 740, 420);
		
		//테이블2 가운데 정렬
		tcm2 = table2.getColumnModel();
		for(int i=0; i<tcm2.getColumnCount(); i++) {
			tcm2.getColumn(i).setCellRenderer(alignCenter);
		}
		
		p1.add(scr1);
		p1.add(total);
		p2.add(scr2);
		tab.addTab("주문목록", p1);
		tab.addTab("품목별 판매량", p2);
		contentsPanel.add(tab);
		rootPanel.add(contentsPanel);
	}
	
	/**
	 * 이벤트리스너 : 홈버튼 눌렀을 때 패널 변경
	 */
	private class ChangePanelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//메인패널로 이동
			if(e.getSource() == homeBtn) {
				//화면 가운데에 위치시키기 
				Toolkit tk = Toolkit.getDefaultToolkit();
				Dimension screenSize = tk.getScreenSize();
				int width = 900;
				int x = screenSize.width/2 - width/2;
				
				f.setBounds(x, 30, 900, 1000);
				MyUtil.changePanel(f, AdminPanel.this, new MainPanel(f)); 
			}
		}
	}
	
}
