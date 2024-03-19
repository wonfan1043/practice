package com.example.practice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;
import com.example.practice.vo.JoinVo;

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
	
	//clearAutomatically = true → 更新資料後會清除暫存資料
	//用於更新資料後再從DB取得的資料依然是舊資料，就可以使用
	@Modifying(clearAutomatically = true)
	@Transactional 
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById3(String id, String city);
	
	/* SQL語法：SELECT */
	
	@Query(value = "select * from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity(String city);
	
	//nativeQuery = true → 只能撈取所有欄位
	@Query(value = "select id, name, age, city from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity1(String city);
	
	//nativeQuery = false → 操作的是Entity的屬性，撈取的欄位必須要有對應的建構方法
	//因為語法中有使用new PersonInfo(id, name, age)，所以就要有對應的建構方法
	@Query(value = "select new PersonInfo(id, name, age) from PersonInfo where city = ?1")
	public List<PersonInfo> findByCity2(String city);
	
	//nativeQuery = false + 使用別名p(名稱自訂義) → 撈取的是整個Entity
	//語法中沒有使用到new PersonInfo(id, name, age, city)
	// → 即使把帶有全部參數的建構方法註解掉也不會報錯
	@Query(value = "select p from PersonInfo as p where city = ?1")
	public List<PersonInfo> findByCity3(String city);
	
	/* SQL語法：SELECT DISTINCT */
	
	@Query(value = "select distinct new PersonInfo(city) from PersonInfo ")
	public List<PersonInfo> findDistinctCity();
	
	/* SQL語法：ORDER BY (asc default) */
	
//	@Query(value = "select * from person_info where city = ?1 order by age", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where city = ?1 order by age")
	public List<PersonInfo> findByCityOrderByAge(String city);
	
	/* SQL語法：ORDER BY + LIMIT */
	
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where city = ?1 order by age limit ?2") → 這個不能用，因為limit只能在nativeQuery = true時執行
	public List<PersonInfo> findByCityOrderByAgeLimit(String city, int limit);
	
	/* SQL語法：LIMIT + 起始Index */
	// limit 5(限制5筆資料) 等同於 limit 0, 5(限制5筆資料，從index = 0開始算)
	
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2, ?3", nativeQuery = true)
	public List<PersonInfo> findByCityOrderByAgeLimit2(String city, int startIndex, int limit);
	
	/* SQL語法：BETWEEN */
	
//	@Query(value = "select * from person_info where age between ?1 and ?2", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where age between ?1 and ?2")
	public List<PersonInfo> findByAgeBetween(int age1, int age2);
	
	/* SQL語法：IN */
	//JPQL語法無法達到動態參數(可變式參數)，所以直接用JPA的in就好
	
//	@Query(value = "select * from person_info where city in(?1, ?2)", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where age in(?1, ?2)")
	public List<PersonInfo> findByCityIn(String ...city);
	
	/* SQL語法：LIKE */
	
//	@Query(value = "select * from person_info where city like %?1%", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where city like %?1%")
	public List<PersonInfo> findByCityLike(String str);
	
	/* SQL語法：LIKE + IGNORE CASE */
	//lower → 把比較的值轉成全小寫來比較
	//uppder → 把比較的值轉成全大寫來比較
	//要使用concat連接字串和變數，SQL語法的字串是用單引號
	
//	@Query(value = "select * from person_info where lower(city) like lower(concat('%',?1,'%'))", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where lower(city) like lower(concat('%',?1,'%'))")
	public List<PersonInfo> findByCityContainingIgnoreCase(String str);
	//SQL語法不知為何無法用，先用JPA的containing!
	
	/* SQL語法：REGEXP */
	
	@Query(value = "select * from person_info where city regexp ?1", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where city regexp ?1") → 這個不能用，因為REGECP只能在nativeQuery = true時執行
	public List<PersonInfo> findByCityRegexp(String str);
	
	//要多個的話用||連接
	@Query(value = "select * from person_info where city regexp ?1||?2", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1, String str2);
	
	/* SQL語法：JOIN */
	//select (表1的)欄位名1, (表2的)欄位名2 from 表1 join 表2 on 表1欄位值=表2欄位值
	
//	@Query(value = "select * from person_info as p join atm on p.id = atm.account", nativeQuery = true) 
//  → 這樣寫雖然可以跑，但只會有person_info的資料，因為資料型態是PersonInfo，沒辦法裝atm的資料 → 到VO新增一個類別JoinVo來當容器且一定要用別名寫法
// 	(1)建議使用nativeQuery = false，因為使用true時，接回來的資料型態是Object(無getter/setter)
//  (2)因為JoinVo沒有被Spring Boot託管(class上沒有任何annotation)，所以必須要告知Spring Boot路徑
//  (3)JoinVo中的所有屬性，要使用 表.欄位 或 別名.欄位 來表示
	@Query(value = "select new com.example.practice.vo.JoinVo(p.id, p.name, p.age, p.city, a.amount) from PersonInfo as p join Atm as a on p.id = a.account") 
	public List<JoinVo> joinById();
	
}
