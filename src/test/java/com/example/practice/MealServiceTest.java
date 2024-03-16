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
//		meal.setName("�������׵��̿|");
//		meal.setPrice(25);
		
		//�Х߫غc��k���ܴN�i�H�@��ѨM�I
		Meal meal = new Meal("�������׵��̿|", 25);
		
		//�쥻new�@�����O�O���U���o�˼g
//		MealServiceImpl mealService = new MealServiceImpl();
		//���]�����OMealServiceImpl����@�����A�ҥH�]�i�H�Τ����Ӹ�new�X�Ӫ����O
		//�Y��MealService�o�Ӥ�����@�J�f�I�h�i�J���O�M��U�۹�@
//		MealService mealService = new MealServiceImpl(); //�b15�B16��U��MealService���O��N���Φbnew�L�K�i�H��
		mealService.addMeal(meal);
	}
	
	//addMealTest1�i�H�ΰΦW�覡�g�A�|�u�ܦh
	@Test
	public void addMealTest2() {
//		MealService mealService = new MealServiceImpl();
		mealService.addMeal(new Meal("�������׵��̿|", 25));
	}
	
	@Test
	public void addMealsTest1() {
		List<Meal> list = new ArrayList<>();
		Meal meal1 = new Meal("�������׵��̿|", 25);
		Meal meal2 = new Meal("�o�z���R��", 40);
		Meal meal3 = new Meal("��ç������", 70);
		list.add(meal1);
		list.add(meal2);
		list.add(meal3);
		
//		MealService mealService = new MealServiceImpl();
	    mealService.addMeals(list);
	}
	
	//�P�˥i�H�ΰΦW�g�k�B�z�W����meal1~meal3�A�{���X�|�u�ܦh
	@Test
	public void addMealsTest2() {
		List<Meal> list = new ArrayList<>();
		list.add(new Meal("�������׵��̿|", 25));
		list.add(new Meal("�o�z���R��", 40));
		list.add(new Meal("��ç������", 70));
		
		MealService mealService = new MealServiceImpl();
	    mealService.addMeals(list);
	}
	
	@Test
	public void updateMealTest1() {
		mealService.updateMeal(new Meal("�o�z���R��", 60));
		mealService.updateMeal(new Meal("���z�N��", 60));
	}
	
	@Test
	public void existByIdTest() {
		mealService.existById("�o�z���R��");
		mealService.existById("���z�N��");
	}
	
	@Test
	public void findByIdTest() {
		mealService.findById("�o�z���R��");
		mealService.findById("���z�N��");
	}
	
	@Test
	public void findByNameTest() {
		mealService.findByName("���z�N��");
	}
	
	@Test
	public void findAllTest() {
		mealService.findAll();
	}
	
}
