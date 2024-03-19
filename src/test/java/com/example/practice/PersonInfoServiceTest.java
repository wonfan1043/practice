package com.example.practice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.repository.PersonInfoDao;
import com.example.practice.service.ifs.PersonInfoService;
import com.example.practice.vo.JoinVo;

@SpringBootTest
public class PersonInfoServiceTest {

	@Autowired
	public PersonInfoService personInfoService;

	@Autowired
	public PersonInfoDao personInfoDao;

	@Test
	public void addInfoTest1() {
		List<PersonInfo> addList = new ArrayList<>();
		addList.add(new PersonInfo("A005", "����", 28, "�x�F"));
		addList.add(new PersonInfo("A005", "����", 28, "�x�F"));
		addList.add(new PersonInfo("A006", "����", 38, "�y��"));
		addList.add(new PersonInfo("A007", "���E", 48, "�x��"));
		personInfoService.addInfo(addList);
	}

	// ����StringUtils.hasText()���޿�!
	@Test
	public void addInfoTest2() {
		String str1 = null;
		String str2 = "";
		String str3 = " ";
		String str4 = "ABC";
		System.out.println(str1 + StringUtils.hasText(str1));
		System.out.println(str2 + StringUtils.hasText(str2));
		System.out.println(str3 + StringUtils.hasText(str3));
		System.out.println(str4 + StringUtils.hasText(str4));
		System.out.println("=====�ڬO���j�u=====");
		System.out.println(str1 + !StringUtils.hasText(str1));
		System.out.println(str2 + !StringUtils.hasText(str2));
		System.out.println(str3 + !StringUtils.hasText(str3));
		System.out.println(str4 + !StringUtils.hasText(str4));
	}

	@Test
	public void findAllTest() {
		personInfoService.findAll();
	}

	@Test
	public void findByIdTest() {
		personInfoService.findById("A003");
	}

	@Test
	public void findByAgeGreaterThanTest() {
		personInfoService.findByAgeGreaterThan(30);
	}

	@Test
	public void findByAgeLessThanOrAgeGreaterThan() {
		personInfoService.findByAgeLessThanOrAgeGreaterThan(40, 30);
	}

	@Test
	public void findByAgeLessThanEqualOrderByAgeAsc() {
		personInfoService.findByAgeLessThanEqualOrderByAgeAsc(50);
	}

	@Test
	public void findByCityContainingTest() {
		personInfoService.findByCityContaining("�x");
	}

	/* ��Ʈw�y�kTEST */

	// INSERT

	@Test
	public void insertTest1() {
		int a = personInfoDao.insert("A011", "���b");
		System.out.println("=====>" + a);
	}

	@Test
	public void insertTest2() {
		int a = personInfoDao.insert("A012", "����", "���");
		System.out.println("=====>" + a);
	}

	// UPDATE

	@Test
	public void updateTest1() {
		int a = personInfoDao.updateCityById("A012", "�x�_");
		System.out.println("=====>" + a);
		a = personInfoDao.updateCityById("A011", "�x�_");
		System.out.println("=====>" + a);
	}

	@Test
	public void updateTest2() {
		int a = personInfoDao.updateCityByAgeLessThanEqual(30, "���");
		System.out.println("=====>" + a);
		a = personInfoDao.updateCityByAgeLessThanEqual(50, "���");
		System.out.println("=====>" + a);
	}

	@Test
	public void updateTest3() {
		int a = personInfoDao.updateCityById2("A012", "����");
		System.out.println("=====>" + a);
		a = personInfoDao.updateCityById2("A011", "����");
		System.out.println("=====>" + a);
	}

	@Transactional
	@Test
	public void updateTest4() throws Exception {
		int a = personInfoDao.updateCityById2("A011", "��");
		System.out.println("=====>" + a); // �o�̷|�L�X1,�N�����`��s��ơB�q�����ܦ���
		// throw new Exception("===== ERROE YO! =====");
		// //�G�N��@��Exception�Ӽ����X�������ҡA���ɸ�Ʒ|�^�Ҭ��쥻������
		throw new IOException("===== ERROE YO! ====="); // Transactional�u�o�ͦbException��RuntimeException
	}

	// @Transactional

