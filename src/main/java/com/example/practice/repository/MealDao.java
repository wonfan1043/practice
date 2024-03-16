package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.Meal;

@Repository  //將MealDao交由Spring Boot託管成repository類
public interface MealDao extends JpaRepository<Meal, String>{  //<資料型態, 在Entity有下@ID的資料型態>
	
	//自定義的Dao方法：只需要定義方法名稱，不需要實作
	//(1)小駝峰命名
	//(2)By的B一定要大寫否則會報錯
	//(3)findByName的Name是指Java的Meal類別中的屬性name → By後面的名稱和參數的資料型態要與Entity類別Meal裡面的屬性(變數名稱)一模一樣
	//(4)方法名稱設定幾個屬性，後面就要放幾個參數，多一個或少一個都打妹！
	public Meal findByName(String name);
	
	
}
