package com.example.practice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.practice.constants.RtnCode;
import com.example.practice.entity.Atm;
import com.example.practice.repository.AtmDao;
import com.example.practice.service.ifs.AtmService;
import com.example.practice.vo.AtmRes;

//@Service
public class AtmServiceImplNote implements AtmService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private AtmDao atmDao;

//	private boolean inputCheck(Atm atm) {
//		if (atm == null) {
//			return false;
//		}
//		return true;
//	}

//	private boolean inputCheck(int amount) {
//		if (amount < 0) {
//			return false;
//		}
//		return true;
//	}
//
//	private boolean inputCheck(String ...strs) {
//		for (String item : strs) {
//			if (!StringUtils.hasText(item)) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	// ↓↓↓老師解法↓↓↓
	/** 
	 * 1.檢查account, pwd是否為null或是空字串或是空白字串<br>
	 * 2.檢查amount是否為負數
	 **/
	private RtnCode checkParams(String account, String pwd, int amount) {
		if(amount < 0) {
			return RtnCode.INPUT_INFO_ERROR;
		}
		return checkParams(account, pwd);
	}
	
	private RtnCode checkParams(String ...strs) {
		for (String item : strs) {
			if (!StringUtils.hasText(item)) {
				return RtnCode.INPUT_INFO_ERROR;
			}
		}
		return null; //return null → 表示參數格式確認無誤
	}
	// ↑↑↑老師解法↑↑↑
	
	private AtmRes logInCheck(String account, String pwd) {
		Optional<Atm> op = atmDao.findById(account);
		if (op.isEmpty()) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm atm = op.get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(account));
		}
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}
	
	private AtmRes checkParams2(String account, String pwd, int amount) {
		if(amount < 0) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(account));
		}
		return checkParams2(account, pwd);
	}
	
	private AtmRes checkParams2(String ...strs) {
		for (String item : strs) {
			if (!StringUtils.hasText(item)) {
				return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(strs[0]));
			}
		}
		return null; //return null → 表示參數格式確認無誤
	}

	private AtmRes logInCheck2(String account, String pwd, int amount) {
		AtmRes checkInput = checkParams2(account, pwd, amount);
		if(checkInput != null) {
			return checkInput;
		}
		Optional<Atm> op = atmDao.findById(account);
		if (op.isEmpty()) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm atm = op.get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(account));
		}
		return new AtmRes(RtnCode.SUCCESS, atm);
	}
	
	private void updateBalance(String account, int amount) {
		Atm target = atmDao.findById(account).get();
//		System.out.printf("帳號%s原餘額：%d\n", account, target.getAmount());
		target.setAmount(target.getAmount() + amount);
		atmDao.save(target);
//		System.out.printf("帳號%s新餘額：%d\n", account, target.getAmount());
	}

	// 新增資訊
	// (1)帳號及密碼非null、空字串、空白字串
	// (2)只能新增，且要對新增的資訊防呆
	@Override
	public AtmRes addInfo(String account, String pwd, int amount) {
		// 1.檢查格式
//老師解
		RtnCode res = checkParams(account, pwd, amount);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
//自己解	
//		if (!inputCheck(account, pwd) || !inputCheck(amount)) {
//			System.out.println("資料錯誤!");
//			return;
//		}
		
//最原始		
//		if (atm == null || !StringUtils.hasText(atm.getAccount()) || !StringUtils.hasText(atm.getPwd())) {
//			System.out.println("帳號或密碼格式錯誤!");
//			return;
//		}
		// 2.檢查重複帳號
		if(atmDao.existsById(account)) {
//			System.out.println("帳號已存在!");
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		// 3.★★★加密密碼★★★
		// (1)new跟import類別BCryptPasswordEncoder
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		// (2)取出輸入的明文密碼
//		String pwd = atm.getPwd();
		// (3)將明文密碼加密塞回原本的變數容器
//		pwd = encoder.encode(pwd);
		// (4)將加密後的密文set回atm
//		atm.setPwd(pwd);
		// ↓↓↓ 縮減程式碼 ↓↓↓
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //直接宣告在最上面就可以不用一直new!
//		atm.setPwd(encoder.encode(atm.getPwd()));
		// 4.儲存資料並列印
		atmDao.save(new Atm(account, encoder.encode(pwd), amount));
//		System.out.println("資料新增成功!");
//		System.out.printf("帳號：%s 餘額：%d\n", account, amount);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes getAmountByAccount(String account, String pwd) {
		// 1.檢查格式
//老師解
		RtnCode res = checkParams(account, pwd);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
		
//自己解
//		if (!inputCheck(account) || !inputCheck(pwd)) {
//			System.out.println("帳號或密碼格式錯誤!");
//			return;
//		}

//最原始
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
//			System.out.println("帳號或密碼格式錯誤!");
//			return;
//		}
		// 2.檢查帳號密碼是否吻合
		// 但無法使用帳號與密碼取得資訊，因為輸入的pwd是明文而存入資料庫的是密文
		AtmRes logIn = logInCheck(account, pwd);
		if (logIn.getRtnCode().getCode() != 200) {
//			System.out.println("帳號或密碼錯誤!");
			return logIn;
		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("帳號不存在!");
//			return;
//		}
//		Atm atm = op.get(); // 這裡取出的密碼為密文!
//		// 3.檢查密碼
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) { // encoder.matches的參數為「輸入的明文密碼」→「資料庫中的密文密碼」
//			System.out.println("密碼錯誤!");
//			return;
//		}
		// 4.印出餘額
//		System.out.printf("登入成功! 帳號%s的餘額為：%d\n", logIn.getAccount(), logIn.getAmount());
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, logIn.getAtm().getAmount()));
	}

	@Override
	public AtmRes updatePwd(String account, String pwdOld, String pwdNew) {
		// 1.檢查格式
		RtnCode res = checkParams(account, pwdOld, pwdNew);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwdOld) || !StringUtils.hasText(pwdNew)) {
//			System.out.println("帳號或密碼格式錯誤!");
//			return;
//		}
		// 2.檢查帳號密碼是否吻合
		AtmRes logIn = logInCheck(account, pwdOld);
		if (logIn.getRtnCode().getCode() != 200) {
//			System.out.println("帳號或密碼錯誤!");
			return logIn;
		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("帳號不存在!");
//			return;
//		}
//		Atm atm = op.get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwdOld, atm.getPwd())) {
//			System.out.println("密碼錯誤!");
//			return;
//		}
		// 3.更新密碼，且要加新密碼加密
//		Atm atm = atmDao.findById(account).get();
		Atm target = logIn.getAtm();
		target.setPwd(encoder.encode(pwdNew));
		// 4.將資料更新回DB
		atmDao.save(target);
//		System.out.printf("帳號%s密碼更新成功!\n", account);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes deposit(String account, String pwd, int amount) {
		// 1.檢查格式
//老師解
		RtnCode res = checkParams(account, pwd, amount);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
//自己解
//		inputCheck(account, pwd);
//		inputCheck(amount);
//最原始
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd) || amount < 0) {
//			System.out.println("輸入資訊錯誤!");
//			return;
//		}
		// 2.檢查帳號密碼是否吻合
		 AtmRes logIn = logInCheck(account, pwd);
		if(logIn.getRtnCode().getCode() != 200) {
//			System.out.println("帳號或密碼錯誤!");
			return logIn;
		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("帳號不存在!");
//			return;
//		}
//		Atm atm = op.get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) {
//			System.out.println("密碼錯誤!");
//			return;
//		}
		// 3.先印出餘額後存入金額(餘額加上存入金額)
//		Atm atm = atmDao.findById(account).get();
		updateBalance(account, amount);
//		System.out.printf("帳號%s存款前餘額：%d\n", account, logIn.getAmount());
//		logIn.setAmount(logIn.getAmount() + amount);
//		atmDao.save(logIn);
//		// 4.顯示結果
//		System.out.printf("帳號%s存款後餘額：%d\n", account, logIn.getAmount());
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, atmDao.findById(account).get().getAmount()));
	}

	@Override
	public AtmRes withdraw(String account, String pwd, int amount) {
		// 1.檢查格式
//老師解
//		RtnCode res = checkParams(account, pwd, amount);
//		if(res != null) {
//			return new AtmRes(res, new Atm(account));
//		}
//自己解
//		inputCheck(account, pwd);
//		inputCheck(amount);
		
//最原始
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd) || amount < 0) {
//			System.out.println("輸入資訊錯誤!");
//			return;
//		}
		// 2.檢查帳號密碼是否吻合
//		AtmRes logIn = logInCheck(account, pwd);
//		if(logIn.getRtnCode().getCode() != 200) {
////			System.out.println("帳號或密碼錯誤!");
//			return logIn;
//		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("帳號不存在!");
//			return;
//		}
//		Atm atm = op.get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) {
//			System.out.println("密碼錯誤!");
//			return;
//		}
		// 3.因為是提款，所以先確認餘額是否足夠
//		Atm atm = atmDao.findById(account).get();
		AtmRes goal = logInCheck2(account, pwd, amount);
		if(amount > goal.getAtm().getAmount()) {
			return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
		}
//		Atm target = logIn.getAtm();
//		if(amount > target.getAmount()) {
////			System.out.println("餘額不足!");
//			return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
//		}
		// 4.提款：先印出餘額後取出金額，然後再印出結果(餘額扣掉存入金額)
		updateBalance(account, -amount);
//		System.out.printf("帳號%s取款前餘額：%d\n", account, logIn.getAmount());
//		logIn.setAmount(logIn.getAmount() - amount);
//		atmDao.save(logIn);
//		System.out.printf("帳號%s取款後餘額：%d\n", account, logIn.getAmount());
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, atmDao.findById(account).get().getAmount()));
	}

	/*
	 * //確認輸入的字串是否為null、空字串或空白字串以及格式是否正確 private boolean checkLogInInfo(String
	 * account, String password) { //確認輸入的字串是否為null、空字串或空白字串
	 * if(!StringUtils.hasText(account) || !StringUtils.hasText(password)) { return
	 * false; } //確認格式是否正確 String accountPattern = "[\\w&&[^_]]{3,8}"; String
	 * passwordPattern = "[\\W+&&[\\w[^_]]]{8,16}";
	 * if(!account.matches(accountPattern) || !password.matches(passwordPattern)) {
	 * return false; } return true; }
	 * 
	 * //確認資料庫裡是否有相對應的資料並比對account和password是否正確 private boolean checkData(String
	 * account, String password){ //確認資料庫裡是否有相對應的資料 Optional<Atm> op =
	 * atmDao.findById(account); if(op.isEmpty()) { return false; }
	 * //比對account和password是否正確 Atm data = op.get();
	 * if(!data.getAccount().equals(account) ||
	 * !data.getPassword().equals(password)) { return false; } return true; }
	 * 
	 * //新增資訊 //(1)帳號及密碼非null、空字串、空白字串 //(2)只能新增，且要對新增的資訊防呆
	 * 
	 * @Override public void addInfo(String account, String password, int amount) {
	 * //確認字串是否有內容、格式是否正確 if(checkLogInInfo(account, password)==false) {
	 * System.out.println("帳號或密碼格式錯誤!"); return; } //確認金額是否為負 if(amount < 0) {
	 * System.out.println("金額錯誤!"); return; } //確認是否已存在相同account、確認無誤即新增資料
	 * Optional<Atm> op = atmDao.findById(account); if(op.isEmpty()) {
	 * atmDao.save(new Atm(account, password, amount));
	 * System.out.printf("資料新增成功! 帳號：%s 密碼：%s 餘額：%d", account, password, amount);
	 * return; } System.out.println("帳號已存在!"); }
	 * 
	 * //透過帳號取得餘額 //(1)檢查帳號及密碼 //(2)顯示帳號即餘額
	 * 
	 * @Override public void checkAmount(String account, String password) {
	 * //確認輸入的資料是否為null、空字串或空白字串 if(checkLogInInfo(account, password)==false) {
	 * System.out.println("帳號或密碼格式錯誤!"); return; }
	 * //確認資料庫裡是否有相對應的資料即比對account和password是否正確 if(checkData(account,
	 * password)==false) { System.out.println("帳號或密碼錯誤!"); return; } //比對正確印出資料
	 * System.out.printf("帳號%s查詢成功! 餘額為%d",
	 * atmDao.findById(account).get().getAccount(),
	 * atmDao.findById(account).get().getAmount()); }
	 * 
	 * //修改密碼 //(1)檢查帳號及密碼 //(2)資訊防呆
	 * 
	 * @Override public void passwordReset(String account, String password) {
	 * //確認是否有輸入資料 if(checkLogInInfo(account, password)==false) {
	 * System.out.println("帳號或密碼格式錯誤!"); return; } //確認帳號密碼是否一致
	 * if(checkData(account, password)==false) { System.out.println("帳號或密碼錯誤!");
	 * return; } //請使用者輸入要更新的密碼 Scanner scan = new Scanner(System.in); String input
	 * = scan.next(); String passwordPattern = ""; for(;;) {
	 * if(!input.matches(passwordPattern)) {
	 * System.out.println("密碼格式錯誤! 請重新輸入新密碼!"); input = scan.next(); continue; }
	 * //更新密碼、顯示更新成功 atmDao.save(new
	 * Atm(account,input,atmDao.findById(account).get().getAmount()));
	 * System.out.println("密碼更新成功!"); }
	 */
}
