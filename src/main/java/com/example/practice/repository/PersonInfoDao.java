package com.example.practice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;
import com.example.practice.vo.JoinVo;

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
	
	//clearAutomatically = true �� ��s��ƫ�|�M���Ȧs���
	//�Ω��s��ƫ�A�qDB���o����ƨ̵M�O�¸�ơA�N�i�H�ϥ�
	@Modifying(clearAutomatically = true)
	@Transactional 
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById3(String id, String city);
	
	/* SQL�y�k�GSELECT */
	
	@Query(value = "select * from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity(String city);
	
	//nativeQuery = true �� �u�༴���Ҧ����
	@Query(value = "select id, name, age, city from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity1(String city);
	
	//nativeQuery = false �� �ާ@���OEntity���ݩʡA��������쥲���n���������غc��k
	//�]���y�k�����ϥ�new PersonInfo(id, name, age)�A�ҥH�N�n���������غc��k
	@Query(value = "select new PersonInfo(id, name, age) from PersonInfo where city = ?1")
	public List<PersonInfo> findByCity2(String city);
	
	//nativeQuery = false + �ϥΧO�Wp(�W�٦ۭq�q) �� �������O���Entity
	//�y�k���S���ϥΨ�new PersonInfo(id, name, age, city)
	// �� �Y�ϧ�a�������Ѽƪ��غc��k���ѱ��]���|����
	@Query(value = "select p from PersonInfo as p where city = ?1")
	public List<PersonInfo> findByCity3(String city);
	
	/* SQL�y�k�GSELECT DISTINCT */
	
	@Query(value = "select distinct new PersonInfo(city) from PersonInfo ")
	public List<PersonInfo> findDistinctCity();
	
	/* SQL�y�k�GORDER BY (asc default) */
	
//	@Query(value = "select * from person_info where city = ?1 order by age", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where city = ?1 order by age")
	public List<PersonInfo> findByCityOrderByAge(String city);
	
	/* SQL�y�k�GORDER BY + LIMIT */
	
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where city = ?1 order by age limit ?2") �� �o�Ӥ���ΡA�]��limit�u��bnativeQuery = true�ɰ���
	public List<PersonInfo> findByCityOrderByAgeLimit(String city, int limit);
	
	/* SQL�y�k�GLIMIT + �_�lIndex */
	// limit 5(����5�����) ���P�� limit 0, 5(����5����ơA�qindex = 0�}�l��)
	
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2, ?3", nativeQuery = true)
	public List<PersonInfo> findByCityOrderByAgeLimit2(String city, int startIndex, int limit);
	
	/* SQL�y�k�GBETWEEN */
	
//	@Query(value = "select * from person_info where age between ?1 and ?2", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where age between ?1 and ?2")
	public List<PersonInfo> findByAgeBetween(int age1, int age2);
	
	/* SQL�y�k�GIN */
	//JPQL�y�k�L�k�F��ʺA�Ѽ�(�i�ܦ��Ѽ�)�A�ҥH������JPA��in�N�n
	
//	@Query(value = "select * from person_info where city in(?1, ?2)", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where age in(?1, ?2)")
	public List<PersonInfo> findByCityIn(String ...city);
	
	/* SQL�y�k�GLIKE */
	
//	@Query(value = "select * from person_info where city like %?1%", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where city like %?1%")
	public List<PersonInfo> findByCityLike(String str);
	
	/* SQL�y�k�GLIKE + IGNORE CASE */
	//lower �� ���������ন���p�g�Ӥ��
	//uppder �� ���������ন���j�g�Ӥ��
	//�n�ϥ�concat�s���r��M�ܼơASQL�y�k���r��O�γ�޸�
	
//	@Query(value = "select * from person_info where lower(city) like lower(concat('%',?1,'%'))", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where lower(city) like lower(concat('%',?1,'%'))")
	public List<PersonInfo> findByCityContainingIgnoreCase(String str);
	//SQL�y�k��������L�k�ΡA����JPA��containing!
	
	/* SQL�y�k�GREGEXP */
	
	@Query(value = "select * from person_info where city regexp ?1", nativeQuery = true)
//	@Query(value = "select p from PersonInfo as p where city regexp ?1") �� �o�Ӥ���ΡA�]��REGECP�u��bnativeQuery = true�ɰ���
	public List<PersonInfo> findByCityRegexp(String str);
	
	//�n�h�Ӫ��ܥ�||�s��
	@Query(value = "select * from person_info where city regexp ?1||?2", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1, String str2);
	
	/* SQL�y�k�GJOIN */
	//select (��1��)���W1, (��2��)���W2 from ��1 join ��2 on ��1����=��2����
	
//	@Query(value = "select * from person_info as p join atm on p.id = atm.account", nativeQuery = true) 
//  �� �o�˼g���M�i�H�]�A���u�|��person_info����ơA�]����ƫ��A�OPersonInfo�A�S��k��atm����� �� ��VO�s�W�@�����OJoinVo�ӷ�e���B�@�w�n�ΧO�W�g�k
// 	(1)��ĳ�ϥ�nativeQuery = false�A�]���ϥ�true�ɡA���^�Ӫ���ƫ��A�OObject(�Lgetter/setter)
//  (2)�]��JoinVo�S���QSpring Boot�U��(class�W�S������annotation)�A�ҥH�����n�i��Spring Boot���|
//  (3)JoinVo�����Ҧ��ݩʡA�n�ϥ� ��.��� �� �O�W.��� �Ӫ��
	@Query(value = "select new com.example.practice.vo.JoinVo(p.id, p.name, p.age, p.city, a.amount) from PersonInfo as p join Atm as a on p.id = a.account") 
	public List<JoinVo> joinById();
	
}