	// ����1�GDao���[@Transactional�A��Service����k�S���[
	// ���G�G��Ʊq�s���ܬ��� �� @Transactional�L��
	// ��]�Gnon-transactional��k�I�stransactional��k
	//    (non-transacional�O����k�W�S���[@Transactional)
	@Test
	public void transTest1() {
		personInfoService.transTest1("A002", "��");
	}

	// ����2�GDao�MService����k�����[@Transactional
	// ���G�G��ƺ������s�� �� @Transactional����
	// ��]�Gtransactional��k�I�stransactional��k
	@Test
	public void transTest2() {
		personInfoService.transTest2("A002", "��");
	}

	// ����3�GDao�MpublicMethod�����[@Transactional�AService��k�S���[
	// Service��k�|�I�spublicMethod�AException�o�ͦbpublicMethod
	// ���G�G��Ʊq�s���ܬ��� �� @Transactional�L��
	// ��]�Gnon-transactional��k�I�stransactional��k
	@Test
	public void transTest3() {
		personInfoService.transTest3("A002", "��");
	}

	// ����4�GDao�MprivateMethod�����[@Transactional�AService��k�S���[
	// Service��k�|�I�sprivateMethod�AException�o�ͦbprivateMethod
	// ���G�G��Ʊq�s���ܬ��� �� @Transactional�L��
	// ��]�Gnon-transactional��k�I�stransactional��k
	@Test
	public void transTest4() {
		personInfoService.transTest4("A002", "��");
	}

	// ����5�GDao�BprivateMethod�k�MService����k�����[@Transactional
	// Service��k�|�I�sprivateMethod�AException�o�ͦbprivateMethod
	// ���G�G��ƺ������s�� �� @Transactional����
	@Test
	public void transTest5() {
		personInfoService.transTest5("A002", "��");
	}
	
	// ����6�GDao�MService����k�����[@Transactional�AprivateMethod�k�S���[
	// Service��k�|�I�sprivateMethod�AException�o�ͦbprivateMethod
	// ���G�G��ƺ������s�� �� @Transactional����
	@Test
	public void transTest6() {
		personInfoService.transTest6("A002", "��");
	}
	
	// ����7�G@Test�W��[�W@Transactional�A���դ��e�O�I�s�L�Ī�transTest1
	// ���G�G��ƺ������s�� �� @Transactional����
	@Transactional
	@Test
	public void transTest7() {
		personInfoService.transTest1("A002", "��");
	}
	
	// ����8�Gread-only
	// ���G�G��ƺ������s�� �� �]���Oread-only�ҥH�����\��ʸ��
	@Test
	public void transTest8() {
		personInfoService.transTest8("A002", "��");
	}
	
	// SELECT
	
	@Test
	public void selectTest1() {
		List<PersonInfo> res = personInfoDao.findByCity("���");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest2() {
		List<PersonInfo> res = personInfoDao.findByCity1("���");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest3() {
		List<PersonInfo> res = personInfoDao.findByCity2("���");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest4() {
		List<PersonInfo> res = personInfoDao.findByCity3("���");
		System.out.println(res.size());
	}
	
	@Test
	public void selectDistinctTest() {
		List<PersonInfo> res = personInfoDao.findDistinctCity();
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByTest() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAge("���");
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByLimitTest() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAgeLimit("���", 2);
		System.out.println(res.size());
	}
	
	@Test
	public void findByCityOrderByAgeLimit2Test() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAgeLimit2("���", 1, 2);
		System.out.println(res.size());
	}
	
	@Test
	public void findByAgeBetweenTest() {
		List<PersonInfo> res = personInfoDao.findByAgeBetween(20, 80);
		System.out.println(res.size());
	}
	
	@Test
	public void selectLikeTest() {
		List<PersonInfo> res = personInfoDao.findByCityLike("��");
		System.out.println(res.size());
	}
	
	@Test
	public void selectIgnoreCaseLikeTest() {
		List<PersonInfo> res = personInfoDao.findByCityContainingIgnoreCase("��");
		System.out.println(res.size());
	}
	
	@Test
	public void selectRegexpTest() {
		List<PersonInfo> res = personInfoDao.findByCityRegexp("��");
		System.out.println(res.size());
	}
	
	@Test
	public void joinTest() {
		List<JoinVo> res = personInfoDao.joinById();
		System.out.println(res.size());
	}
	
}
