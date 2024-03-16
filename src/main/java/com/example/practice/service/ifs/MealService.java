package com.example.practice.service.ifs;

import java.util.List;

import com.example.practice.entity.Meal;

public interface MealService {
	
	public void addMeal(Meal meal); //因為在不同的Package所以會需要import
	
	public void addMeals(List<Meal> menus);
	
	public void updateMeal(Meal meal);
	
	public void findById(String id);
	
	public void existById(String id);
	
	public void findByName(String name);
	
	public void findAll(); //要找全部所以不用帶參數了!

}
