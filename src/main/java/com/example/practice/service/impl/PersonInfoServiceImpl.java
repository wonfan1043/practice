package com.example.practice.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.repository.PersonInfoDao;
import com.example.practice.service.ifs.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public void addInfo(List<PersonInfo> infoList) {
		//�{���P��Ʈw���s����cost�ܤj�A�ҥH�s���e��P�_�����`���n���P�_
		//���P�_�O�_���list���O�Ū�
		if(infoList == null) {
			System.out.println("�п�J���!");
			return;
		}
		//�A�P�_�C�@����ƬO�_���� or �C����ƪ�ID�O�_���� or �C����ƪ�ID�O�_���Ŧr��Ϊťզr��
		//���ӬO�g�Gitem.getId() == null || item.getId().isBlank()
		//���O(1)�GStringUtils������hasText�o�Ӥ�k�i�H�����P�O��J��ID�O�_���šB�Ŧr��Ϊťզr��
		//���O(2)�GhasText�p�G��true�N��r�ꤺ�e���O�šB�Ŧr��Ϊťզr��A�ҥH�e��n�[�W��ĸ��u!�v���൲�G
		for (PersonInfo item : infoList) {
			if(item == null || !StringUtils.hasText(item.getId()) || item.getAge()<0) {
				System.out.println("��ƿ��~!");
				continue;
			}
			if(personInfoDao.existsById(item.getId())) {
				System.out.println(item.getId()+"�w�s�b!���n�ð��_!");
				continue; //�greturn�N�|���������{���B�����~��s��L���
			} 
			personInfoDao.save(item);
			System.out.println("�w�s�W��ơG"+"ID�G"+item.getId()+" �W�r�G"+item.getName()+" �~�֡G"+item.getAge()+" �����G"+item.getCity());
			}
	}
	
//	�H�U�O�ۤv�g���U����k�@XDDD
//	System.out.println("�п�JID�]A+3�ӼƦr�^");
//	Scanner scan = new Scanner(System.in);
//	String inputId = scan.next();
//	Optional<PersonInfo> op = personInfoDao.findById(inputId);
//	if(op.isPresent()) {
//		System.out.println("���H�w�s�b!���n�ð��_!");
//	} else {
//		System.out.println("�~���J�W�r");
//		String inputName = scan.next();
//		System.out.println("�~���J�~��");
//		int inputAge = scan.nextInt();
//		System.out.println("�~���J����");
//		String inputCity = scan.next();
//		PersonInfo addIn = new PersonInfo(inputId, inputName, inputAge, inputCity);
//		personInfoDao.save(addIn);
//		System.out.println("�w�s�W��ơG"+"ID�G"+addIn.getId()+" �W�r�G"+addIn.getName()+" �~�֡G"+addIn.getAge()+" �����G"+addIn.getCity());
//	}
//	System.out.println("�O�_�s�W�U�@���H");
	
	
//	Optional<PersonInfo> op = personInfoDao.findById(personInfo.getId());
//	if(op.isEmpty()) {
//		personInfoDao.save(personInfo);
//		System.out.println("�w�s�W���");
//		System.out.println("ID�G"+personInfo.getId());
//		System.out.println("�m�W�G"+personInfo.getName());
//		System.out.println("�~�֡G"+personInfo.getAge());
//		System.out.println("�����G"+personInfo.getCity());
//
//	} else {
//		System.out.println("���H�w�s�b!���n�ð��_!");
//	}

	@Override
	public void findAll() {
		List<PersonInfo> allData = personInfoDao.findAll();
		for(PersonInfo item : allData) {
			System.out.println("ID�G"+item.getId()+" �W�r�G"+item.getName()+" �~�֡G"+item.getAge()+" �����G"+item.getCity());
		}
	}

	@Override
	public void findById(String id) {
		if(!StringUtils.hasText(id)) {
			System.out.println("�п�JID!");
			return;
		}
		Optional<PersonInfo> target = personInfoDao.findById(id);
		if(target.isEmpty()){
			System.out.println("�d�L���H!");
			return;
		}
		System.out.println("ID�G"+target.get().getId());
		System.out.println("�m�W�G"+target.get().getName());
		System.out.println("�~�֡G"+target.get().getAge());
		System.out.println("�����G"+target.get().getCity());
	}

