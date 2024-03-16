package com.example.practice.service.ifs;

import java.util.List;

import com.example.practice.entity.PersonInfo;

@SuppressWarnings("unused")
public interface PersonInfoService {
	
	//�Ыظ�T
	//(1)�Ы�1~�h�����(id�b���έ^��}�Y)
	//(2)�u��s�W��T
	//(3)�^�ǳQ�Ыت���T
	public void addInfo(List<PersonInfo> infoList);
	
	//���o�Ҧ���T
	public void findAll();
	
	//�z�Lid���o�������ӤH��T
	public void findById(String id);
	
	//��X�~���j���J���󪺩Ҧ���T
	public void findByAgeGreaterThan(int age);
	
	//���~�֤p��X�άO�j��Y���Ҧ���T
	public void findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater);
	
	//��X�~���p�󵥩��J���󪺸�T�A�H�~�֥Ѥj��p�Ƨ�
	public void findByAgeLessThanEqualOrderByAgeAsc(int age);
	
	//���o city �]�t�Y�ӯS�w�r���Ҧ��ӤH��T
	public void findByCityContaining(String keyword);

}
