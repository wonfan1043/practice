package com.example.practice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {

	// �u�n���OPK(�ߤ@��)�A�N���i����h����� �� �ϥ�List!
	public List<PersonInfo> findByAgeGreaterThan(int age);

	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater);

	// SQL���w�]�ƧǪk��Asc�A�ҥH���]�i�H�gfindByAgeLessThanEqualOrderByAge�N�n�B���g�̫᪺Asc
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);

	// ���~������2�ӼƦr����(���]�t) ����T�A�H�~�֥Ѥj��p�ƧǡB�u���e3�����
	// JPA����ƨϥ�First��Top�������r�[�W�Ʀr�A�n��bfindBy������
	public List<PersonInfo> findFirst3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);

	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int ageStart, int ageEnd);

	// ���o city �]�t�Y�ӯS�w�r���Ҧ��ӤH��T
	public List<PersonInfo> findByCityContaining(String keyword);

	// ��X�~���j���J����H��city �]�t�Y�ӯS�w�r���Ҧ��H��T�A�̷Ӧ~�֥Ѥj��p�Ƨ�
	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String keyword);

	/* SQL�y�k�GINSERT */
	@Modifying
	@Transactional
	@Query(value = "insert into person_info (id, name) values (?1, ?2)", nativeQuery = true)
	public int insert(String id, String name);
	//����żg�k�������Ӷ���

	@Modifying
	@Transactional
	@Query(value = "insert into person_info (id, name, city) values (:inputId, :inputName, :inputCity)", nativeQuery = true)
	public int insert(@Param("inputId") String id, @Param("inputName") String name, @Param("inputCity") String city);
	//:inputId �� �_���᪺�W�r�O�۩w�q���A�o�Ӽg�k�]�����T���w�Ѽƹ������ȤF�ҥH���ǨS�t�B�i�H�����Ӹ�Ʈw������
	
	/* SQL�y�k�GUPDATE �� �O�o�@�w�n�[�WWHERE�_�h�Ҧ���Ƴ��|�Q��s */
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where id = ?1", nativeQuery = true)
	public int updateCityById(String id, String city);
	
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where age <= ?1", nativeQuery = true)
	public int updateCityByAgeLessThanEqual(int age, String city);
	
	@Modifying
	@Transactional
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById2(String id, String city);
	//nativeQuery = true�S�g���ܥN��O�w�]��false�A�y�k���ާ@���OEntity�M���ݩ�(�ܼ�)�W��
	//�W���L��knativeQuery = true�N��P�k���ާ@����H�O��ƪ�M���W��
	//�]���o�̻y�k����city�Mid�OEntity���ݩ�(�ܼ�)�W�١A�u�O��n�M��Ʈw�P�W
	
	@Modifying
	@Transactional(rollbackOn = Exception.class) 
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById3(String id, String city);
	
}
