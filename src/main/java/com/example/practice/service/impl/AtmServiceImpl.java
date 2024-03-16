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

@Service
public class AtmServiceImpl implements AtmService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private AtmDao atmDao;

	@Override
	public AtmRes addInfo(String account, String pwd, int amount) {
		AtmRes res = checkParams(account, pwd, amount);
		if (res != null) {
			return new AtmRes(res.getRtnCode(), new Atm(account));
		}
		if (atmDao.existsById(account)) {
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		atmDao.save(new Atm(account, encoder.encode(pwd), amount));
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes getAmountByAccount(String account, String pwd) {
		AtmRes res = checkAll(account, pwd);
		RtnCode rtn = res.getRtnCode();
		if(rtn.getCode() != 200) {
			return res;
		}
		Atm result = res.getAtm();
		return new AtmRes(rtn, new Atm(result.getAccount(), result.getAmount()));
	}

	@Override
	public AtmRes updatePwd(String account, String pwdOld, String pwdNew) {
		AtmRes res = checkAll(account, pwdOld, pwdNew);
		if (res.getRtnCode().getCode() != 200) {
			return res;
		}
		Atm result = res.getAtm();
		result.setPwd(encoder.encode(pwdNew));
		atmDao.save(result);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes deposit(String account, String pwd, int amount) {
		return updateBalance(account, pwd, amount, true);
	}

	@Override
	public AtmRes withdraw(String account, String pwd, int amount) {
		return updateBalance(account, pwd, amount, false);
	}

	/*
	 * ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
	 * ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 自定義方法們 ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
	 * ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
	 */
	
	// 自定義方法：檢查多個字串的格式【Overload】
	private AtmRes checkParams(String ...strs) {
		for (String item : strs) {
			if (!StringUtils.hasText(item)) {
				return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(strs[0]));
			}
		}
		return new AtmRes(RtnCode.SUCCESS, new Atm(strs[0]));
	}
	
	// 自定義方法：檢查字串+字串+數字的格式【Overload】
	private AtmRes checkParams(String account, String pwd, int amount) {
		if (amount < 0) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(account));
		}
		return checkParams(account, pwd);
	}

	// 自定義方法：檢查多個字串的格式以及帳號密碼是否相符
	private AtmRes checkAll(String ...strs) {
		AtmRes checkInput = checkParams(strs);
		if(checkInput.getRtnCode().getCode() != 200) {
			return new AtmRes(checkInput.getRtnCode(),new Atm(strs[0]));
		}
		Optional<Atm> locate = atmDao.findById(strs[0]);
		if(locate == null) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(strs[0]));
		}
		Atm target = locate.get();
		if(!encoder.matches(strs[1], target.getPwd())) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(strs[0]));
		}
		return new AtmRes(RtnCode.SUCCESS, target);
	}
	
	// 自定義方法：檢查多個字串的格式以及帳號密碼是否相符，都沒問題後操作提取款
	private AtmRes updateBalance(String account, String pwd, int amount, boolean isDeposit) {
		AtmRes checkInput = checkParams(account, pwd, amount);
		if(checkInput.getRtnCode().getCode() != 200) {
			return new AtmRes(checkInput.getRtnCode(),new Atm(account));
		}
		Optional<Atm> locate = atmDao.findById(account);
		if(locate == null) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm target = locate.get();
		if(!encoder.matches(pwd, target.getPwd())) {
			return new AtmRes(RtnCode.INPUT_INFO_ERROR, new Atm(account));
		}
		if(isDeposit) {
			target.setAmount(target.getAmount() + amount);
			atmDao.save(target);
			return new AtmRes(RtnCode.SUCCESS, new Atm(account, target.getAmount()));
		} else {
			if(amount > target.getAmount()) {
				return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
			}
			target.setAmount(target.getAmount() - amount);
			atmDao.save(target);
			return new AtmRes(RtnCode.SUCCESS, new Atm(account, target.getAmount()));
		}
	}

}
