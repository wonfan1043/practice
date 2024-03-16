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
//		meal.setName("�T���v");
//		meal.setCookingStyle("��");
//		meal.setPrice(30);
//		newMealService.addNewMeal(meal);
		
		// �������ϥΫغc��k��ֵ{���X������
//		NewMeal meal = new NewMeal("�T���v","��",30);
//		newMealService.addNewMeal(meal);

		// �������ϥΰΦW���O�~���ֵ{���X������
		newMealService.addNewMeal(new NewMeal("�T���v","��",30));
	}
	
	@Test
	public void updateNewMealTest() {
		newMealService.updateNewMeal(new NewMeal("�T���v","��",50));
	}
	
	@Test
	public void findByNameAndCookingStyleTest() {
		newMealService.findByNameAndCookingStyle("�T���v", "��");
	}
}
