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
		//Assert.isTrue(�P�_��, �r��)
		//�N���{�����u�ڽT�{�e�����O�֩w���A�p�G���O�N�^�Чګ��r��v
		//�Ҧpres.getRtnCode().getCode() != 200�N�O��{�����u�֩w�O������a!�v�A�T��O�����N��O�A�ӿ��S�����N�|�G���O
		//�p�G���G�T��۲�Junit�|�]��O�A�_�h�]���O�M����ܫ��r�ꤺ�e
		AtmRes res;
		//�b���榡���~
		res = atmService.addInfo("", "AA123", 0);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//�K�X�榡���~
		res = atmService.addInfo("A001", null, 0);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//���B�榡���~
		res = atmService.addInfo("A001", "AA123", -1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//�b���w�s�b
		res = atmService.addInfo("A001", "AA123", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//���\
		res = atmService.addInfo("A004", "AA321", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//�]���H�WA004�O���ո�ơA�ҥH���ժ��̫�n��ӵ���ƧR��
		//�o�˪��n�B�O�P�@�����ո�ƥi�H���ƨϥ�
		//��ʡG��work bench�I��찵�䪺�ťաA�����ϥի���Delete Row(s)�A�M��Apply
		//�{���G�ϥ�Dao��deletById("PK")
		atmDao.deleteById("A004");
		
//		int code = res.getRtnCode().getCode();
//		System.out.println(res);
//		System.out.println(code);
//		//�W����res�����L�X�|�O�O�����}�A�ҥH�n�ΤU����ObjectMapper�⪫���ন�r��
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
