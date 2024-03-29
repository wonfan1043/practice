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
public class AtmServiceImpl2 implements AtmService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private AtmDao atmDao;

	@Override
	public AtmRes addInfo(String account, String pwd, int amount) {
		AtmRes checkRes = checkParams2(account, pwd, amount);//此方法把上述的檢查隱藏起來
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		if(atmDao.existsById(account)) {
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		// 新建出的 Atm 裡面的 pwd 要加密後再存進DB
		atmDao.save(new Atm(account, encoder.encode(pwd), amount));
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes getAmountByAccount(String account, String pwd) {
		AtmRes checkRes = check(account, pwd);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
	}

//	@Override
	public AtmRes updatePassword(String account, String oldPwd, String newPwd) {
		AtmRes checkRes = check(account, oldPwd, newPwd);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		// 3. 更換新密碼，要將新密碼加密
		atm.setPwd(encoder.encode(newPwd));
		// 4. 將資料更新回 DB
		atmDao.save(atm);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes deposit(String account, String pwd, int amount) {
		return operation(account, pwd, amount, false);
	}

	@Override
	public AtmRes withdraw(String account, String pwd, int amount) {
		return operation(account, pwd, amount, true);
	}
	
	private AtmRes operation(String account, String pwd, int amount, boolean isWithdraw) {
		AtmRes checkRes = check(account, pwd, amount);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		if (isWithdraw) {
			//3. 檢查餘額是否足夠
			if(atm.getAmount() < amount) {
				return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
			}
			// 4. 提款(原本的金額 - 提款金額)
			atm.setAmount(atm.getAmount() - amount);
		} else {
			atm.setAmount(atm.getAmount() + amount);
		}
		atmDao.save(atm);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
	}
	
	/**
	 * 1. 確認帳號是否存在<br>
	 * 2. 帳號存在再確認密碼是否正確
	 */
	private AtmRes checkAccountAndPwd(String account, String pwd) {
		Optional<Atm> op = atmDao.findById(account);
		if(op.isEmpty()) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm atm = op.get(); // atm 是從資料庫取出的資料，所以密碼會是密文
		// 2. 檢查密碼
		// encoder.matches(明文, 加密後的密文)
		if(!encoder.matches(pwd, atm.getPwd())) {
			return new AtmRes(RtnCode.PASSWORD_ERROR, new Atm(account));
		}
		return new AtmRes(RtnCode.SUCCESS, atm);
	}
	//=====================================================
	/**
	 * 1. 檢查 account, pwd 是否為 null 或是空字串或是全空白字串<br>
	 * 1.1 角括號裡面放 br 表示斷行，html 標籤語法<br>
	 * 2. 檢查 amount 是否為負數
	 **/
	private AtmRes checkParams2(String account, String pwd, int amount) {			
		if(amount < 0) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(account));
		}
		return checkParams2(account, pwd);
	}
	
	private AtmRes checkParams2(String ...strs) {
		for(String item : strs) {
			if (!StringUtils.hasText(item)) {
				return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(strs[0]));
			}
		}
		return new AtmRes(RtnCode.SUCCESS, null); // return null 表示參數格式確認無誤
	}
	
	private AtmRes check(String account, String pwd, int amount) {
		AtmRes res = checkParams2(account, pwd, amount);
		RtnCode rtnCode = res.getRtnCode();
		if (rtnCode.getCode() != 200) {
			return new AtmRes(rtnCode, new Atm(account));
		}
		return checkAccountAndPwd(account, pwd);
	}
	
	/**
	 * strs[0] : account；strs[1]: oldPwd/pwd；strs[2]: newPwd
	 */
	private AtmRes check(String ...strs) {
		AtmRes res = checkParams2(strs[0], strs[1], strs[2]);
		RtnCode rtnCode = res.getRtnCode();
		if (rtnCode.getCode() != 200) {
			return new AtmRes(rtnCode, new Atm(strs[0]));
		}
		return checkAccountAndPwd(strs[0], strs[1]);
	}

	@Override
	public AtmRes updatePwd(String account, String pwdOld, String pwdNew) {
		// TODO Auto-generated method stub
		return null;
	}

}
