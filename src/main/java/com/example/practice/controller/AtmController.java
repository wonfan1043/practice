package com.example.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.service.ifs.AtmService;
import com.example.practice.vo.AtmReq;
import com.example.practice.vo.AtmRes;
import com.example.practice.vo.AtmUpdatePwdReq;

//@Controller
@RestController //���P��@Controller�[�W�U�誺@ResponseBody
public class AtmController {
	
	@Autowired
	private AtmService atmService;
	
//	@RequestMapping(value = "atm/add", method = "RequestMethod.POST")
//	public @ResponseBody AtmRes addInfo(@RequestBody AtmReq req) {
//		return atmService.addInfo(req.getAccount(), req.getPwd(), req.getAmount());
//	}

	@PostMapping(value = "atm/add")
	public AtmRes addInfo(@RequestBody AtmReq req) {
		return atmService.addInfo(req.getAccount(), req.getPwd(), req.getAmount());
	}
	
	@GetMapping(value = "atm/get_amount")
	public AtmRes getAmount(@RequestBody AtmReq req) {
		return atmService.getAmountByAccount(req.getAccount(), req.getPwd());
	}
	
	// �������������������� ����@RequestParams ��������������������
	// �榡�G@RequestParam(value = "�r��", required = true/false, defaultValue = "�r��")
	// value = "�r��" �� ���P��@JsonProperty���\�ΡA�ΨӦ۩w�q�n��ܬƻ�W�ٵ��ϥΪ̬�
	// required = true/false �� ���ѼƬO�_������A�w�]�Otrue�ҥH�i�H���g
	// defaultValue = "�r��"  �� ��ܨS����J���Ѽƪ��ȮɡA�|�a�J���w�]��
	@GetMapping(value = "atm/get_amount1")
	public AtmRes getAmount1( //
			@RequestParam(value = "account") String account, //
			@RequestParam(value = "password") String pwd //
			) {
		return atmService.getAmountByAccount(account, pwd);
	}
	
	// �������������������� ����@PathVariable ��������������������
	// �榡�G@PathVariable()
	// ���}�G�۩w�q���}/{�Ѽ�}�A�p�G�h�ӰѼƴN�γr���Ω��u�s�� �� �۩w�q���}/{�Ѽ�},{�Ѽ�} or �۩w�q���}/{�Ѽ�}_{�Ѽ�}
	// value = "�r��" �� �n�a�J���ѼƦW�١A�������誺�۩w�q�ѼƦW�٤@�Ҥ@��
	// required = true/false �� ���ѼƬO�_������A�w�]�Otrue�ҥH�i�H���g
	// ���}����{�Ѽ�}�����A�ѼƤ]�O��value���r��@�ˡA������۩w�q�ѼƦW�٤@�Ҥ@��
	@GetMapping(value = "atm/get_amount2/{account}_{password}")
	public AtmRes getAmount2( //
			@PathVariable(value = "account") String account, //
			@PathVariable(value = "password") String pwd //
			) {
		return atmService.getAmountByAccount(account, pwd);
	}
	
	@PostMapping(value = "atm/update_password")
	public AtmRes updatePwd(@RequestBody AtmUpdatePwdReq req) {
		return atmService.updatePwd(req.getAccount(), req.getPwdOld(), req.getPwdNew());
	}
	
	@PostMapping(value = "atm/deposit")
	public AtmRes deposit(@RequestBody AtmReq req) {
		return atmService.deposit(req.getAccount(), req.getPwd(), req.getAmount());
	}
	
	@PostMapping(value = "atm/withdraw")
	public AtmRes withdraw(@RequestBody AtmReq req) {
		return atmService.withdraw(req.getAccount(), req.getPwd(), req.getAmount());
	}

}
