package com.example.practice.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
//import com.fasterxml.jackson.annotation.JsonProperty;

//因為這邊的屬性很剛好的跟Entity的Atm一模一樣，所以其實也可以直接去extends繼承Atm類別就好
//如果這樣做，就可以不需要寫屬性，只要新增Superclass建構方法即可，這樣他會自己呼叫父類別的建構方法出來
// public class AtmReq extends Atm {
//		public Atm() {
//			super();
//		}
//
//		public Atm(String account, String password, int amount) {
//			super();
//			this.account = account;
//			this.pwd = password;
//			this.amount = amount;
//		}
//
//		public Atm(String account) {
//			super();
//			this.account = account;
//		}
// }

public class AtmReq {
	
	private String account;
	
    //以下兩個annotation是讓外部輸入的Json格式key名稱可以對應到Req中的屬性pwd
//	@JsonProperty("passowrd") 
	@JsonAlias({"password", "pwd"})
	private String pwd;
	
	private int amount;

	public AtmReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AtmReq(String account, String pwd, int amount) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	} 

}
