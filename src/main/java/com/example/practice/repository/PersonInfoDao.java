package com.example.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {
	
	//�u�n���OPK(�ߤ@��)�A�N���i����h����� �� �ϥ�List!
	public List<PersonInfo> findByAgeGreaterThan(int age);
	
	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater);
	
	//SQL���w�]�ƧǪk��Asc�A�ҥH���]�i�H�gfindByAgeLessThanEqualOrderByAge�N�n�B���g�̫᪺Asc
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);
	
	//���~������2�ӼƦr����(���]�t) ����T�A�H�~�֥Ѥj��p�ƧǡB�u���e3�����
	//JPA����ƨϥ�First��Top�������r�[�W�Ʀr�A�n��bfindBy������
	public List<PersonInfo> findFirst3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);
	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);
	
	//���o city �]�t�Y�ӯS�w�r���Ҧ��ӤH��T
	public List<PersonInfo> findByCityContaining(String keyword);
	
	//��X�~���j���J����H��city �]�t�Y�ӯS�w�r���Ҧ��H��T�A�̷Ӧ~�֥Ѥj��p�Ƨ�
	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String keyword);

}
