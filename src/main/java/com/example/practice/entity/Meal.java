package com.example.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // �NMeal���Spring Boot�U�ަ�������(Entity)�A���Ʈw������(mapping)
@Table(name = "meal") // �i�DJava�o�@�����O�O��������@�i��
public class Meal {

	@Id // �i�DJava�o�ӬOPK�A�]�������b���OP Key
	@Column(name = "name") // �Ĥ@��name�O�y�k�A�i�DJava���W�r�A�ĤG�Ӧr�ꤤ��name�ODB table���F���W��(�n�����@��)
	private String name; // �o�̪��ܼƦW��name�O�ݩʦW��

	@Column(name = "price")
	private int price;

	public Meal() {
		super();
	}

	public Meal(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
