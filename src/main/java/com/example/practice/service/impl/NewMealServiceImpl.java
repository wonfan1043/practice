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
		System.out.println("�s�W�\�I���G" + newMeal.getName());
		System.out.println("�Ʋz�覡���G" + newMeal.getCookingStyle());
		System.out.println("�\�I���欰�G" + newMeal.getPrice());
	}

	/*@Override
	public void updateNewMeal(NewMeal newMeal) {
		//�q�ϥΪ̦^�Ǫ�newMeal�����X���PK�Gname��cookingStyle�A�M���iNewMealId���O����
		//�Ы�NewMealId�o�����O�N�O���F�����޲z�Ҧ���PK
		NewMealId id = new NewMealId();
		id.setName(newMeal.getName());
		id.setCookingStyle(newMeal.getCookingStyle());
		//��i�h��N�i�H��newMealDao����kfindById�h��Ʈw������ơB�T�{�O�_���ӵ����
		Optional<NewMeal> check = newMealDao.findById(id);
		if(check.isEmpty()) {
			System.out.println("�d�L����!");
			return;
		} 
		NewMeal target = check.get();
		System.out.println("��s�e:" + target.getName() + target.getCookingStyle() + target.getPrice());
		newMealDao.save(newMeal);
		System.out.println("��s��:" + newMeal.getName() + newMeal.getCookingStyle() + newMeal.getPrice());
	} */
	
	// ������ �W���o�˼g�Ӥ����F�A�ҥH�i�H�hNewMealId��class�s�W�غc��k ������
	
	@Override
	public void updateNewMeal(NewMeal newMeal) {
		Optional<NewMeal> op = newMealDao.findById(new NewMealId(newMeal.getName(),newMeal.getCookingStyle()));
		if(op.isEmpty()) {
			System.out.println("�d�L���!");
			return;
		}
		NewMeal res = op.get();
		System.out.println("��s�e:" + res.getName() + res.getCookingStyle() + res.getPrice());
		newMealDao.save(newMeal);
		System.out.println("��s��:" + newMeal.getName() + newMeal.getCookingStyle() + newMeal.getPrice());
	}

	@Override
	public void findByNameAndCookingStyle(String name, String cookingStyle) {
		NewMeal res = newMealDao.findByNameAndCookingStyle(name, cookingStyle);
		if (res == null) {
			//if���p�A�����O�P�_���A�u��true��false��ص��G
			//�p�G�Q�n�ٲ�== null�A�����n�����a�J��ƫ��A�O���L�Ȫ��ܼ�
			//���o��ONewMeal����ƫ��A�A�ҥH������== null�g�X��
			System.out.println("�d�L���!");
		} else {
			System.out.println(res.getName() + res.getCookingStyle() + res.getPrice());
		}
	}

}
