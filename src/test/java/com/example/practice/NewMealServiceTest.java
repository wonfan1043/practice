package com.example.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.practice.entity.NewMeal;
import com.example.practice.service.ifs.NewMealService;

@SpringBootTest
public class NewMealServiceTest {
	
	@Autowired
	private NewMealService newMealService;
	
	@Test
	public void addNewMealTest1() {
//		NewMeal meal = new NewMeal();
//		meal.setName("三明治");
//		meal.setCookingStyle("炸");
//		meal.setPrice(30);
//		newMealService.addNewMeal(meal);
		
		// ↓↓↓使用建構方法減少程式碼↓↓↓
//		NewMeal meal = new NewMeal("三明治","炸",30);
//		newMealService.addNewMeal(meal);

		// ↓↓↓使用匿名類別繼續減少程式碼↓↓↓
		newMealService.addNewMeal(new NewMeal("三明治","炸",30));
	}
	
	@Test
	public void updateNewMealTest() {
		newMealService.updateNewMeal(new NewMeal("三明治","炸",50));
	}
	
	@Test
	public void findByNameAndCookingStyleTest() {
		newMealService.findByNameAndCookingStyle("三明治", "炸");
	}
}
