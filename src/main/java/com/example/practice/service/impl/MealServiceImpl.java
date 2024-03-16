package com.example.practice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.entity.Meal;
import com.example.practice.repository.MealDao;
import com.example.practice.service.ifs.MealService;

@Service //託管成服務類
public class MealServiceImpl implements MealService{
	
	@Autowired  //將原本交由Spring Boot託管的物件(class、interface等)拿來使用
	private MealDao mealDao; //類似屬性的宣告方式

	@Override
	public void addMeal(Meal meal) {
		//save：將資料存進DB
		//如果PKey已存在會更新已存在之資料(update)，反之執行資料新增(insert)
		mealDao.save(meal); 
		System.out.println("新增的餐點為：" + meal.getName() + "，價格為：" + meal.getPrice());
	}

	@Override
	public void addMeals(List<Meal> meals) {
		
		//老師不推薦寫法
//		for(int i = 0; i < meals.size(); i++) {
//			System.out.println("新增的餐點為：" + meals.get(i).getName() + "，價格為：" + meals.get(i).getPrice());
//		}
		
		mealDao.saveAll(meals);
		for(Meal item : meals) {
			System.out.println("新增的餐點為：" + item.getName() + "，價格為：" + item.getPrice());
		}
	}

	@Override
	public void updateMeal(Meal meal) {
		//Step1：找欲更新的資料是否已存在於資料庫
		//使用Jpa的findById時，他就是會自己把取出來的物件用Optional包起來，所以我們要自己寫出後續的if判斷式等部分去判斷他是不是null
		//★★★ ↑↑↑背起來↑↑↑ ★★★
		Optional<Meal> op = mealDao.findById(meal.getName()); //findById的Id指的是資料庫的PKey
		//一個物件被Optional包起來主要是強制去判斷從資料庫取出來的物件是否為null
		if(op.isEmpty()) {  //去判斷被Optional包起來的物件是否為null
			System.out.println("找無!!!");
			return;
		}
		Meal res = op.get();  //取出被Optional包起來的物件
		// ===== 我是分隔線 =====
		System.out.println("更新前：" + res.getName() + res.getPrice());
		mealDao.save(meal);
		System.out.println("更新後：" + meal.getName() + meal.getPrice());
		
	}

	@Override
	public void findById(String id) {
		if(mealDao.findById(id).isEmpty()) {
			System.out.println(id + "不存在!");
		} else {
			System.out.println(id + "存在!");
		}
	}
	
	//★★★關於上下的差別★★★
	//findById取出的資料是一整筆資料物件，如果後續要針對資料內容做修改就用這個
	//existById取出來的資料是布林值，非true即false，只有1個bit，如果只是單純要查有沒有該資料就用這個

	@Override
	public void existById(String id) {
		boolean res = mealDao.existsById(id);
		//以下為固定寫法，背起來!!!
		if (res) { //等同於 res == true，後面的 == ture 沒有寫的話預設就是等於true，要反過來的話就在res前面加!
			System.out.println(id + "存在!");
		} else { //等同於 res == false
			System.out.println(id + "不存在!");
		}
	}

	@Override
	public void findByName(String name) {
		Meal res = mealDao.findByName(name);
		
		if (res == null) {
			System.out.println(name + "不存在!");
			return; //不寫else要加return讓他結束執行
		}
		System.out.println("菜名：" + res.getName() + " 價格：" + res.getPrice());
	}

	@Override
	public void findAll() {
		List<Meal> res = mealDao.findAll();
		for(Meal item : res) {
			System.out.println("菜名：" + item.getName() + " 價格：" + item.getPrice());
		}
	}

}
