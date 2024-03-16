package com.example.practice.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
//import com.fasterxml.jackson.annotation.JsonProperty;

//�]���o�䪺�ݩʫܭ�n����Entity��Atm�@�Ҥ@�ˡA�ҥH���]�i�H�����hextends�~��Atm���O�N�n
//�p�G�o�˰��A�N�i�H���ݭn�g�ݩʡA�u�n�s�WSuperclass�غc��k�Y�i�A�o�˥L�|�ۤv�I�s�����O���غc��k�X��
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
	
    //�H�U���annotation�O���~����J��Json�榡key�W�٥i�H������Req�����ݩ�pwd
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
