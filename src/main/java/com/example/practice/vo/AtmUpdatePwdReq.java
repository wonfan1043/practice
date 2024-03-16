package com.example.practice.vo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AtmUpdatePwdReq {
	
	private String account;
	
	@JsonAlias({"old password", "old pwd"})
	private String pwdOld;
	
	@JsonAlias({"new password", "new pwd"})
	private String pwdNew;

	public AtmUpdatePwdReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AtmUpdatePwdReq(String account, String pwdOld, String pwdNew) {
		super();
		this.account = account;
		this.pwdOld = pwdOld;
		this.pwdNew = pwdNew;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwdOld() {
		return pwdOld;
	}

	public void setPwdOld(String pwdOld) {
		this.pwdOld = pwdOld;
	}

	public String getPwdNew() {
		return pwdNew;
	}

	public void setPwdNew(String pwdNew) {
		this.pwdNew = pwdNew;
	}

}