/*	@Override
	public void findByAgeGreaterThan(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeGreaterThan(age);
		if(list.isEmpty()) { //�]�i�H�glist.size()==0
			System.out.println("�d�L���!");
			return;
		}
		for(PersonInfo item : list) {
			System.out.println("ID�G"+item.getId()+" �W�r�G"+item.getName()+" �~�֡G"+item.getAge()+" �����G"+item.getCity());
		}
	}	*/

	/*	@Override
	public void findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater) {
		//���μg�o�Ө��b�A�]���o��OOR�A�e�ӽd��ϴN�z�ѡA���p�G�OAND�N���i�H�F
//		if(ageLess >= ageGreater) {
//			System.out.println("�j�M�d����~!");
//			return;
//		}
		List<PersonInfo> list = personInfoDao.findByAgeLessThanOrAgeGreaterThan(ageLess, ageGreater);
		if(list.isEmpty()) {
			System.out.println("�d�L���!");
			return;
		}
		for(PersonInfo item : list) {
			System.out.println("ID�G"+item.getId()+" �W�r�G"+item.getName()+" �~�֡G"+item.getAge()+" �����G"+item.getCity());
		}
	}	*/

/*	@Override
	public void findByAgeLessThanEqualOrderByAgeAsc(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanEqualOrderByAgeAsc(age);
		if(list.isEmpty()) {
			System.out.println("�d�L���!");
			return;
		}
		for(PersonInfo item : list) {
//			System.out.println("ID�G"+item.getId()+" �W�r�G"+item.getName()+" �~�֡G"+item.getAge()+" �����G"+item.getCity());
			System.out.printf("ID�G%s �W�r�G%s �~�֡G%d �����G%s\n",item.getId(),item.getName(),item.getAge(),item.getCity());
		}
	}	*/
	
	// ������ �⭫�ƪ��{���X���Ӧ۫ؤ�k ������
	
	private void checkAndPrintList(List<PersonInfo> list) { //�򥻤W�u���o�̷|�ΩҥH�]��private�Y�i
		if(list.isEmpty()) {
			System.out.println("�d�L���!");
			return;
		}
		for(PersonInfo item : list) {
			System.out.printf("ID�G%s �W�r�G%s �~�֡G%d �����G%s\n",item.getId(),item.getName(),item.getAge(),item.getCity());
		}
	}
	
	// ������ �W�観�Ψ�ۦP�{���X����k�N�i�H�g���U���o�� ������
	
	@Override
	public void findByAgeGreaterThan(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeGreaterThan(age);
		checkAndPrintList(list);
	}
	
	@Override
	public void findByAgeLessThanOrAgeGreaterThan(int ageLess, int ageGreater) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanOrAgeGreaterThan(ageLess, ageGreater);
		checkAndPrintList(list);
	}
	
	@Override
	public void findByAgeLessThanEqualOrderByAgeAsc(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanEqualOrderByAgeAsc(age);
		checkAndPrintList(list);
	}

	// ===== �ڬO���j�u =====
	
	@Override
	public void findByCityContaining(String keyword) {
		List<PersonInfo> list = personInfoDao.findByCityContaining(keyword);
		checkAndPrintList(list);
	}
	
	/* SQL annotation @Transactional */
	@Transactional(rollbackOn = Exception.class)
	@Override
	public int updateCityById3(String id, String city) throws IOException {
		personInfoDao.updateCityById3(id, city);
		throw new IOException("==========");
	}
	//@Transactional �� �w�]����Ʀ^��(����s)�O�o�ͦbRuntimeException�Y��l���O�A���|�]�t�S�����OIOException
	//�]���]�wException�Ψ�Ҧ��l���O�N�i�H�]�tIOException�A�Y�N�h�ũ԰�������O
	//�i�H���ծ���(rollbackOn = Exception.class)�ݬݸ�ƬO�_�|�^��
}
