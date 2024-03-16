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
	
	// �������Ѯv�Ѫk������
	/** 
	 * 1.�ˬdaccount, pwd�O�_��null�άO�Ŧr��άO�ťզr��<br>
	 * 2.�ˬdamount�O�_���t��
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
		return null; //return null �� ��ܰѼƮ榡�T�{�L�~
	}
	// �������Ѯv�Ѫk������
	
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
		return null; //return null �� ��ܰѼƮ榡�T�{�L�~
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
//		System.out.printf("�b��%s��l�B�G%d\n", account, target.getAmount());
		target.setAmount(target.getAmount() + amount);
		atmDao.save(target);
//		System.out.printf("�b��%s�s�l�B�G%d\n", account, target.getAmount());
	}

	// �s�W��T
	// (1)�b���αK�X�Dnull�B�Ŧr��B�ťզr��
	// (2)�u��s�W�A�B�n��s�W����T���b
	@Override
	public AtmRes addInfo(String account, String pwd, int amount) {
		// 1.�ˬd�榡
//�Ѯv��
		RtnCode res = checkParams(account, pwd, amount);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
//�ۤv��	
//		if (!inputCheck(account, pwd) || !inputCheck(amount)) {
//			System.out.println("��ƿ��~!");
//			return;
//		}
		
//�̭�l		
//		if (atm == null || !StringUtils.hasText(atm.getAccount()) || !StringUtils.hasText(atm.getPwd())) {
//			System.out.println("�b���αK�X�榡���~!");
//			return;
//		}
		// 2.�ˬd���Ʊb��
		if(atmDao.existsById(account)) {
//			System.out.println("�b���w�s�b!");
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		// 3.�������[�K�K�X������
		// (1)new��import���OBCryptPasswordEncoder
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		// (2)���X��J������K�X
//		String pwd = atm.getPwd();
		// (3)�N����K�X�[�K��^�쥻���ܼƮe��
//		pwd = encoder.encode(pwd);
		// (4)�N�[�K�᪺�K��set�^atm
//		atm.setPwd(pwd);
		// ������ �Y��{���X ������
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //�����ŧi�b�̤W���N�i�H���Τ@��new!
//		atm.setPwd(encoder.encode(atm.getPwd()));
		// 4.�x�s��ƨæC�L
		atmDao.save(new Atm(account, encoder.encode(pwd), amount));
//		System.out.println("��Ʒs�W���\!");
//		System.out.printf("�b���G%s �l�B�G%d\n", account, amount);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes getAmountByAccount(String account, String pwd) {
		// 1.�ˬd�榡
//�Ѯv��
		RtnCode res = checkParams(account, pwd);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
		
//�ۤv��
//		if (!inputCheck(account) || !inputCheck(pwd)) {
//			System.out.println("�b���αK�X�榡���~!");
//			return;
//		}

//�̭�l
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
//			System.out.println("�b���αK�X�榡���~!");
//			return;
//		}
		// 2.�ˬd�b���K�X�O�_�k�X
		// ���L�k�ϥαb���P�K�X���o��T�A�]����J��pwd�O����Ӧs�J��Ʈw���O�K��
		AtmRes logIn = logInCheck(account, pwd);
		if (logIn.getRtnCode().getCode() != 200) {
//			System.out.println("�b���αK�X���~!");
			return logIn;
		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("�b�����s�b!");
//			return;
//		}
//		Atm atm = op.get(); // �o�̨��X���K�X���K��!
//		// 3.�ˬd�K�X
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) { // encoder.matches���ѼƬ��u��J������K�X�v���u��Ʈw�����K��K�X�v
//			System.out.println("�K�X���~!");
//			return;
//		}
		// 4.�L�X�l�B
//		System.out.printf("�n�J���\! �b��%s���l�B���G%d\n", logIn.getAccount(), logIn.getAmount());
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, logIn.getAtm().getAmount()));
	}

	@Override
	public AtmRes updatePwd(String account, String pwdOld, String pwdNew) {
		// 1.�ˬd�榡
		RtnCode res = checkParams(account, pwdOld, pwdNew);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwdOld) || !StringUtils.hasText(pwdNew)) {
//			System.out.println("�b���αK�X�榡���~!");
//			return;
//		}
		// 2.�ˬd�b���K�X�O�_�k�X
		AtmRes logIn = logInCheck(account, pwdOld);
		if (logIn.getRtnCode().getCode() != 200) {
//			System.out.println("�b���αK�X���~!");
			return logIn;
		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("�b�����s�b!");
//			return;
//		}
//		Atm atm = op.get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwdOld, atm.getPwd())) {
//			System.out.println("�K�X���~!");
//			return;
//		}
		// 3.��s�K�X�A�B�n�[�s�K�X�[�K
//		Atm atm = atmDao.findById(account).get();
		Atm target = logIn.getAtm();
		target.setPwd(encoder.encode(pwdNew));
		// 4.�N��Ƨ�s�^DB
		atmDao.save(target);
//		System.out.printf("�b��%s�K�X��s���\!\n", account);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes deposit(String account, String pwd, int amount) {
		// 1.�ˬd�榡
//�Ѯv��
		RtnCode res = checkParams(account, pwd, amount);
		if(res != null) {
			return new AtmRes(res, new Atm(account));
		}
//�ۤv��
//		inputCheck(account, pwd);
//		inputCheck(amount);
//�̭�l
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd) || amount < 0) {
//			System.out.println("��J��T���~!");
//			return;
//		}
		// 2.�ˬd�b���K�X�O�_�k�X
		 AtmRes logIn = logInCheck(account, pwd);
		if(logIn.getRtnCode().getCode() != 200) {
//			System.out.println("�b���αK�X���~!");
			return logIn;
		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("�b�����s�b!");
//			return;
//		}
//		Atm atm = op.get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) {
//			System.out.println("�K�X���~!");
//			return;
//		}
		// 3.���L�X�l�B��s�J���B(�l�B�[�W�s�J���B)
//		Atm atm = atmDao.findById(account).get();
		updateBalance(account, amount);
//		System.out.printf("�b��%s�s�ګe�l�B�G%d\n", account, logIn.getAmount());
//		logIn.setAmount(logIn.getAmount() + amount);
//		atmDao.save(logIn);
//		// 4.��ܵ��G
//		System.out.printf("�b��%s�s�ګ�l�B�G%d\n", account, logIn.getAmount());
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, atmDao.findById(account).get().getAmount()));
	}

	@Override
	public AtmRes withdraw(String account, String pwd, int amount) {
		// 1.�ˬd�榡
//�Ѯv��
//		RtnCode res = checkParams(account, pwd, amount);
//		if(res != null) {
//			return new AtmRes(res, new Atm(account));
//		}
//�ۤv��
//		inputCheck(account, pwd);
//		inputCheck(amount);
		
//�̭�l
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd) || amount < 0) {
//			System.out.println("��J��T���~!");
//			return;
//		}
		// 2.�ˬd�b���K�X�O�_�k�X
//		AtmRes logIn = logInCheck(account, pwd);
//		if(logIn.getRtnCode().getCode() != 200) {
////			System.out.println("�b���αK�X���~!");
//			return logIn;
//		}
//		Optional<Atm> op = atmDao.findById(account);
//		if (op.isEmpty()) {
//			System.out.println("�b�����s�b!");
//			return;
//		}
//		Atm atm = op.get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) {
//			System.out.println("�K�X���~!");
//			return;
//		}
		// 3.�]���O���ڡA�ҥH���T�{�l�B�O�_����
//		Atm atm = atmDao.findById(account).get();
		AtmRes goal = logInCheck2(account, pwd, amount);
		if(amount > goal.getAtm().getAmount()) {
			return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
		}
//		Atm target = logIn.getAtm();
//		if(amount > target.getAmount()) {
////			System.out.println("�l�B����!");
//			return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
//		}
		// 4.���ڡG���L�X�l�B����X���B�A�M��A�L�X���G(�l�B�����s�J���B)
		updateBalance(account, -amount);
//		System.out.printf("�b��%s���ګe�l�B�G%d\n", account, logIn.getAmount());
//		logIn.setAmount(logIn.getAmount() - amount);
//		atmDao.save(logIn);
//		System.out.printf("�b��%s���ګ�l�B�G%d\n", account, logIn.getAmount());
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, atmDao.findById(account).get().getAmount()));
	}

	/*
	 * //�T�{��J���r��O�_��null�B�Ŧr��Ϊťզr��H�ή榡�O�_���T private boolean checkLogInInfo(String
	 * account, String password) { //�T�{��J���r��O�_��null�B�Ŧr��Ϊťզr��
	 * if(!StringUtils.hasText(account) || !StringUtils.hasText(password)) { return
	 * false; } //�T�{�榡�O�_���T String accountPattern = "[\\w&&[^_]]{3,8}"; String
	 * passwordPattern = "[\\W+&&[\\w[^_]]]{8,16}";
	 * if(!account.matches(accountPattern) || !password.matches(passwordPattern)) {
	 * return false; } return true; }
	 * 
	 * //�T�{��Ʈw�̬O�_���۹�������ƨä��account�Mpassword�O�_���T private boolean checkData(String
	 * account, String password){ //�T�{��Ʈw�̬O�_���۹�������� Optional<Atm> op =
	 * atmDao.findById(account); if(op.isEmpty()) { return false; }
	 * //���account�Mpassword�O�_���T Atm data = op.get();
	 * if(!data.getAccount().equals(account) ||
	 * !data.getPassword().equals(password)) { return false; } return true; }
	 * 
	 * //�s�W��T //(1)�b���αK�X�Dnull�B�Ŧr��B�ťզr�� //(2)�u��s�W�A�B�n��s�W����T���b
	 * 
	 * @Override public void addInfo(String account, String password, int amount) {
	 * //�T�{�r��O�_�����e�B�榡�O�_���T if(checkLogInInfo(account, password)==false) {
	 * System.out.println("�b���αK�X�榡���~!"); return; } //�T�{���B�O�_���t if(amount < 0) {
	 * System.out.println("���B���~!"); return; } //�T�{�O�_�w�s�b�ۦPaccount�B�T�{�L�~�Y�s�W���
	 * Optional<Atm> op = atmDao.findById(account); if(op.isEmpty()) {
	 * atmDao.save(new Atm(account, password, amount));
	 * System.out.printf("��Ʒs�W���\! �b���G%s �K�X�G%s �l�B�G%d", account, password, amount);
	 * return; } System.out.println("�b���w�s�b!"); }
	 * 
	 * //�z�L�b�����o�l�B //(1)�ˬd�b���αK�X //(2)��ܱb���Y�l�B
	 * 
	 * @Override public void checkAmount(String account, String password) {
	 * //�T�{��J����ƬO�_��null�B�Ŧr��Ϊťզr�� if(checkLogInInfo(account, password)==false) {
	 * System.out.println("�b���αK�X�榡���~!"); return; }
	 * //�T�{��Ʈw�̬O�_���۹�������ƧY���account�Mpassword�O�_���T if(checkData(account,
	 * password)==false) { System.out.println("�b���αK�X���~!"); return; } //��勵�T�L�X���
	 * System.out.printf("�b��%s�d�ߦ��\! �l�B��%d",
	 * atmDao.findById(account).get().getAccount(),
	 * atmDao.findById(account).get().getAmount()); }
	 * 
	 * //�ק�K�X //(1)�ˬd�b���αK�X //(2)��T���b
	 * 
	 * @Override public void passwordReset(String account, String password) {
	 * //�T�{�O�_����J��� if(checkLogInInfo(account, password)==false) {
	 * System.out.println("�b���αK�X�榡���~!"); return; } //�T�{�b���K�X�O�_�@�P
	 * if(checkData(account, password)==false) { System.out.println("�b���αK�X���~!");
	 * return; } //�ШϥΪ̿�J�n��s���K�X Scanner scan = new Scanner(System.in); String input
	 * = scan.next(); String passwordPattern = ""; for(;;) {
	 * if(!input.matches(passwordPattern)) {
	 * System.out.println("�K�X�榡���~! �Э��s��J�s�K�X!"); input = scan.next(); continue; }
	 * //��s�K�X�B��ܧ�s���\ atmDao.save(new
	 * Atm(account,input,atmDao.findById(account).get().getAmount()));
	 * System.out.println("�K�X��s���\!"); }
	 */
}
