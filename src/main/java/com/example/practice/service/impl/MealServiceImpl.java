package com.example.practice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.entity.Meal;
import com.example.practice.repository.MealDao;
import com.example.practice.service.ifs.MealService;

@Service //�U�ަ��A����
public class MealServiceImpl implements MealService{
	
	@Autowired  //�N�쥻���Spring Boot�U�ު�����(class�Binterface��)���Өϥ�
	private MealDao mealDao; //�����ݩʪ��ŧi�覡

	@Override
	public void addMeal(Meal meal) {
		//save�G�N��Ʀs�iDB
		//�p�GPKey�w�s�b�|��s�w�s�b�����(update)�A�Ϥ������Ʒs�W(insert)
		mealDao.save(meal); 
		System.out.println("�s�W���\�I���G" + meal.getName() + "�A���欰�G" + meal.getPrice());
	}

	@Override
	public void addMeals(List<Meal> meals) {
		
		//�Ѯv�����˼g�k
//		for(int i = 0; i < meals.size(); i++) {
//			System.out.println("�s�W���\�I���G" + meals.get(i).getName() + "�A���欰�G" + meals.get(i).getPrice());
//		}
		
		mealDao.saveAll(meals);
		for(Meal item : meals) {
			System.out.println("�s�W���\�I���G" + item.getName() + "�A���欰�G" + item.getPrice());
		}
	}

	@Override
	public void updateMeal(Meal meal) {
		//Step1�G�����s����ƬO�_�w�s�b���Ʈw
		//�ϥ�Jpa��findById�ɡA�L�N�O�|�ۤv����X�Ӫ������Optional�]�_�ӡA�ҥH�ڭ̭n�ۤv�g�X����if�P�_���������h�P�_�L�O���Onull
		//������ �������I�_�ӡ����� ������
		Optional<Meal> op = mealDao.findById(meal.getName()); //findById��Id�����O��Ʈw��PKey
		//�@�Ӫ���QOptional�]�_�ӥD�n�O�j��h�P�_�q��Ʈw���X�Ӫ�����O�_��null
		if(op.isEmpty()) {  //�h�P�_�QOptional�]�_�Ӫ�����O�_��null
			System.out.println("��L!!!");
			return;
		}
		Meal res = op.get();  //���X�QOptional�]�_�Ӫ�����
		// ===== �ڬO���j�u =====
		System.out.println("��s�e�G" + res.getName() + res.getPrice());
		mealDao.save(meal);
		System.out.println("��s��G" + meal.getName() + meal.getPrice());
		
	}

	@Override
	public void findById(String id) {
		if(mealDao.findById(id).isEmpty()) {
			System.out.println(id + "���s�b!");
		} else {
			System.out.println(id + "�s�b!");
		}
	}
	
	//����������W�U���t�O������
	//findById���X����ƬO�@�㵧��ƪ���A�p�G����n�w���Ƥ��e���ק�N�γo��
	//existById���X�Ӫ���ƬO���L�ȡA�Dtrue�Yfalse�A�u��1��bit�A�p�G�u�O��­n�d���S���Ӹ�ƴN�γo��

	@Override
	public void existById(String id) {
		boolean res = mealDao.existsById(id);
		//�H�U���T�w�g�k�A�I�_��!!!
		if (res) { //���P�� res == true�A�᭱�� == ture �S���g���ܹw�]�N�O����true�A�n�ϹL�Ӫ��ܴN�bres�e���[!
			System.out.println(id + "�s�b!");
		} else { //���P�� res == false
			System.out.println(id + "���s�b!");
		}
	}

	@Override
	public void findByName(String name) {
		Meal res = mealDao.findByName(name);
		
		if (res == null) {
			System.out.println(name + "���s�b!");
			return; //���gelse�n�[return���L��������
		}
		System.out.println("��W�G" + res.getName() + " ����G" + res.getPrice());
	}

	@Override
	public void findAll() {
		List<Meal> res = mealDao.findAll();
		for(Meal item : res) {
			System.out.println("��W�G" + item.getName() + " ����G" + item.getPrice());
		}
	}

}
