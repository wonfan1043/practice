package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.Meal;

@Repository  //�NMealDao���Spring Boot�U�ަ�repository��
public interface MealDao extends JpaRepository<Meal, String>{  //<��ƫ��A, �bEntity���U@ID����ƫ��A>
	
	//�۩w�q��Dao��k�G�u�ݭn�w�q��k�W�١A���ݭn��@
	//(1)�p�m�p�R�W
	//(2)By��B�@�w�n�j�g�_�h�|����
	//(3)findByName��Name�O��Java��Meal���O�����ݩ�name �� By�᭱���W�٩M�Ѽƪ���ƫ��A�n�PEntity���OMeal�̭����ݩ�(�ܼƦW��)�@�Ҥ@��
	//(4)��k�W�ٳ]�w�X���ݩʡA�᭱�N�n��X�ӰѼơA�h�@�өΤ֤@�ӳ����f�I
	public Meal findByName(String name);
	
	
}
