package com.example.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {
	
	//只要不是PK(唯一值)，就有可能找到多筆資料 → 使用List!
	public List<PersonInfo> findByAgeGreaterThan(int age);
	
	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater);
	
	//SQL的預設排序法為Asc，所以其實也可以寫findByAgeLessThanEqualOrderByAge就好、不寫最後的Asc
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);
	
	//找到年紀介於2個數字之間(有包含) 的資訊，以年齡由大到小排序、只取前3筆資料
	//JPA限制筆數使用First或Top兩個關鍵字加上數字，要放在findBy的中間
	public List<PersonInfo> findFirst3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);
	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);
	
	//取得 city 包含某個特定字的所有個人資訊
	public List<PersonInfo> findByCityContaining(String keyword);
	
	//找出年紀大於輸入條件以及city 包含某個特定字的所有人資訊，依照年齡由大到小排序
	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String keyword);

}
