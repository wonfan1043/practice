package com.example.practice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.practice.entity.Meal;
import com.example.practice.service.ifs.MealService;
import com.example.practice.service.impl.MealServiceImpl;

@SpringBootTest
public class MealServiceTest {
	
	@Autowired
	public MealService mealService;
	
	@Test
	public void addMealTest1() {
//		Meal meal = new Meal();
//		meal.setName("假的雞肉絲米糕");
//		meal.setPrice(25);
		
		//創立建構方法的話就可以一行解決！
		Meal meal = new Meal("假的雞肉絲米糕", 25);
		
		//原本new一個類別是像下面這樣寫
//		MealServiceImpl mealService = new MealServiceImpl();
		//但因為類別MealServiceImpl有實作介面，所以也可以用介面來裝new出來的類別
		//即把MealService這個介面當作入口點去進入類別然後各自實作
//		MealService mealService = new MealServiceImpl(); //在15、16行託管MealService類別後就不用在new他便可以用
		mealService.addMeal(meal);
	}
	
	//addMealTest1可以用匿名方式寫，會短很多
	@Test
	public void addMealTest2() {
//		MealService mealService = new MealServiceImpl();
		mealService.addMeal(new Meal("假的雞肉絲米糕", 25));
	}
	
	@Test
	public void addMealsTest1() {
		List<Meal> list = new ArrayList<>();
		Meal meal1 = new Meal("假的雞肉絲米糕", 25);
		Meal meal2 = new Meal("油爆高麗菜", 40);
		Meal meal3 = new Meal("乾癟炸雞排", 70);
		list.add(meal1);
		list.add(meal2);
		list.add(meal3);
		
//		MealService mealService = new MealServiceImpl();
	    mealService.addMeals(list);
	}
	
	//同樣可以用匿名寫法處理上面的meal1~meal3，程式碼會短很多
	@Test
	public void addMealsTest2() {
		List<Meal> list = new ArrayList<>();
		list.add(new Meal("假的雞肉絲米糕", 25));
		list.add(new Meal("油爆高麗菜", 40));
		list.add(new Meal("乾癟炸雞排", 70));
		
		MealService mealService = new MealServiceImpl();
	    mealService.addMeals(list);
	}
	
	@Test
	public void updateMealTest1() {
		mealService.updateMeal(new Meal("油爆高麗菜", 60));
		mealService.updateMeal(new Meal("醬爆燒肉", 60));
	}
	
	@Test
	public void existByIdTest() {
		mealService.existById("油爆高麗菜");
		mealService.existById("醬爆燒肉");
	}
	
	@Test
	public void findByIdTest() {
		mealService.findById("油爆高麗菜");
		mealService.findById("醬爆燒肉");
	}
	
	@Test
	public void findByNameTest() {
		mealService.findByName("醬爆燒肉");
	}
	
	@Test
	public void findAllTest() {
		mealService.findAll();
	}
	
}
