package com.example.practice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.entity.NewMeal;
import com.example.practice.entity.NewMealId;
import com.example.practice.repository.NewMealDao;
import com.example.practice.service.ifs.NewMealService;

@Service
public class NewMealServiceImpl implements NewMealService{
	
	@Autowired
	private NewMealDao newMealDao;

	@Override
	public void addNewMeal(NewMeal newMeal) {
		newMealDao.save(newMeal);
		System.out.println("新增餐點為：" + newMeal.getName());
		System.out.println("料理方式為：" + newMeal.getCookingStyle());
		System.out.println("餐點價格為：" + newMeal.getPrice());
	}

	/*@Override
	public void updateNewMeal(NewMeal newMeal) {
		//從使用者回傳的newMeal中取出兩個PK：name跟cookingStyle，然後丟進NewMealId類別物件中
		//創建NewMealId這個類別就是為了集中管理所有的PK
		NewMealId id = new NewMealId();
		id.setName(newMeal.getName());
		id.setCookingStyle(newMeal.getCookingStyle());
		//丟進去後就可以用newMealDao的方法findById去資料庫中撈資料、確認是否有該筆資料
		Optional<NewMeal> check = newMealDao.findById(id);
		if(check.isEmpty()) {
			System.out.println("查無此菜!");
			return;
		} 
		NewMeal target = check.get();
		System.out.println("更新前:" + target.getName() + target.getCookingStyle() + target.getPrice());
		newMealDao.save(newMeal);
		System.out.println("更新後:" + newMeal.getName() + newMeal.getCookingStyle() + newMeal.getPrice());
	} */
	
	// ↓↓↓ 上面這樣寫太冗長了，所以可以去NewMealId的class新增建構方法 ↓↓↓
	
	@Override
	public void updateNewMeal(NewMeal newMeal) {
		Optional<NewMeal> op = newMealDao.findById(new NewMealId(newMeal.getName(),newMeal.getCookingStyle()));
		if(op.isEmpty()) {
			System.out.println("查無資料!");
			return;
		}
		NewMeal res = op.get();
		System.out.println("更新前:" + res.getName() + res.getCookingStyle() + res.getPrice());
		newMealDao.save(newMeal);
		System.out.println("更新後:" + newMeal.getName() + newMeal.getCookingStyle() + newMeal.getPrice());
	}

	@Override
	public void findByNameAndCookingStyle(String name, String cookingStyle) {
		NewMeal res = newMealDao.findByNameAndCookingStyle(name, cookingStyle);
		if (res == null) {
			//if的小括號中是判斷式，只有true跟false兩種結果
			//如果想要省略== null，必須要直接帶入資料型態是布林值的變數
			//但這邊是NewMeal的資料型態，所以必須把== null寫出來
			System.out.println("查無資料!");
		} else {
			System.out.println(res.getName() + res.getCookingStyle() + res.getPrice());
		}
	}

}
