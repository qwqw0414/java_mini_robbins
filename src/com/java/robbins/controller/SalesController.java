package com.java.robbins.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.java.robbins.model.vo.SalesVO;

public class SalesController {
	private final String DATALINK = "data/salesDB.ser";

	/**
	 * 품목별 판매량 갱신
	 */
	public void updateStock(String iceName) {
		List<SalesVO> list = loadStock();
		SalesVO stock = new SalesVO();
		stock.setIceName(iceName);
		int index = 0;
		
		// 재고 값 비교
		if (!list.contains(stock)) {
			stock.setIceStock(1);
			list.add(stock);
			System.out.println(list);
		} else {
			index = list.indexOf(stock);
			int num = list.get(index).getIceStock() + 1;
			stock.setIceStock(num);
			list.set(index, stock);
			System.out.println(list);
		}
		
		saveStock(list);
	}

	/**
	 * 판매량 저장
	 */
	public void saveStock(List<SalesVO> list) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(DATALINK)))) {
			oos.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 판매량 불러오기
	 */
	public List<SalesVO> loadStock() {
		List<SalesVO> stockList = null;

		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(DATALINK)))) {
			stockList = (List<SalesVO>)ois.readObject();
		} catch(EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(stockList == null) {
			stockList = new ArrayList<>();
		}

		return stockList;
	}
}
