package com.example.practice.service.ifs;

import com.example.practice.vo.AtmRes;

@SuppressWarnings("unused")
public interface AtmService {
	
	//新增資訊
	//(1)帳號及密碼非null、空字串、空白字串
	//(2)只能新增，且要對新增的資訊防呆
	public AtmRes addInfo(String account, String pwd, int amount);
//	public void addInfo(String account, String password, int amount);
	
	//透過帳號取得餘額
	//(1)檢查帳號及密碼
	//(2)顯示帳號即餘額
	public AtmRes getAmountByAccount(String account, String pwd);
	
	//修改密碼
	//(1)檢查帳號及密碼
	//(2)資訊防呆
	public AtmRes updatePwd(String account, String pwdOld, String pwdNew);	
	
	//存款
	//(1)檢查帳號和密碼
	//(2)資訊要防呆
	//(3)顯示帳號、存款後的餘額
	public AtmRes deposit(String account, String pwd, int amount);
	
	//提款
	//(1)檢查帳號和密碼
	//(2)資訊要防呆
	//(3)顯示帳號、存款後的餘額
	public AtmRes withdraw(String account, String pwd, int amount);
}
