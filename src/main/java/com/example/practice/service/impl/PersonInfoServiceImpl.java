package com.example.practice.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.repository.PersonInfoDao;
import com.example.practice.service.ifs.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public void addInfo(List<PersonInfo> infoList) {
		//程式與資料庫間連接的cost很大，所以連接前能判斷的異常都要先判斷
		//先判斷是否整個list都是空的
		if(infoList == null) {
			System.out.println("請輸入資料!");
			return;
		}
		//再判斷每一筆資料是否為空 or 每筆資料的ID是否為空 or 每筆資料的ID是否為空字串或空白字串
		//本來是寫：item.getId() == null || item.getId().isBlank()
		//但是(1)：StringUtils類當中有hasText這個方法可以直接判別輸入的ID是否為空、空字串或空白字串
		//但是(2)：hasText如果為true代表字串內容不是空、空字串或空白字串，所以前方要加上驚嘆號「!」反轉結果
		for (PersonInfo item : infoList) {
			if(item == null || !StringUtils.hasText(item.getId()) || item.getAge()<0) {
				System.out.println("資料錯誤!");
				continue;
			}
			if(personInfoDao.existsById(item.getId())) {
				System.out.println(item.getId()+"已存在!不要亂假冒!");
				continue; //寫return就會直接結束程式、不能繼續存其他資料
			} 
			personInfoDao.save(item);
			System.out.println("已新增資料："+"ID："+item.getId()+" 名字："+item.getName()+" 年齡："+item.getAge()+" 城市："+item.getCity());
			}
	}
	
//	以下是自己寫的垃圾方法一XDDD
//	System.out.println("請輸入ID（A+3個數字）");
//	Scanner scan = new Scanner(System.in);
//	String inputId = scan.next();
//	Optional<PersonInfo> op = personInfoDao.findById(inputId);
//	if(op.isPresent()) {
//		System.out.println("此人已存在!不要亂假冒!");
//	} else {
//		System.out.println("繼續輸入名字");
//		String inputName = scan.next();
//		System.out.println("繼續輸入年齡");
//		int inputAge = scan.nextInt();
//		System.out.println("繼續輸入城市");
//		String inputCity = scan.next();
//		PersonInfo addIn = new PersonInfo(inputId, inputName, inputAge, inputCity);
//		personInfoDao.save(addIn);
//		System.out.println("已新增資料："+"ID："+addIn.getId()+" 名字："+addIn.getName()+" 年齡："+addIn.getAge()+" 城市："+addIn.getCity());
//	}
//	System.out.println("是否新增下一筆？");
	
	
//	Optional<PersonInfo> op = personInfoDao.findById(personInfo.getId());
//	if(op.isEmpty()) {
//		personInfoDao.save(personInfo);
//		System.out.println("已新增資料");
//		System.out.println("ID："+personInfo.getId());
//		System.out.println("姓名："+personInfo.getName());
//		System.out.println("年齡："+personInfo.getAge());
//		System.out.println("城市："+personInfo.getCity());
//
//	} else {
//		System.out.println("此人已存在!不要亂假冒!");
//	}

	@Override
	public void findAll() {
		List<PersonInfo> allData = personInfoDao.findAll();
		for(PersonInfo item : allData) {
			System.out.println("ID："+item.getId()+" 名字："+item.getName()+" 年齡："+item.getAge()+" 城市："+item.getCity());
		}
	}

	@Override
	public void findById(String id) {
		if(!StringUtils.hasText(id)) {
			System.out.println("請輸入ID!");
			return;
		}
		Optional<PersonInfo> target = personInfoDao.findById(id);
		if(target.isEmpty()){
			System.out.println("查無此人!");
			return;
		}
		System.out.println("ID："+target.get().getId());
		System.out.println("姓名："+target.get().getName());
		System.out.println("年齡："+target.get().getAge());
		System.out.println("城市："+target.get().getCity());
	}

/*	@Override
	public void findByAgeGreaterThan(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeGreaterThan(age);
		if(list.isEmpty()) { //也可以寫list.size()==0
			System.out.println("查無資料!");
			return;
		}
		for(PersonInfo item : list) {
			System.out.println("ID："+item.getId()+" 名字："+item.getName()+" 年齡："+item.getAge()+" 城市："+item.getCity());
		}
	}	*/

	/*	@Override
	public void findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater) {
		//不用寫這個防呆，因為這邊是OR，畫個範圍圖就理解，但如果是AND就不可以了
//		if(ageLess >= ageGreater) {
//			System.out.println("搜尋範圍錯誤!");
//			return;
//		}
		List<PersonInfo> list = personInfoDao.findByAgeLessThanOrAgeGreaterThan(ageLess, ageGreater);
		if(list.isEmpty()) {
			System.out.println("查無資料!");
			return;
		}
		for(PersonInfo item : list) {
			System.out.println("ID："+item.getId()+" 名字："+item.getName()+" 年齡："+item.getAge()+" 城市："+item.getCity());
		}
	}	*/

/*	@Override
	public void findByAgeLessThanEqualOrderByAgeAsc(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanEqualOrderByAgeAsc(age);
		if(list.isEmpty()) {
			System.out.println("查無資料!");
			return;
		}
		for(PersonInfo item : list) {
//			System.out.println("ID："+item.getId()+" 名字："+item.getName()+" 年齡："+item.getAge()+" 城市："+item.getCity());
			System.out.printf("ID：%s 名字：%s 年齡：%d 城市：%s\n",item.getId(),item.getName(),item.getAge(),item.getCity());
		}
	}	*/
	
	// ↓↓↓ 把重複的程式碼拿來自建方法 ↓↓↓
	
	private void checkAndPrintList(List<PersonInfo> list) { //基本上只有這裡會用所以設成private即可
		if(list.isEmpty()) {
			System.out.println("查無資料!");
			return;
		}
		for(PersonInfo item : list) {
			System.out.printf("ID：%s 名字：%s 年齡：%d 城市：%s\n",item.getId(),item.getName(),item.getAge(),item.getCity());
		}
	}
	
	// ↓↓↓ 上方有用到相同程式碼的方法就可以寫成下面這樣 ↓↓↓
	
	@Override
	public void findByAgeGreaterThan(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeGreaterThan(age);
		checkAndPrintList(list);
	}
	
	@Override
	public void findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanOrAgeGreaterThan(ageLess, ageGreater);
		checkAndPrintList(list);
	}
	
	@Override
	public void findByAgeLessThanEqualOrderByAgeAsc(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanEqualOrderByAgeAsc(age);
		checkAndPrintList(list);
	}

	// ===== 我是分隔線 =====
	
	@Override
	public void findByCityContaining(String keyword) {
		List<PersonInfo> list = personInfoDao.findByCityContaining(keyword);
		checkAndPrintList(list);
	}
	
	/* SQL annotation @Transactional */
	@Transactional(rollbackOn = Exception.class)
	@Override
	public int updateCityById3(String id, String city) throws IOException {
		personInfoDao.updateCityById3(id, city);
		throw new IOException("==========");
	}
	//@Transactional → 預設的資料回朔(不更新)是發生在RuntimeException即其子類別，不會包含兄弟類別IOException
	//因此設定Exception及其所有子類別就可以包含IOException，即將層級拉高到父類別
	//可以測試拿掉(rollbackOn = Exception.class)看看資料是否會回朔
}
