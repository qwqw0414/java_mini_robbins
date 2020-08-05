package com.java.robbins.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JLabel;

import com.java.robbins.model.vo.OrderVO;

public class OrderController {
	
	private final String DATALINK = "data/orderDB.ser";
	
	/**
	 * SizeSelectPanel에서 선택한 사이즈에 따라 
	 * 사이즈명, 사이즈별 아이스크림 선택개수, 가격 정보를 파라미터로 갖는 생성자로 
	 * vo객체 생성하기 
	 */
	public OrderVO selectSize(String size) {
		int iceEa = inputIceEa(size);
		int price = inputPrice(size);
		OrderVO vo = new OrderVO(size, iceEa, price);
		
		return vo;
	}
	
	/**
	 * 사이즈별 아이스크림 선택개수 구하기 
	 */
	public int inputIceEa(String size) {
		int iceEa = 0;
		if(size.equals("싱글레귤러") || size.equals("싱글킹"))
			iceEa = 1;
		else if(size.equals("더블주니어") || size.equals("더블레귤러"))
			iceEa = 2;
		else if(size.equals("파인트"))
			iceEa = 3;
		else if(size.equals("쿼터"))
			iceEa = 4;
		else if(size.equals("패밀리"))
			iceEa = 5;
		else if(size.equals("하프갤런"))		
			iceEa = 6;
		
		return iceEa;
	}
	
	
	/**
	 * 사이즈별 아이스크림 가격 구하기
	 */
	public int inputPrice(String size) {
		int price = 0;
		if(size.equals("싱글레귤러"))
			price = 3200;
		else if(size.equals("싱글킹"))
			price = 4000;
		else if(size.equals("더블주니어"))
			price = 4300;
		else if(size.equals("더블레귤러"))
			price = 6200;
		else if(size.equals("파인트"))
			price = 8200;
		else if(size.equals("쿼터"))
			price = 15500;
		else if(size.equals("패밀리"))
			price = 22500;
		else if(size.equals("하프갤런"))
			price = 26500;
		
		return price;
	}
	
	/**
	 * IceSelectPanel - printPanel에 아이스크림 개수 별 출력 구분하기
	 * 라벨 : "한가지 플레이버를 선택해주세요 (0/1)"
	 */
	public String printFlavor(int iceEa) {
		String result = "";
		if(iceEa == 1) 
			result = "한 가지";
		else if(iceEa == 2)
			result = "두 가지";
		else if(iceEa == 3)
			result = "세 가지";
		else if(iceEa == 4)
			result = "네 가지";
		else if(iceEa == 5)
			result = "다섯 가지";
		else if(iceEa == 6)
			result = "여섯 가지";
		
		return result;
	}
	
	/**
	 * vo객체의 selectedList -> 배열 -> 문자열 -> "[" , "]" 제거하기 
	 */
	public String splitList(OrderVO vo) {
		String[] arr = vo.getSelectedList().toArray(new String[vo.getSelectedList().size()]);
		
		String s1 = Arrays.toString(arr);
		String result = s1.replace("[", "").replace("]", "").replace(" ", ""); 
		
		return result;
	}
	
	/**
	 * 파일 읽기(데이터 로드)
	 */
	public List<OrderVO> loadIcecream() {
		List<OrderVO> voList = null;
		
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(DATALINK)));) {
			voList = (List<OrderVO>)ois.readObject();
		} catch(EOFException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(voList == null) {
			voList = new ArrayList<>();
		}
		
		return voList;
	}
	
	/**
	 * 파일 출력(데이터 저장)
	 * loadIcecream메소드로 파일을 먼저 읽어와서 vo배열에 담고, 현재 주문 객체도 vo배열에 추가한 다음
	 * 매개인자로 배열 전달해서 기존 파일에 저장, 덮어씌우기
	 */
	public boolean saveIcecream(List<OrderVO> voList) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(DATALINK)));){
			oos.writeObject(voList);
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
