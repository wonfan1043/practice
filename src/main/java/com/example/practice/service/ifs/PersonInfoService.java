package com.example.practice.service.ifs;

import java.util.List;

import com.example.practice.entity.PersonInfo;

@SuppressWarnings("unused")
public interface PersonInfoService {
	
	//創建資訊
	//(1)創建1~多筆資料(id帳號用英文開頭)
	//(2)只能新增資訊
	//(3)回傳被創建的資訊
	public void addInfo(List<PersonInfo> infoList);
	
	//取得所有資訊
	public void findAll();
	
	//透過id取得對應的個人資訊
	public void findById(String id);
	
	//找出年紀大於輸入條件的所有資訊
	public void findByAgeGreaterThan(int age);
	
	//找到年齡小於X或是大於Y的所有資訊
	public void findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater);
	
	//找出年紀小於等於輸入條件的資訊，以年齡由大到小排序
	public void findByAgeLessThanEqualOrderByAgeAsc(int age);
	
	//取得 city 包含某個特定字的所有個人資訊
	public void findByCityContaining(String keyword);

}
