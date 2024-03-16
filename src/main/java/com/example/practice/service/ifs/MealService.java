package com.example.practice.service.ifs;

import java.util.List;

import com.example.practice.entity.Meal;

public interface MealService {
	
	public void addMeal(Meal meal); //�]���b���P��Package�ҥH�|�ݭnimport
	
	public void addMeals(List<Meal> menus);
	
	public void updateMeal(Meal meal);
	
	public void findById(String id);
	
	public void existById(String id);
	
	public void findByName(String name);
	
	public void findAll(); //�n������ҥH���αa�ѼƤF!

}
