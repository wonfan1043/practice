package com.example.practice.service.ifs;

import com.example.practice.vo.AtmRes;

@SuppressWarnings("unused")
public interface AtmService {
	
	//�s�W��T
	//(1)�b���αK�X�Dnull�B�Ŧr��B�ťզr��
	//(2)�u��s�W�A�B�n��s�W����T���b
	public AtmRes addInfo(String account, String pwd, int amount);
//	public void addInfo(String account, String password, int amount);
	
	//�z�L�b�����o�l�B
	//(1)�ˬd�b���αK�X
	//(2)��ܱb���Y�l�B
	public AtmRes getAmountByAccount(String account, String pwd);
	
	//�ק�K�X
	//(1)�ˬd�b���αK�X
	//(2)��T���b
	public AtmRes updatePwd(String account, String pwdOld, String pwdNew);	
	
	//�s��
	//(1)�ˬd�b���M�K�X
	//(2)��T�n���b
	//(3)��ܱb���B�s�ګ᪺�l�B
	public AtmRes deposit(String account, String pwd, int amount);
	
	//����
	//(1)�ˬd�b���M�K�X
	//(2)��T�n���b
	//(3)��ܱb���B�s�ګ᪺�l�B
	public AtmRes withdraw(String account, String pwd, int amount);
}
