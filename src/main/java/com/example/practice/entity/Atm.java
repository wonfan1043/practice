package com.example.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "atm")
public class Atm {
	
	@Id
	@Column(name = "account")
	private String account;
	
	//落點掃描：根據寫法和邏輯判斷會有甚麼錯誤，password因為是敏感字眼所以習慣寫pwd比較好
	@Column(name = "password")
	private String pwd;
	
	@Column(name = "amount")
	private int amount;

	public Atm() {
		super();
	}

	public Atm(String account, String password, int amount) {
		super();
		this.account = account;
		this.pwd = password;
		this.amount = amount;
	}

	public Atm(String account) {
		super();
		this.account = account;
	}

	public Atm(String account, int amount) {
		super();
		this.account = account;
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
