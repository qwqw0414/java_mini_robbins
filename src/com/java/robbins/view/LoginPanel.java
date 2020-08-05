package com.java.robbins.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.java.robbins.view.common.MyUtil;

public class LoginPanel extends JPanel {
	
	//패널변경
	private MainFrame f;
	private MainPanel mp; //이전화면
	private AdminPanel ap; //다음화면
	
	//컨테이너 & 컴포넌트
	private JLabel title;
	private JPasswordField txtPassword; //비밀번호 입력
	private JButton btncheck; //확인버튼
	
	public LoginPanel(MainFrame f, MainPanel mp) {
		this.f = f;
		this.mp = mp;
		
		ap = new AdminPanel(f, LoginPanel.this); //다음화면 객체 생성
		
		setLayout(null);
		setBounds(0, 0, 600, 400);
		
		//타이틀 라벨
		title = new JLabel("비밀번호를 입력하세요.");
		title.setBounds(0, 100, 600, 30);
		title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		//비밀번호 입력란 
		txtPassword = new JPasswordField(8);
		txtPassword.setBounds(150, 180, 300, 40);
		
		//확인버튼
		btncheck = new JButton("확인");
		btncheck.setBounds(270, 250, 70, 40);
		btncheck.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		
		//이벤트 리스너 등록
		ChangePanelListener listener = new ChangePanelListener();
		btncheck.addActionListener(listener);
		txtPassword.addActionListener(listener);

		add(title);
		add(txtPassword);
		add(btncheck);
	}
	
	/**
	 * 이벤트 리스너 : 패널 전환
	 * 확인 버튼, 엔터 누르면 비밀번호 유효성 검사 하고 다음 패널 AdminPanel로 이동
	 */
	private class ChangePanelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//JPasswordField의 char값을 문자열로 변환
			char[] pwCh = txtPassword.getPassword();
			String pw = new String(pwCh);
			
			if(e.getSource()==btncheck || e.getSource()==txtPassword) {
				//패스워드가 일치할 때만 AdminPanel로 이동
				if(pw.equals(f.getPASSWORD())) {
					//화면 가운데에 위치시키기 
					Toolkit tk = Toolkit.getDefaultToolkit();
					Dimension screenSize = tk.getScreenSize();
					int width = 900;
					int height = 630;
					int x = screenSize.width/2 - width/2;
					int y = screenSize.height/2 - height/2;
					
					f.setTitle("관리자 페이지");
					f.setBounds(x, y, width, height);
					MyUtil.changePanel(f, LoginPanel.this, ap);
					txtPassword.setText("");
				}
				else {
					txtPassword.setText("");
					JOptionPane.showMessageDialog(LoginPanel.this, "잘못입력하셨습니다. 다시 입력해주세요.");
					return;
				}
				
			}//end of if
			
		}//end of actionPerformed method
		
	}//end of ChangePanelListener class
	
}
