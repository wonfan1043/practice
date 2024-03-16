package com.example.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.practice.repository.AtmDao;
import com.example.practice.service.ifs.AtmService;
import com.example.practice.vo.AtmRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class AtmServiceTest {
	
	private ObjectMapper mapper = new ObjectMapper();

	
	@Autowired
	private AtmService atmService;

	@Autowired
	private AtmDao atmDao;
	
	@Test
	public void addInfoTest() throws JsonProcessingException {
		//Assert.isTrue(判斷式, 字串)
		//代表跟程式說「我確認前方條件是肯定的，如果不是就回覆我後方字串」
		//例如res.getRtnCode().getCode() != 200就是跟程式說「肯定是錯的對吧!」，確實是錯的就綠燈，該錯沒有錯就會亮紅燈
		//如果結果確實相符Junit會跑綠燈，否則跑紅燈然後顯示後方字串內容
		AtmRes res;
		//帳號格式錯誤
		res = atmService.addInfo("", "AA123", 0);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//密碼格式錯誤
		res = atmService.addInfo("A001", null, 0);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//金額格式錯誤
		res = atmService.addInfo("A001", "AA123", -1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//帳號已存在
		res = atmService.addInfo("A001", "AA123", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//成功
		res = atmService.addInfo("A004", "AA321", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//因為以上A004是測試資料，所以測試的最後要把該筆資料刪除
		//這樣的好處是同一筆測試資料可以重複使用
		//手動：到work bench點欄位做邊的空白，全部反白後選擇Delete Row(s)，然後Apply
		//程式：使用Dao的deletById("PK")
		atmDao.deleteById("A004");
		
//		int code = res.getRtnCode().getCode();
//		System.out.println(res);
//		System.out.println(code);
//		//上面的res直接印出會是記憶體位址，所以要用下面的ObjectMapper把物件轉成字串
//		String str = mapper.writeValueAsString(res);
//		System.out.println(str);		
	}
	
	@Test
	public void getAmountByAccountTest() throws JsonProcessingException {
		AtmRes res = atmService.getAmountByAccount("A01", "AA123");
		String str = mapper.writeValueAsString(res);
		System.out.println(str);
	}
	
	@Test
	public void updatePwdTest() throws JsonProcessingException {
		atmService.updatePwd("A01","AA321", "AA123");
	}
	
	@Test
	public void depositTest() throws JsonProcessingException {
		atmService.deposit("A01","AA123", 0);
	}
	
	@Test
	public void withdrawTest() throws JsonProcessingException {
		AtmRes res = atmService.withdraw("A01","AA123", 500);
		String str = mapper.writeValueAsString(res);
		System.out.println(str);
	}

}
