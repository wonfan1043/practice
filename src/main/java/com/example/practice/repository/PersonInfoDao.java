package com.example.practice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {

	// 只要不是PK(唯一值)，就有可能找到多筆資料 → 使用List!
	public List<PersonInfo> findByAgeGreaterThan(int age);

	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater);

	// SQL的預設排序法為Asc，所以其實也可以寫findByAgeLessThanEqualOrderByAge就好、不寫最後的Asc
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);

	// 找到年紀介於2個數字之間(有包含) 的資訊，以年齡由大到小排序、只取前3筆資料
	// JPA限制筆數使用First或Top兩個關鍵字加上數字，要放在findBy的中間
	public List<PersonInfo> findFirst3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);

	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);

	// 取得 city 包含某個特定字的所有個人資訊
	public List<PersonInfo> findByCityContaining(String keyword);

	// 找出年紀大於輸入條件以及city 包含某個特定字的所有人資訊，依照年齡由大到小排序
	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String keyword);

	/* SQL語法：INSERT */
	@Modifying
	@Transactional
	@Query(value = "insert into person_info (id, name) values (?1, ?2)", nativeQuery = true)
	public int insert(String id, String name);
	//佔位符寫法必須按照順序

	@Modifying
	@Transactional
	@Query(value = "insert into person_info (id, name, city) values (:inputId, :inputName, :inputCity)", nativeQuery = true)
	public int insert(@Param("inputId") String id, @Param("inputName") String name, @Param("inputCity") String city);
	//:inputId → 冒號後的名字是自定義的，這個寫法因為明確指定參數對應的值了所以順序沒差、可以不按照資料庫表的順序
	
	/* SQL語法：UPDATE → 記得一定要加上WHERE否則所有資料都會被更新 */
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where id = ?1", nativeQuery = true)
	public int updateCityById(String id, String city);
	
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where age <= ?1", nativeQuery = true)
	public int updateCityByAgeLessThanEqual(int age, String city);
	
	@Modifying
	@Transactional
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById2(String id, String city);
	//nativeQuery = true沒寫的話代表是預設的false，語法中操作的是Entity和其屬性(變數)名稱
	//上方其他方法nativeQuery = true代表與法中操作的對象是資料表和欄位名稱
	//因此這裡語法中的city和id是Entity的屬性(變數)名稱，只是剛好和資料庫同名
	
	@Modifying
	@Transactional(rollbackOn = Exception.class) 
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById3(String id, String city);
	
}
