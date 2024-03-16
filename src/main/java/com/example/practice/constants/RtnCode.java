package com.example.practice.constants;

public enum RtnCode {

	SUCCESS(200, "Success!!!"), //
	ACCOUNT_EXISTS(400, "Account Exists!!!"), //
	INPUT_INFO_ERROR(400, "Input info error!!!"), //
	ACCOUNT_NOT_FOUND(400, "Account is not found!!!"), //
	INSUFFICIENT_BALANCE(400, "Insufficient balance!!!"),//
	PASSWORD_ERROR(400, "Password Error!!!");

	private int code;

	private String message;

	// 使用下面的建構方法來操作上面的RTN Code
	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
