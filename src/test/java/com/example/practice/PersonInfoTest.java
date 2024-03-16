package com.example.practice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.service.ifs.PersonInfoService;

@SpringBootTest
public class PersonInfoTest {
	
	@Autowired
	public PersonInfoService personInfoService;
	
	@Test
	public void addInfoTest1() {
		List<PersonInfo> addList = new ArrayList<>();
		addList.add(new PersonInfo("A005", "����", 28, "�x�F"));
		addList.add(new PersonInfo("A005", "����", 28, "�x�F"));
		addList.add(new PersonInfo("A006", "����", 38, "�y��"));
		addList.add(new PersonInfo("A007", "���E", 48, "�x��"));
		personInfoService.addInfo(addList);
	}
	
	//����StringUtils.hasText()���޿�!
	@Test
	public void addInfoTest2() {
		String str1 = null;
		String str2 = "";
		String str3 = " ";
		String str4 = "ABC";
		System.out.println(str1 + StringUtils.hasText(str1));
		System.out.println(str2 + StringUtils.hasText(str2));
		System.out.println(str3 + StringUtils.hasText(str3));
		System.out.println(str4 + StringUtils.hasText(str4));
		System.out.println("=====�ڬO���j�u=====");
		System.out.println(str1 + !StringUtils.hasText(str1));
		System.out.println(str2 + !StringUtils.hasText(str2));
		System.out.println(str3 + !StringUtils.hasText(str3));
		System.out.println(str4 + !StringUtils.hasText(str4));
	}
	
	@Test
	public void findAllTest() {
		personInfoService.findAll();
	}
	
	@Test
	public void findByIdTest() {
		personInfoService.findById("A003");
	}
	
	@Test
	public void findByAgeGreaterThanTest() {
		personInfoService.findByAgeGreaterThan(30);
	}
	
	@Test
	public void findByAgeLessThanOrAgeGreaterThan() {
		personInfoService.findByAgeLessThanOrAgeGreaterThan(40, 30);
	}
	
	@Test
	public void findByAgeLessThanEqualOrderByAgeAsc() {
		personInfoService.findByAgeLessThanEqualOrderByAgeAsc(50);
	}
	
	@Test
	public void findByCityContainingTest() {
		personInfoService.findByCityContaining("�x");
	}

}
