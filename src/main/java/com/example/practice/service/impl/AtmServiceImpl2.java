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
		AtmRes checkRes = checkParams2(account, pwd, amount);//����k��W�z���ˬd���ð_��
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		if(atmDao.existsById(account)) {
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		// �s�إX�� Atm �̭��� pwd �n�[�K��A�s�iDB
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
		// 3. �󴫷s�K�X�A�n�N�s�K�X�[�K
		atm.setPwd(encoder.encode(newPwd));
		// 4. �N��Ƨ�s�^ DB
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
			//3. �ˬd�l�B�O�_����
			if(atm.getAmount() < amount) {
				return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
			}
			// 4. ����(�쥻�����B - ���ڪ��B)
			atm.setAmount(atm.getAmount() - amount);
		} else {
			atm.setAmount(atm.getAmount() + amount);
		}
		atmDao.save(atm);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
	}
	
	/**
	 * 1. �T�{�b���O�_�s�b<br>
	 * 2. �b���s�b�A�T�{�K�X�O�_���T
	 */
	private AtmRes checkAccountAndPwd(String account, String pwd) {
		Optional<Atm> op = atmDao.findById(account);
		if(op.isEmpty()) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm atm = op.get(); // atm �O�q��Ʈw���X����ơA�ҥH�K�X�|�O�K��
		// 2. �ˬd�K�X
		// encoder.matches(����, �[�K�᪺�K��)
		if(!encoder.matches(pwd, atm.getPwd())) {
			return new AtmRes(RtnCode.PASSWORD_ERROR, new Atm(account));
		}
		return new AtmRes(RtnCode.SUCCESS, atm);
	}
	//=====================================================
	/**
	 * 1. �ˬd account, pwd �O�_�� null �άO�Ŧr��άO���ťզr��<br>
	 * 1.1 ���A���̭��� br ����_��Ahtml ���һy�k<br>
	 * 2. �ˬd amount �O�_���t��
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
		return new AtmRes(RtnCode.SUCCESS, null); // return null ��ܰѼƮ榡�T�{�L�~
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
	 * strs[0] : account�Fstrs[1]: oldPwd/pwd�Fstrs[2]: newPwd
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
