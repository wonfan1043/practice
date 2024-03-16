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
@RestController //单@Controllerよ@ResponseBody
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
	
	// 】】】】】】】】】】 闽@RequestParams 】】】】】】】】】】
	// Α@RequestParam(value = "﹃", required = true/false, defaultValue = "﹃")
	// value = "﹃" △ 单@JsonPropertyノノㄓ﹚竡璶陪ボ或嘿倒ㄏノ
	// required = true/false △ 把计琌ゲ恶箇砞琌true┮ぃ糶
	// defaultValue = "﹃"  △ ボ⊿Τ块把计穦盿箇砞
	@GetMapping(value = "atm/get_amount1")
	public AtmRes getAmount1( //
			@RequestParam(value = "account") String account, //
			@RequestParam(value = "password") String pwd //
			) {
		return atmService.getAmountByAccount(account, pwd);
	}
	
	// 】】】】】】】】】】 闽@PathVariable 】】】】】】】】】】
	// Α@PathVariable()
	// 呼﹚竡呼/{把计}狦把计碞ノ硆腹┪┏絬硈钡 △ ﹚竡呼/{把计},{把计} or ﹚竡呼/{把计}_{把计}
	// value = "﹃" △ 璶盿把计嘿ゲ斗蛤よ﹚竡把计嘿家妓
	// required = true/false △ 把计琌ゲ恶箇砞琌true┮ぃ糶
	// 呼い{把计}场だ把计琌蛤value﹃妓ゲ斗蛤﹚竡把计嘿家妓
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
