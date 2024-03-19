package com.example.practice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.repository.PersonInfoDao;
import com.example.practice.service.ifs.PersonInfoService;
import com.example.practice.vo.JoinVo;

@SpringBootTest
public class PersonInfoServiceTest {

	@Autowired
	public PersonInfoService personInfoService;

	@Autowired
	public PersonInfoDao personInfoDao;

	@Test
	public void addInfoTest1() {
		List<PersonInfo> addList = new ArrayList<>();
		addList.add(new PersonInfo("A005", "曾五", 28, "台東"));
		addList.add(new PersonInfo("A005", "曾五", 28, "台東"));
		addList.add(new PersonInfo("A006", "王六", 38, "宜蘭"));
		addList.add(new PersonInfo("A007", "陳九", 48, "台中"));
		personInfoService.addInfo(addList);
	}

	// 關於StringUtils.hasText()的邏輯!
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
		System.out.println("=====我是分隔線=====");
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
		personInfoService.findByCityContaining("台");
	}

	/* 資料庫語法TEST */

	// INSERT

	@Test
	public void insertTest1() {
		int a = personInfoDao.insert("A011", "高枴");
		System.out.println("=====>" + a);
	}

	@Test
	public void insertTest2() {
		int a = personInfoDao.insert("A012", "董事", "桃園");
		System.out.println("=====>" + a);
	}

	// UPDATE

	@Test
	public void updateTest1() {
		int a = personInfoDao.updateCityById("A012", "台北");
		System.out.println("=====>" + a);
		a = personInfoDao.updateCityById("A011", "台北");
		System.out.println("=====>" + a);
	}

	@Test
	public void updateTest2() {
		int a = personInfoDao.updateCityByAgeLessThanEqual(30, "桃園");
		System.out.println("=====>" + a);
		a = personInfoDao.updateCityByAgeLessThanEqual(50, "桃園");
		System.out.println("=====>" + a);
	}

	@Test
	public void updateTest3() {
		int a = personInfoDao.updateCityById2("A012", "高雄");
		System.out.println("=====>" + a);
		a = personInfoDao.updateCityById2("A011", "高雄");
		System.out.println("=====>" + a);
	}

	@Transactional
	@Test
	public void updateTest4() throws Exception {
		int a = personInfoDao.updateCityById2("A011", "基隆");
		System.out.println("=====>" + a); // 這裡會印出1,代表有正常更新資料、從高雄變成基隆
		// throw new Exception("===== ERROE YO! =====");
		// //故意丟一個Exception來模擬出錯的環境，此時資料會回朔為原本的高雄
		throw new IOException("===== ERROE YO! ====="); // Transactional只發生在Exception跟RuntimeException
	}

	// @Transactional

	// 情境1：Dao有加@Transactional，但Service的方法沒有加
	// 結果：資料從新竹變為基隆 → @Transactional無效
	// 原因：non-transactional方法呼叫transactional方法
	//    (non-transacional是指方法上沒有加@Transactional)
	@Test
	public void transTest1() {
		personInfoService.transTest1("A002", "基隆");
	}

	// 情境2：Dao和Service的方法都有加@Transactional
	// 結果：資料維持為新竹 → @Transactional有效
	// 原因：transactional方法呼叫transactional方法
	@Test
	public void transTest2() {
		personInfoService.transTest2("A002", "基隆");
	}

	// 情境3：Dao和publicMethod都有加@Transactional，Service方法沒有加
	// Service方法會呼叫publicMethod，Exception發生在publicMethod
	// 結果：資料從新竹變為基隆 → @Transactional無效
	// 原因：non-transactional方法呼叫transactional方法
	@Test
	public void transTest3() {
		personInfoService.transTest3("A002", "基隆");
	}

	// 情境4：Dao和privateMethod都有加@Transactional，Service方法沒有加
	// Service方法會呼叫privateMethod，Exception發生在privateMethod
	// 結果：資料從新竹變為基隆 → @Transactional無效
	// 原因：non-transactional方法呼叫transactional方法
	@Test
	public void transTest4() {
		personInfoService.transTest4("A002", "基隆");
	}

	// 情境5：Dao、privateMethod法和Service的方法都有加@Transactional
	// Service方法會呼叫privateMethod，Exception發生在privateMethod
	// 結果：資料維持為新竹 → @Transactional有效
	@Test
	public void transTest5() {
		personInfoService.transTest5("A002", "基隆");
	}
	
	// 情境6：Dao和Service的方法都有加@Transactional，privateMethod法沒有加
	// Service方法會呼叫privateMethod，Exception發生在privateMethod
	// 結果：資料維持為新竹 → @Transactional有效
	@Test
	public void transTest6() {
		personInfoService.transTest6("A002", "基隆");
	}
	
	// 情境7：@Test上方加上@Transactional，測試內容是呼叫無效的transTest1
	// 結果：資料維持為新竹 → @Transactional有效
	@Transactional
	@Test
	public void transTest7() {
		personInfoService.transTest1("A002", "基隆");
	}
	
	// 情境8：read-only
	// 結果：資料維持為新竹 → 因為是read-only所以不允許更動資料
	@Test
	public void transTest8() {
		personInfoService.transTest8("A002", "基隆");
	}
	
	// SELECT
	
	@Test
	public void selectTest1() {
		List<PersonInfo> res = personInfoDao.findByCity("桃園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest2() {
		List<PersonInfo> res = personInfoDao.findByCity1("桃園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest3() {
		List<PersonInfo> res = personInfoDao.findByCity2("桃園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest4() {
		List<PersonInfo> res = personInfoDao.findByCity3("桃園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectDistinctTest() {
		List<PersonInfo> res = personInfoDao.findDistinctCity();
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByTest() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAge("桃園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByLimitTest() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAgeLimit("桃園", 2);
		System.out.println(res.size());
	}
	
	@Test
	public void findByCityOrderByAgeLimit2Test() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAgeLimit2("桃園", 1, 2);
		System.out.println(res.size());
	}
	
	@Test
	public void findByAgeBetweenTest() {
		List<PersonInfo> res = personInfoDao.findByAgeBetween(20, 80);
		System.out.println(res.size());
	}
	
	@Test
	public void selectLikeTest() {
		List<PersonInfo> res = personInfoDao.findByCityLike("園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectIgnoreCaseLikeTest() {
		List<PersonInfo> res = personInfoDao.findByCityContainingIgnoreCase("園");
		System.out.println(res.size());
	}
	
	@Test
	public void selectRegexpTest() {
		List<PersonInfo> res = personInfoDao.findByCityRegexp("園");
		System.out.println(res.size());
	}
	
	@Test
	public void joinTest() {
		List<JoinVo> res = personInfoDao.joinById();
		System.out.println(res.size());
	}
	
}
