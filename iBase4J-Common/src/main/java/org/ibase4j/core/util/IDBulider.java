package org.ibase4j.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class IDBulider {
	
	private static IDBulider instance = null;
	
	private Lock lock = new ReentrantLock();

	private IDBulider() {
	}

	static {
		instance = new IDBulider();
	}
	/**
	 * 单实例模式
	 * @return
	 */
	public static IDBulider getInstance(){
		return instance;
	}

	/**
	 * 获取自增id
	 * 
	 * @return
	 */
	public static String generator() {
		StringBuffer idStr = new StringBuffer();
		Date now = new Date();
		String date = DateUtil.getTime();
		idStr.append(date);
		return idStr.toString();
	}

	/**
	 * 功能：获取0-9内的随机数
	 * 
	 * @return
	 */
	public int getRandomTime() {
		Random rand = new Random();
		return rand.nextInt(10);
	}

	/**
	 * 功能：生成押品编号
	 * 
	 * @return
	 */
	public String generatorIouCode() {
		try {
			if (lock.tryLock(getRandomTime(), TimeUnit.MILLISECONDS)) {
				Date iouTime = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
				String iouCode = "MJ" + ft.format(iouTime);
				return iouCode;
			} else {
				return generatorIouCode();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		} finally {
			// 释放锁
			lock.unlock();
		}
		return null;

	}

}
